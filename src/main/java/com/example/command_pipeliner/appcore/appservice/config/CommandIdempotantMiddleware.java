package com.example.command_pipeliner.appcore.appservice.config;

import an.awesome.pipelinr.Command;
import com.example.command_pipeliner.appcore.domain.model.CommandLog;
import com.example.command_pipeliner.appcore.domain.model.CommandLogRepository;
import com.example.command_pipeliner.appcore.domain.model.CommandStatus;
import com.example.command_pipeliner.common.IdempotentCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommandIdempotantMiddleware implements Command.Middleware {

    private final CommandLogRepository commandLogRepository;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public <R, C extends Command<R>> R invoke(C c, Next<R> next) {
        if (c instanceof IdempotentCommand<?> == false) {
            return next.invoke();
        }

        R response;
        IdempotentCommand<?> cmd = (IdempotentCommand<?>) c;
        String commandId = cmd.getCommandId();
        Optional<CommandLog> commandLogOpt = commandLogRepository.findByCommandIdAndCommandType(commandId, cmd.getCommandType());

        if (commandLogOpt.isEmpty()) {
            commandLogRepository.creteNewCommandLog(commandId, objectMapper.writeValueAsString(c), CommandStatus.DOING.name(), cmd.getClass().getName(), cmd.getCommandType());
            response = next.invoke();
            commandLogRepository.updateResponseAndStatusByCommandId(objectMapper.writeValueAsString(response), CommandStatus.OK.name(), response.getClass().getName(), commandId);
        } else {
            int retry = 0;
            CommandLog commandLog = commandLogOpt.get();

            while (commandLog.getStatus() == CommandStatus.DOING && retry < 3) {
                Thread.sleep(5_000L);
                commandLog = commandLogRepository.findByCommandId(commandId).get();
                retry++;
            }

            if (commandLog.getStatus() != CommandStatus.OK) {
                throw new RuntimeException("command is not ok: " + commandLog);
            }

            response = (R) objectMapper.readValue(commandLog.getResponse(), Class.forName(commandLog.getResponseClass()));
        }

        return response;
    }
}

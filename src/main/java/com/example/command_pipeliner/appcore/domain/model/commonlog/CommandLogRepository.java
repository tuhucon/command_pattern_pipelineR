package com.example.command_pipeliner.appcore.domain.model.commonlog;

import com.example.command_pipeliner.appcore.domain.model.commonlog.CommandLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CommandLogRepository extends JpaRepository<CommandLog, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into command_log (command_id, request, status, command_class, command_type) VALUES (:commandId, :request, :status, :commandClass, :commandType)", nativeQuery = true)
    @Transactional
    void creteNewCommandLog(String commandId, String request, String status, String commandClass, String commandType);

    Optional<CommandLog> findByCommandIdAndCommandType(String commandId, String commandType);

    @Modifying(clearAutomatically = true)
    @Query(value = "update command_log set response = :response, status = :status, response_class = :responseClass where command_id = :commandId", nativeQuery = true)
    @Transactional
    void updateResponseAndStatusByCommandId(String response, String status, String responseClass, String commandId);

    Optional<CommandLog> findByCommandId(String commandId);
}
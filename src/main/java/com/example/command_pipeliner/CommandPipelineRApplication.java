package com.example.command_pipeliner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//1. too much conveter body -> command -> event
//2. reader side need to have logic to build model from event ???
//3. if service return domain object, we will have problem with json encode <-> decode for idempotent.git
//4. ValueObject become xxx: {value: } when json encoding.
public class CommandPipelineRApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandPipelineRApplication.class, args);
    }

}

package com.example.command_pipeliner.presentation;

import lombok.Data;

@Data
public abstract class IdempotentKeyBody {

    String idempotentKey;
}

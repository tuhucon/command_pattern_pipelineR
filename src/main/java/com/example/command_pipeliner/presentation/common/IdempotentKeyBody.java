package com.example.command_pipeliner.presentation.common;

import lombok.Data;

@Data
public abstract class IdempotentKeyBody {

    String idempotentKey;
}

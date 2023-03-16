package com.example.command_pipeliner.presentation;

import lombok.Data;

@Data
public class UpdateProductStockBody extends IdempotentKeyBody{

    private Long id;
    private Integer stock;
}

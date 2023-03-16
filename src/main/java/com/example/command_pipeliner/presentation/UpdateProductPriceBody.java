package com.example.command_pipeliner.presentation;

import lombok.Data;

@Data
public class UpdateProductPriceBody extends IdempotentKeyBody{

    private Long id;
    private Integer price;
}

package com.example.command_pipeliner.presentation;

import lombok.Data;

@Data
public class CreateProductBody extends IdempotentKeyBody{

    private String title;
    private String description;
    private Integer price;
    private Integer stock;
}

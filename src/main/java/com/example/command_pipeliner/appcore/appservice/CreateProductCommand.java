package com.example.command_pipeliner.appcore.appservice;

import com.example.command_pipeliner.appcore.domain.model.Product;
import com.example.command_pipeliner.common.IdempotentCommand;
import lombok.Getter;
import lombok.Setter;

public class CreateProductCommand extends IdempotentCommand<Product> {

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Integer price;

    @Getter
    @Setter
    private Integer stock;

    public CreateProductCommand(String commandId) {
        super(commandId);
    }
}

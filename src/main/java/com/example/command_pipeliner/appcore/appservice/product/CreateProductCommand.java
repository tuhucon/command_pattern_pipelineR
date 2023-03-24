package com.example.command_pipeliner.appcore.appservice.product;

import com.example.command_pipeliner.appcore.domain.model.product.Product;
import com.example.command_pipeliner.appcore.appservice.common.IdempotentCommand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductCommand extends IdempotentCommand<Product> {

    private String title;
    private String description;
    private Integer price;
    private Integer stock;

    public CreateProductCommand(String commandId) {
        super(commandId);
    }
}

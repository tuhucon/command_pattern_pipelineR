package com.example.command_pipeliner.appcore.appservice.product;

import com.example.command_pipeliner.appcore.domain.model.product.Product;
import com.example.command_pipeliner.common.IdempotentCommand;
import lombok.Getter;
import lombok.Setter;

public class UpdateProductStockCommand extends IdempotentCommand<Product> {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Integer stock;

    public UpdateProductStockCommand(String commandId) {
        super(commandId);
    }
}

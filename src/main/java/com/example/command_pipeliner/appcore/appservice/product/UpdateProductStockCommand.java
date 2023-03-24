package com.example.command_pipeliner.appcore.appservice.product;

import com.example.command_pipeliner.appcore.domain.model.product.Product;
import com.example.command_pipeliner.appcore.appservice.common.IdempotentCommand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductStockCommand extends IdempotentCommand<Product> {

    private Long id;
    private Integer stock;

    public UpdateProductStockCommand(String commandId) {
        super(commandId);
    }
}

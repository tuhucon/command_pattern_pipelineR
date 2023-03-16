package com.example.command_pipeliner.presentation;

import an.awesome.pipelinr.Pipeline;
import com.example.command_pipeliner.appcore.appservice.CreateProductCommand;
import com.example.command_pipeliner.appcore.appservice.UpdateProductPriceCommand;
import com.example.command_pipeliner.appcore.appservice.UpdateProductStockCommand;
import com.example.command_pipeliner.appcore.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.server.UID;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final Pipeline pipeline;

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductBody body) {
        CreateProductCommand command = new CreateProductCommand(body.getIdempotentKey());

        command.setTitle(body.getTitle());
        command.setDescription(body.getDescription());
        command.setPrice(body.getPrice());
        command.setStock(body.getStock());

        Product product = command.execute(pipeline);
        return product;
    }

    @PostMapping("/products/updatePrice")
    public Product updateProductPrice(@RequestBody UpdateProductPriceBody body) {
        UpdateProductPriceCommand command = new UpdateProductPriceCommand(body.getIdempotentKey());

        command.setId(body.getId());
        command.setPrice(body.getPrice());

        Product product = command.execute(pipeline);
        return product;
    }

    @PostMapping("/products/updateStock")
    public Product updateProductStock(@RequestBody UpdateProductStockBody body) {
        UpdateProductStockCommand command = new UpdateProductStockCommand(body.getIdempotentKey());

        command.setId(body.getId());
        command.setStock(body.getStock());

        Product product = command.execute(pipeline);
        return product;
    }

}

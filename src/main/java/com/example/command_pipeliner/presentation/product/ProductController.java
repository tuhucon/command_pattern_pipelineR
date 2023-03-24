package com.example.command_pipeliner.presentation.product;

import an.awesome.pipelinr.Pipeline;
import com.example.command_pipeliner.appcore.appservice.product.CreateProductCommand;
import com.example.command_pipeliner.appcore.appservice.product.UpdateProductPriceCommand;
import com.example.command_pipeliner.appcore.appservice.product.UpdateProductStockCommand;
import com.example.command_pipeliner.appcore.domain.model.product.Product;
import com.example.command_pipeliner.appcore.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final Pipeline pipeline;

    private final ProductRepository productRepository;

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

    @PostMapping("/products/{productId}/updatePrice")
    public Product updateProductPrice(@PathVariable Long productId, @RequestBody UpdateProductPriceBody body) {
        UpdateProductPriceCommand command = new UpdateProductPriceCommand(body.getIdempotentKey());

        command.setId(productId);
        command.setPrice(body.getPrice());

        Product product = command.execute(pipeline);
        return product;
    }

    @PostMapping("/products/{productId}/updateStock")
    public Product updateProductStock(@PathVariable Long productId, @RequestBody UpdateProductStockBody body) {
        UpdateProductStockCommand command = new UpdateProductStockCommand(body.getIdempotentKey());

        command.setId(productId);
        command.setStock(body.getStock());

        Product product = command.execute(pipeline);
        return product;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

}

package com.example.command_pipeliner.appcore.appservice.product;

import an.awesome.pipelinr.Command;
import com.example.command_pipeliner.appcore.domain.model.product.Product;
import com.example.command_pipeliner.appcore.domain.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProductCommandHandler implements Command.Handler<CreateProductCommand, Product> {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product handle(CreateProductCommand command) {
        Product product = new Product();
        product.setTitle(command.getTitle());
        product.setDescription(command.getDescription());
        product.setStock(command.getStock());
        product.setPrice(command.getPrice());

        productRepository.save(product);

        return product;
    }
}

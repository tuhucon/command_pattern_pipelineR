package com.example.command_pipeliner.appcore.appservice;

import an.awesome.pipelinr.Command;
import com.example.command_pipeliner.appcore.domain.model.Product;
import com.example.command_pipeliner.appcore.domain.model.ProductRepository;
import com.example.command_pipeliner.common.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductStockCommandHandler implements Command.Handler<UpdateProductStockCommand, Product> {

    private final ProductRepository productRepository;

    @Override
    public Product handle(UpdateProductStockCommand command) {
        Product product = productRepository.findById(command.getId())
                .orElseThrow(ObjectNotFoundException::new);

        product.setStock(command.getStock());

        productRepository.save(product);

        return product;
    }
}

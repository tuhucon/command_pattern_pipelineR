package com.example.command_pipeliner.appcore.appservice.product;

import an.awesome.pipelinr.Command;
import com.example.command_pipeliner.appcore.domain.model.product.Product;
import com.example.command_pipeliner.appcore.domain.model.product.ProductRepository;
import com.example.command_pipeliner.common.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductPriceCommandHandler implements Command.Handler<UpdateProductPriceCommand, Product> {

    private final ProductRepository productRepository;

    @Override
    public Product handle(UpdateProductPriceCommand command) {
        Product product = productRepository.findById(command.getId())
                .orElseThrow(ObjectNotFoundException::new);

        product.setPrice(command.getPrice());

        productRepository.save(product);

        return product;
    }
}

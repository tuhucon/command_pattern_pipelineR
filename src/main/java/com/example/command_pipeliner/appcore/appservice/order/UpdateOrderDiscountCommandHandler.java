package com.example.command_pipeliner.appcore.appservice.order;

import an.awesome.pipelinr.Command;
import com.example.command_pipeliner.appcore.domain.model.common.PrecisionMoney;
import com.example.command_pipeliner.appcore.domain.model.order.Order;
import com.example.command_pipeliner.appcore.domain.model.order.OrderRepostiory;
import com.example.command_pipeliner.appcore.domain.model.order.UpdateOrderDiscountEvent;
import com.example.command_pipeliner.common.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOrderDiscountCommandHandler implements Command.Handler<UpdateOrderDiscountCommand, Order> {

    private final OrderRepostiory orderRepostiory;

    @Override
    @Transactional
    public Order handle(UpdateOrderDiscountCommand command) {
        UpdateOrderDiscountEvent event = new UpdateOrderDiscountEvent();
        event.setDiscount(new PrecisionMoney(command.getDiscount().toString()));

        Order order = orderRepostiory.findById(command.getOrderId())
                .orElseThrow(ObjectNotFoundException::new);
        order.updateDiscount(event);
        orderRepostiory.save(order);
        return order;
    }
}

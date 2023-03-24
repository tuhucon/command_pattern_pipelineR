package com.example.command_pipeliner.infras.domain.model.order;

import com.example.command_pipeliner.appcore.domain.model.common.DomainEvent;
import com.example.command_pipeliner.appcore.domain.model.order.Order;
import com.example.command_pipeliner.appcore.domain.model.order.OrderRepostiory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepostiory {

    private final InfrasOrderRepository orderRepository;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    @Transactional
    public void save(Order order) {
        InfrasOrders infrasOrders = new InfrasOrders();
        infrasOrders.setId(order.getId());
        infrasOrders.setUserId(order.getUserId());
        for (DomainEvent event : order.getEvents()) {
            OrderEvents orderEvents = new OrderEvents();
            orderEvents.setId(event.getId());
            orderEvents.setEventType(event.getEventType());
            orderEvents.setEventClass(event.getEventClassName());
            orderEvents.setVersion(event.getVersion());
            orderEvents.setEventContent(objectMapper.writeValueAsString(event));
            infrasOrders.addOrderEvent(orderEvents);
        }
        orderRepository.save(infrasOrders);
    }

    @Override
    @SneakyThrows
    public Optional<Order> findById(Long orderId) {
        Optional<InfrasOrders> infrasOrdersOpt = orderRepository.findById(orderId);
        if (infrasOrdersOpt.isEmpty()) {
            return Optional.empty();
        }

        Order order = convertInfrasOrderToDomainOrder(infrasOrdersOpt.get());
        return Optional.of(order);
    }

    @Override
    public List<Order> FindAllByUserId(Long userId) {
        List<InfrasOrders> infrasOrders = orderRepository.findAllByUserId(userId);
        List<Order> orders = new ArrayList<>();
        for (InfrasOrders infrasOrder: infrasOrders) {
            Order order = convertInfrasOrderToDomainOrder(infrasOrder);
            orders.add(order);
        }
        return orders;
    }

    @SneakyThrows
    private Order convertInfrasOrderToDomainOrder(InfrasOrders infrasOrders) {
        List<DomainEvent> events = new ArrayList<>();
        for (OrderEvents event : infrasOrders.getOrderEvents()) {
            DomainEvent domainEvent = (DomainEvent) objectMapper.readValue(event.getEventContent(), Class.forName(event.getEventClass()));
            domainEvent.setId(event.getId());
            events.add(domainEvent);
        }
        Order order = new Order(infrasOrders.getId(), events);
        return order;
    }
}

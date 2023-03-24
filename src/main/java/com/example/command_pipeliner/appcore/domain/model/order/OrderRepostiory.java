package com.example.command_pipeliner.appcore.domain.model.order;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface OrderRepostiory {

    @Transactional
    void save(Order order);

    Optional<Order> findById(Long orderId);

    List<Order> FindAllByUserId(Long userId);

}

package com.example.command_pipeliner.infras.domain.model.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface InfrasOrderRepository extends JpaRepository<InfrasOrders, Long> {

    List<InfrasOrders> findAllByUserId(Long userId);

}

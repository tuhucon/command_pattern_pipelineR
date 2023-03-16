package com.example.command_pipeliner.appcore.domain.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

}

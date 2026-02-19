package com.zestindia.product.repository;

import com.zestindia.product.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item , Long> {
}

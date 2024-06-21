package com.hutech.NguyenDacThang.repository;

import com.hutech.NguyenDacThang.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
package com.hutech.NguyenDacThang.repository;

import com.hutech.NguyenDacThang.model.Order;
import com.hutech.NguyenDacThang.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
        List<Order> findByUser(User user);
}
package com.travel.repository;

import com.travel.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(String status);

    Optional<Order> findByOrderNo(String orderNo);

    List<Order> findByUserIdOrderByCreateTimeDesc(Long userId);
}

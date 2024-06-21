package com.hutech.NguyenDacThang.service;

import com.hutech.NguyenDacThang.model.CartItem;
import com.hutech.NguyenDacThang.model.Order;
import com.hutech.NguyenDacThang.model.OrderDetail;
import com.hutech.NguyenDacThang.repository.OrderDetailRepository;
import com.hutech.NguyenDacThang.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartService cartService;

    public Order createOrder(String customerName, String shippingAddress, String phone, String email, String notes, String paymentMethod, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setShippingAddress(shippingAddress);
        order.setPhone(phone);
        order.setEmail(email);
        order.setNotes(notes);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("Order Placed");

        order = orderRepository.save(order);

        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }
        cartService.clearCart();

        return order;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}

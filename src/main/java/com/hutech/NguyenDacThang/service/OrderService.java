package com.hutech.NguyenDacThang.service;

import com.hutech.NguyenDacThang.model.CartItem;
import com.hutech.NguyenDacThang.model.Order;
import com.hutech.NguyenDacThang.model.OrderDetail;
import com.hutech.NguyenDacThang.model.User;
import com.hutech.NguyenDacThang.repository.OrderDetailRepository;
import com.hutech.NguyenDacThang.repository.OrderRepository;
import com.hutech.NguyenDacThang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    public Order createOrder(String customerName, String shippingAddress, String phone, String email, String notes, String paymentMethod, List<CartItem> cartItems, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Không tìm thấy người dùng với username: " + username);
        }

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setShippingAddress(shippingAddress);
        order.setPhone(phone);
        order.setEmail(email);
        order.setNotes(notes);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("Đã đặt hàng");
        order.setUser(user); // Liên kết đơn đặt hàng với người dùng

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

    public List<Order> getUserOrders(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("Không tìm thấy người dùng với username: " + username);
        }
        return orderRepository.findByUser(user);
    }

    public Map<User, List<Order>> getOrdersGroupedByUser() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().collect(Collectors.groupingBy(Order::getUser));
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}

package com.hutech.NguyenDacThang.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String shippingAddress;
    private String phone;
    private String email;
    private String notes;
    private String paymentMethod;
    private String status;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}

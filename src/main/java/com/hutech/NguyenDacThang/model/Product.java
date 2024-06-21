package com.hutech.NguyenDacThang.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String description;

    private int productQuantities;  // Changed to camelCase

    private String imagePath;  // New field for image path

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public int getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(int productQuantities) {
        this.productQuantities = productQuantities;
    }
}

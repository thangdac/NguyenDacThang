package com.hutech.NguyenDacThang.service;

import com.hutech.NguyenDacThang.model.Product;
import com.hutech.NguyenDacThang.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final String uploadDir = "src/main/resources/static/images/";

    public List<Product> getList() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.getReferenceById(id);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void editOrAddProduct(Product product, MultipartFile image) throws IOException {
        Optional<Product> existingProductOpt = productRepository.findByName(product.getName());

        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();
            existingProduct.setProductQuantities(existingProduct.getProductQuantities() + product.getProductQuantities());
            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);
                existingProduct.setImagePath(imagePath);
            }
            productRepository.save(existingProduct);
        } else {
            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);
                product.setImagePath(imagePath);
            }
            productRepository.save(product);
        }
    }

    private String saveImage(MultipartFile image) throws IOException {
        String fileExtension = getFileExtension(image.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
        Path path = Paths.get(uploadDir + newFileName);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());
        return newFileName;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex > 0 ? fileName.substring(dotIndex + 1) : "";
    }
}

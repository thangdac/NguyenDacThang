package com.hutech.NguyenDacThang.service;

import com.hutech.NguyenDacThang.model.Brand;
import com.hutech.NguyenDacThang.model.Category;
import com.hutech.NguyenDacThang.repository.BrandRepository;
import com.hutech.NguyenDacThang.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {
    @Autowired
    private final BrandRepository brandRepository;

    public List<Brand> getList() {
        List<Brand> list = brandRepository.findAll().stream().toList();
        return list;
    }

    public Brand GetBrand(Long id) {
        return brandRepository.getReferenceById(id);
    }

    public void DeleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public void EditOrAddBrand(Brand brand) {
        brandRepository.save(brand);
    }
}
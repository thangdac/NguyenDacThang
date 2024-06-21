package com.hutech.NguyenDacThang.service;

import com.hutech.NguyenDacThang.model.Manufacturer;
import com.hutech.NguyenDacThang.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ManufacturerService {
    @Autowired
    private final ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getList() {
        List<Manufacturer> list = manufacturerRepository.findAll().stream().toList();
        return list;
    }

    public Manufacturer GetManufacturer(Long id) {
        return manufacturerRepository.getReferenceById(id);
    }

    public void DeleteManufacturer(Long id) {
        manufacturerRepository.deleteById(id);
    }

    public void EditOrAddManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }
}
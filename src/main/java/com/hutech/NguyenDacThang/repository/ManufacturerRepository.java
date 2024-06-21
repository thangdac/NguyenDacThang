package com.hutech.NguyenDacThang.repository;

import com.hutech.NguyenDacThang.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>{
}
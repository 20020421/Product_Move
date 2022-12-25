package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.Capacity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {
    public Capacity findByCapacity(Integer capacity);
}

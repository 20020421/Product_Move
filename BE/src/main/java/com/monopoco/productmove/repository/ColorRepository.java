package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, Long> {
    public Color findByColor(String color);
}

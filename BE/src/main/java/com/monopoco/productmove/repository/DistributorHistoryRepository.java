package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.DistributorHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributorHistoryRepository extends JpaRepository<DistributorHistory, Long> {
    DistributorHistory findFirstByProduct_Serial(String serial);
}

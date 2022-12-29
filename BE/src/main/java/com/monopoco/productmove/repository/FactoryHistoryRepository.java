package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.FactoryHistory;
import com.monopoco.productmove.entity.HistoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactoryHistoryRepository extends JpaRepository<FactoryHistory, Long> {

    List<FactoryHistory> findFactoryHistoriesByFactory_NameAndHistoryType(String factory_name, HistoryType historyType);

}

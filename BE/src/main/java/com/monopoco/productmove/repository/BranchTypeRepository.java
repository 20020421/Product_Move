package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.BranchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BranchTypeRepository extends JpaRepository<BranchType, Long> {

    Optional<BranchType> findBranchTypeByTypeName(String typeName);

}

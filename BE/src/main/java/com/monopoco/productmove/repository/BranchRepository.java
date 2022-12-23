package com.monopoco.productmove.repository;

import com.monopoco.productmove.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findBranchesByBranchType_TypeName(String typeName);

//    List<Branch> findBranchesByBranchType_TypeCode(String typeCode);
    Optional<Branch> findBranchByName(String name);
}

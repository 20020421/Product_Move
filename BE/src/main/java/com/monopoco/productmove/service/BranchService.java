package com.monopoco.productmove.service;

import com.monopoco.productmove.entity.BranchType;
import com.monopoco.productmove.entityDTO.BranchDTO;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.entityDTO.WarehouseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BranchService {
    public BranchDTO save(BranchDTO branchDTO);

    public BranchDTO findById(Long id);

    public List<BranchDTO> findAllBranch();

    public List<BranchDTO> findBranchesByBranchType(String branchType);

    public Boolean addUserToBranch(String userName, String brandName);

    public List<String> getAllBranchName();

    public WarehouseDTO saveNewWarehouse(WarehouseDTO warehouseDTO);

    public Page<WarehouseDTO> getWarehouses(Pageable pageable);

    public int factoryWarehouseing(String warehouseName, List<ProductDTO> productDTOList);

    public void addBranchType(BranchType branchType);

    public Page<ProductDTO> getAllProduct(Pageable pageable);


}

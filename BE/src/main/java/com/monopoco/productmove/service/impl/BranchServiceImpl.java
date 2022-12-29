package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.*;
import com.monopoco.productmove.entityDTO.BranchDTO;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.entityDTO.UserDTO;
import com.monopoco.productmove.entityDTO.WarehouseDTO;
import com.monopoco.productmove.repository.*;
import com.monopoco.productmove.service.BranchService;
import com.monopoco.productmove.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@Slf4j
@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchTypeRepository branchTypeRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private FactoryHistoryRepository factoryHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;



    @Autowired
    private ModelMapper modelMapper;


    @Override
    public BranchDTO save(BranchDTO branchDTO) {
        Branch branch = modelMapper.map(branchDTO, Branch.class);
//        Boolean isExisted = branchRepository.findBranchByName(branchDTO.getName()) != null;

        if (branchDTO.getType() != null) {
            Optional<BranchType> optionalBranchType = branchTypeRepository.findBranchTypeByTypeName(branchDTO.getType());
            if (optionalBranchType.isPresent()) {
                BranchType branchType = optionalBranchType.get();
                branch.setBranchType(branchType);
            }
        }



        Branch branchSaved = branchRepository.save(branch);
        if (branchSaved != null) {
            BranchDTO returnBranchDTO = modelMapper.map(branchSaved, BranchDTO.class);
            if (branchSaved.getBranchType() != null) {
                returnBranchDTO.setType(branchSaved.getBranchType().getTypeName());
            }
            return returnBranchDTO;
        }
        return null;
    }

    @Override
    public BranchDTO findById(Long id) {
        return null;
    }

    @Override
    public List<BranchDTO> findAllBranch() {

        List<Branch> branches = branchRepository.findAll();
        List<BranchDTO> branchDTOS = new ArrayList<>();
        branches.forEach(branch -> {
            branchDTOS.add(mapBranch(branch));
        });


        return branchDTOS;
    }

    public BranchDTO mapBranch(Branch branch) {
        List<String> users = new ArrayList<>();
        branch.getUsers().forEach(user -> {
            users.add(user.getUsername());
        });
        BranchDTO branchDTO = modelMapper.map(branch, BranchDTO.class);
        branchDTO.setUsersName(users);
        branchDTO.setType(branch.getBranchType().getTypeName());
        return branchDTO;
    }

    @Override
    public List<BranchDTO> findBranchesByBranchType(String branchType) {
        List<Branch> branches = branchRepository.findBranchesByBranchType_TypeName(branchType);
        List<BranchDTO> branchDTOList = new ArrayList<>();
        if (branches.size() > 0) {
            branches.forEach(branch -> {
                branchDTOList.add(mapBranch(branch));
            });
        }
        return branchDTOList;
    }

    @Override
    public Boolean addUserToBranch(String userName, String brandName) {
        User user = userRepository.findUserByUsername(userName);
        Optional<Branch> optionalBranch = branchRepository.findBranchByName(brandName);
        if (optionalBranch.isPresent() && user != null) {
            String roleName = "";
            Branch branch = optionalBranch.get();
            switch (branch.getBranchType().getTypeCode()) {
                case "FTR":
                    roleName = "FACTORY";
                    break;
                case "DIA":
                    roleName = "DISTRIBUTOR";
                    break;
                case "WAC":
                    roleName = "WARRANTY";
                    break;
                default:

            }
            Role role = roleRepository.findRoleByName(roleName);
            user.setBranch(branch);
            user.setRole(role);


            log.info("adding username {} to branch {}", userName, brandName);
            return true;
        }

        return false;

    }

    @Override
    public List<String> getAllBranchName() {

        List<Branch> branches = branchRepository.findAll();
        List<String> branchesName = new ArrayList<>();
        if (branches.size() > 0) {
            branches.forEach(branch -> {
                branchesName.add(branch.getName());
            });
        }

        return branchesName;
    }

    @Override
    public WarehouseDTO saveNewWarehouse(WarehouseDTO warehouseDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findUserByUsername(username);
        String brandName = user.getBranch().getName();

        Warehouse warehouse = modelMapper.map(warehouseDTO, Warehouse.class);
        Optional<Branch> branch = branchRepository.findBranchByName(brandName);
        branch.ifPresent(warehouse::setBranch);
        warehouse.setProducts(new ArrayList<>());
        Warehouse warehouseSaved = warehouseRepository.save(warehouse);

        WarehouseDTO warehouseDTOSaved = modelMapper.map(warehouseSaved, WarehouseDTO.class);
        warehouseDTOSaved.setProductDTOS(new ArrayList<>());
        warehouseDTOSaved.setBranchName(brandName);
        return warehouseDTOSaved;


    }

    @Override
    public Page<WarehouseDTO> getWarehouses(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findUserByUsername(username);
        String brandName = user.getBranch().getName();
        Page<Warehouse> warehousePage = warehouseRepository.findWarehousesByBranch_Name(pageable, brandName);
        return warehousePage.map(new Function<Warehouse, WarehouseDTO>() {
            @Override
            public WarehouseDTO apply(Warehouse warehouse) {
                WarehouseDTO warehouseDTO = modelMapper.map(warehouse, WarehouseDTO.class);
                warehouseDTO.setBranchName(brandName);
                warehouseDTO.setTotalProduct(warehouse.getProducts().size());

                return warehouseDTO;
            }
        });
    }

    @Override
    public int factoryWarehouseing(String warehouseName, List<ProductDTO> productDTOList) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findWarehouseByName(warehouseName);
        if (warehouseOptional.isPresent()) {
            productDTOList.forEach(productDTO -> {
                ProductDTO productDTOSaved = productService.save(productDTO, warehouseOptional.get());
            });

            FactoryHistory factoryHistory = new FactoryHistory(null,
                    warehouseOptional.get().getBranch(),
                    HistoryType.WAREHOUSING,
                    productDTOList.size());
            FactoryHistory factoryHistorySaved =
                    factoryHistoryRepository.save(factoryHistory);

            return productDTOList.size();
        }
        return 0;
    }

    @Override
    public void addBranchType(BranchType branchType) {
        log.info("adding new branch type: {}", branchType.getTypeName());
        BranchType branchTypeSaved = branchTypeRepository.save(branchType);
    }

    @Override
    public Page<ProductDTO> getAllProduct(Pageable pageable) {

        Page<WarehouseDTO> warehouseDTOPage = getWarehouses(PageRequest.of(0, 1000));
        List<WarehouseDTO> warehouseDTOList = warehouseDTOPage.getContent();
        List<ProductDTO> productDTOList = new ArrayList<>();
        warehouseDTOList.forEach(warehouseDTO -> {
            List<ProductDTO> productDTOWarehouse = productService.getProductsByWarehouse(warehouseDTO.getName());
            productDTOList.addAll(productDTOWarehouse);
        });

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productDTOList.size());
        Page<ProductDTO> productDTOPage = new PageImpl<>(productDTOList.subList(start, end), pageable, productDTOList.size());
        return productDTOPage;
    }


}

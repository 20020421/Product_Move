package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.*;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.entityDTO.ProductModelDTO;
import com.monopoco.productmove.repository.CapacityRepository;
import com.monopoco.productmove.repository.ColorRepository;
import com.monopoco.productmove.repository.ProductModelRepository;
import com.monopoco.productmove.repository.ProductRepository;
import com.monopoco.productmove.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

@Service
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductModelRepository productModelRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private CapacityRepository capacityRepository;

    @Autowired
    private ModelMapper modelMapper;


    private ProductModelDTO mapProductModel(ProductModel productModel) {
        ProductModelDTO productModelDTO = modelMapper.map(productModel, ProductModelDTO.class);
        List<String> colorString = new ArrayList<String>();
        List<Integer> capacities = new ArrayList<>();
        if (productModel.getColor().size() > 0) {
            List<Color> colors = new ArrayList<>(productModel.getColor());
            colors.forEach(color -> {
                colorString.add(color.getColor());
            });
        }
        if (productModel.getCapacities().size() > 0) {
            List<Capacity> capacityList = new ArrayList<>(productModel.getCapacities());
            capacityList.forEach(capacity -> {
                capacities.add(capacity.getCapacity());
            });
        }

        productModelDTO.setColorString(colorString);
        productModelDTO.setCapacityList(capacities);

        return productModelDTO;


    }

    @Override
    public ProductDTO save(ProductDTO productDTO, Warehouse warehouse) {
        Product product = new Product();
        product.setWarehouse(warehouse);
        product.setSerial(productDTO.getSerial());
        product.setProductStatus(ProductStatus.NEWLY_PRODUCED);
        product.setFactory(warehouse.getBranch());
        Color color = colorRepository.findByColor(productDTO.getColorString());
        product.setColor(color);
        Capacity capacity = capacityRepository.findByCapacity(productDTO.getCapacityInt());
        product.setCapacity(capacity);
        ProductModel productModel = productModelRepository.findByModel(productDTO.getProductModelName());
        product.setProductModel(productModel);
        productRepository.save(product);

        return productDTO;
    }

    @Override
    public ProductDTO getById(Long id) {
        return null;
    }

    @Override
    public void addNewColor(String color, String code) {
        colorRepository.save(new Color(null, color, code, null, null));
    }

    @Override
    public void addNewCapacity(Integer capacity) {
        capacityRepository.save(new Capacity(null, capacity, null, null));
    }

    @Override
    public Map<String, String> getAllColor() {
        Map<String, String> colors = new HashMap<>();
        List<Color> colorList = colorRepository.findAll();
        colorList.forEach(color -> {
            colors.put(color.getColor(), color.getCode());
        });

        return colors;
    }

    @Override
    public List<Integer> getAllCapacity() {
        List<Capacity> capacities = capacityRepository.findAll();
        List<Integer>  capacitiesInt = new ArrayList<>();
        capacities.forEach(capacity -> {
            capacitiesInt.add(capacity.getCapacity());
        });
        return capacitiesInt;
    }

    @Override
    public ProductModelDTO saveNewProductModel(ProductModelDTO productModelDTO) {
        List<Color> colors = new ArrayList<>();
        List<Capacity> capacities = new ArrayList<>();
        productModelDTO.getColorString().forEach(colorString -> {
            Color colorDB = colorRepository.findByColor(colorString);
            colors.add(colorDB);
        });

        productModelDTO.getCapacityList().forEach(capacity -> {
            capacities.add(capacityRepository.findByCapacity(capacity));
        });

        ProductModel productModel = modelMapper.map(productModelDTO, ProductModel.class);
        productModel.setColor(new HashSet<>(colors));
        productModel.setCapacities(new HashSet<>(capacities));

        ProductModel productModelSaved = productModelRepository.save(productModel);
        if (productModelSaved.getId() > 0) {
            return mapProductModel(productModelSaved);
        } else {
            return null;
        }
    }

    @Override
    public Page<ProductModelDTO> getAllModel(Pageable pageable) {

        Page<ProductModel> productModelPage = productModelRepository.findAll(pageable);
        Page<ProductModelDTO> productModelDTOPage = productModelPage.map(new Function<ProductModel, ProductModelDTO>() {
            @Override
            public ProductModelDTO apply(ProductModel productModel) {
                return mapProductModel(productModel);
            }
        });
        return productModelDTOPage;

    }

    @Override
    public List<Map<String,String>> getColor(Long id) {
        Optional<ProductModel> productModel = productModelRepository.findById(id);
        List<Map<String, String>> colors = new ArrayList<>();
        productModel.get().getColor().forEach(color -> {
            Map<String, String> colorMap = new HashMap<>();
            colorMap.put("color", color.getColor());
            colorMap.put("code", color.getCode());
            colors.add(colorMap);
        });
        return colors;
    }

    @Override
    public Map<String, String> getColor(String model) {
        ProductModel productModel = productModelRepository.findByModel(model);
        Map<String, String> colors = new HashMap<>();
        productModel.getColor().forEach(color -> {
            colors.put(color.getColor(), color.getCode());
        });
        return colors;
    }

    @Override
    public void addNewColor(Map<String, String> colors) {
        for(Map.Entry<String, String> pair : colors.entrySet()) {
            colorRepository.save(new Color(null, pair.getKey(), pair.getValue(), null, null));
        }
    }

    @Override
    public List<ProductDTO> getProductsByWarehouse(String warehouse) {
        List<Product> productList = productRepository.findProductsByWarehouse_Name(warehouse);
        List<ProductDTO> productDTOList = new ArrayList<>();
        if (productList.size() > 0) {
            productList.forEach(product -> {
                productDTOList.add(productMap(product));
            });
        }

        return productDTOList;
    }

    @Override
    public Map<String, String> getColorByName(String color) {
        Color colorDb = colorRepository.findByColor(color);
        if (colorDb != null) {
            Map<String, String> colorMap = new HashMap<>();
            colorMap.put(colorDb.getColor(), colorDb.getCode());
            return colorMap;
        }
        return null;
    }

    private ProductDTO productMap(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String createdAt = formatter.format(product.getCreatedAt());
        productDTO.setCreatedAt(createdAt);
        productDTO.setColorString(product.getColor().getColor());
        productDTO.setCapacityInt(product.getCapacity().getCapacity());
        productDTO.setProductModelName(product.getProductModel().getModel());
        productDTO.setStatus(product.getProductStatus().toString());
        productDTO.setManufactureAt(product.getFactory().getName());
        productDTO.setWarehouseName(product.getWarehouse().getName());
        if (product.getDistribution() != null) {
            productDTO.setDistributionAt(product.getDistribution().getName());
        }
        if (product.getWarranty() != null) {
            productDTO.setWarrantyAt(product.getWarranty().getName());
        }

        return productDTO;
    }


}

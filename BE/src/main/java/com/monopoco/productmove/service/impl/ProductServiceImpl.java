package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.Capacity;
import com.monopoco.productmove.entity.Color;
import com.monopoco.productmove.entity.ProductModel;
import com.monopoco.productmove.entityDTO.ProductDTO;
import com.monopoco.productmove.entityDTO.ProductModelDTO;
import com.monopoco.productmove.repository.CapacityRepository;
import com.monopoco.productmove.repository.ColorRepository;
import com.monopoco.productmove.repository.ProductModelRepository;
import com.monopoco.productmove.repository.ProductRepository;
import com.monopoco.productmove.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;

@Service
@Transactional
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
    public ProductDTO save(ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO getById(Long id) {
        return null;
    }

    @Override
    public void addNewColor(String color, String code) {
        colorRepository.save(new Color(null, color, code, null));
    }

    @Override
    public void addNewCapacity(Integer capacity) {
        capacityRepository.save(new Capacity(null, capacity, null));
    }

    @Override
    public List<Map<String, String>> getAllColor() {
        List<Map<String, String>> colorString = new ArrayList<>();
        List<Color> colors = colorRepository.findAll();
        colors.forEach(color -> {
            Map<String, String> colorI = new HashMap<>();
            colorI.put("color", color.getColor());
            colorI.put("code", color.getCode());
            colorString.add(colorI);
        });

        return colorString;
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
    public List<String> getColorCode(List<String> colors) {
        List<String> colorCode = new ArrayList<>();
        colors.forEach(color -> {
            Color colorDB = colorRepository.findByColor(color);
            colorCode.add(colorDB.getCode());
        });
        return colorCode;
    }
}

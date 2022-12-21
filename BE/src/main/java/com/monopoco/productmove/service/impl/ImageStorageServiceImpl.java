package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.ImageData;
import com.monopoco.productmove.repository.ImageStorageRepository;
import com.monopoco.productmove.service.ImageStorageService;
import com.monopoco.productmove.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class ImageStorageServiceImpl implements ImageStorageService {

    @Autowired
    private ImageStorageRepository repository;


    @Override
    public Long uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());

        if (imageData != null) {
            return imageData.getId();
        }

        return null;
    }

    @Override
    public byte[] downloadImage(Long id) {
        Optional<ImageData> dbImageData = repository.findById(id);
        if(dbImageData.isPresent()) {
            byte[] images = ImageUtils.decompressImage(dbImageData.get().getImageData());
            return images;
        }
        return null;
    }
}

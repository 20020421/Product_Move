package com.monopoco.productmove.api;

import com.monopoco.productmove.entity.ImageData;
import com.monopoco.productmove.entityDTO.UserDTO;
import com.monopoco.productmove.requestentity.UserRequestForm;
import com.monopoco.productmove.service.ImageStorageService;
import com.monopoco.productmove.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ModelMapper modelMapper;



    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllUser(
            @RequestParam(required = false) String branch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<UserDTO> userDTOs = service.getAllWithPaging(paging);
        List<UserDTO> userDTOList = userDTOs.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("users", userDTOList);
        response.put("currentPage", userDTOs.getNumber());
        response.put("totalItems", userDTOs.getTotalElements());
        response.put("totalPages", userDTOs.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity<?> saveUser(@ModelAttribute UserRequestForm userRequestForm) throws IOException {
        log.info(userRequestForm.toString());
        UserDTO userDTO = modelMapper.map(userRequestForm, UserDTO.class);
        if (userRequestForm.getAvatarFile() != null) {
            Long avatarId = imageStorageService.uploadImage(userRequestForm.getAvatarFile());
            if (avatarId != null && avatarId > 0) {
                userDTO.setAvatar_id(avatarId);
            }
        }
        UserDTO userSaved = service.addNewUser(userDTO);
        if (userSaved != null && userSaved.getId() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Save user failed!");
        }
    }

    @PostMapping("/uploadimage")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        Long image_id = imageStorageService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.CREATED).body(image_id);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable String id) {
        byte[] imageData = imageStorageService.downloadImage(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }



}

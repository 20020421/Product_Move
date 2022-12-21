package com.monopoco.productmove.api;

import com.monopoco.productmove.entity.ImageData;
import com.monopoco.productmove.entityDTO.UserDTO;
import com.monopoco.productmove.requestentity.UserRequestForm;
import com.monopoco.productmove.service.ImageStorageService;
import com.monopoco.productmove.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok().body(service.getAllUser());
    }

    @PostMapping("")
    public ResponseEntity<?> saveUser(@ModelAttribute UserRequestForm userRequestForm) throws IOException {
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

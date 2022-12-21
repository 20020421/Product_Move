package com.monopoco.productmove.requestentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestForm {
    private String username;
    private String password;

    private String roleName;

    private MultipartFile avatarFile;
}

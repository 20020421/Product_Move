package com.monopoco.productmove.entityDTO;

import com.monopoco.productmove.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;

    private String roleName;

    private String branchName;

    private String createdAt;

    private Long avatar_id;

}

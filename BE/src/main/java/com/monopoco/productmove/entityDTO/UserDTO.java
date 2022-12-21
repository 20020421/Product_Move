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

    private Long avatar_id;

}

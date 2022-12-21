package com.monopoco.productmove.service;

import com.monopoco.productmove.entity.Role;
import com.monopoco.productmove.entityDTO.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUser();

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);

    void deleteUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void addRoleToUser(String username, String roleName);

    void addAvatarToUser(String username, Long avatarId);

    UserDTO addNewUser(UserDTO userDTO);

    Role addNewRole(Role role);
}

package com.monopoco.productmove.service.impl;

import com.monopoco.productmove.entity.ImageData;
import com.monopoco.productmove.entity.Role;
import com.monopoco.productmove.entity.User;
import com.monopoco.productmove.entityDTO.UserDTO;
import com.monopoco.productmove.principal.UserPrincipal;
import com.monopoco.productmove.repository.ImageStorageRepository;
import com.monopoco.productmove.repository.RoleRepository;
import com.monopoco.productmove.repository.UserRepository;
import com.monopoco.productmove.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ImageStorageRepository imageStorageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<UserDTO> getAllUser() {
        List<User> userList = userRepository.findAll();

        List<UserDTO> userDTOList = new ArrayList<>();

        log.info("Fetching all user......");

        userList.forEach(user -> {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setRoleName(user.getRole().getName());
            userDTOList.add(userDTO);
        });

        return userDTOList;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return null;
    }

    @Override
    public void deleteUser(UserDTO userDTO) {

    }

    @Override
    public void updateUser(UserDTO userDTO) {

    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findUserByUsername(username);
        Role role = roleRepository.findRoleByName(roleName);
        user.setRole(role);
        log.info("Add role {} to user {} successful", role.getName(), user.getUsername());
    }

    @Override
    public void addAvatarToUser(String username, Long avatarId) {
        User user = userRepository.findUserByUsername(username);
        Optional<ImageData> avatar = imageStorageRepository.findById(avatarId);
        if (avatar.isPresent()) {
            user.setImageData(avatar.get());
            log.info("Add avatar to username {} successfully", username);
        } else {
            log.warn("Failed to add avatar to user");
        }
    }

    @Override
    public UserDTO addNewUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userDTO.getAvatar_id() != null && userDTO.getAvatar_id() > 0) {
            user.setImageData(imageStorageRepository.findById(userDTO.getAvatar_id()).get());
        }
        if (userDTO.getRoleName() != null) {
            Role role = roleRepository.findRoleByName(userDTO.getRoleName());
            if (role != null) {
                user.setRole(role);
            } else {
                Role newRole = new Role(null, userDTO.getRoleName(), null);
                Role roleSaved = roleRepository.save(newRole);
                user.setRole(roleSaved);
            }
        }
        log.info("Saving a new user: {}", user.getUsername());
        User userSaved = userRepository.save(user);
        return modelMapper.map(userSaved, UserDTO.class);
    }

    @Override
    public Role addNewRole(Role role) {
        log.info("Saving a new role: {}", role.getName());
        Role roleSaved = roleRepository.save(role);
        return roleSaved;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            log.warn("User {} not found in the database", username);
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", user.getUsername());
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            if (user.getRole() != null) {
                userDTO.setRoleName(user.getRole().getName());
            }
            return new UserPrincipal(userDTO);
        }

    }
}

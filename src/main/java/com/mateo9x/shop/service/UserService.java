package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.UserDTO;

public interface UserService {

    UserDTO save(UserDTO userDTO);
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    void deleteUser(Long id);
    
}

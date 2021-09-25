package com.mateo9x.shop.service;

import java.util.List;

import com.mateo9x.shop.dto.UserDTO;

public interface UserService {

    UserDTO save(UserDTO userDTO);

    void resetPswd(String mail);

    Boolean updateUserPassword(UserDTO userDTO);

    Boolean updateUserPasswordFromToken(UserDTO userDTO);

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO findByResetToken(UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO getUsernameLogged();

}

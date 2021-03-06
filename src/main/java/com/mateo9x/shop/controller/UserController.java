package com.mateo9x.shop.controller;

import java.util.List;
import javax.validation.Valid;

import com.mateo9x.shop.dto.UserDTO;
import com.mateo9x.shop.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to save User: {}", userDTO);
        return userService.save(userDTO);
    }

    @PostMapping("/users/resetPassword")
    public void resetPassword(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to reset password for User: {}", userDTO.getMail());
        userService.resetPswd(userDTO.getMail());
    }

    @PutMapping("/users")
    public UserDTO updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User: {}", userDTO);
        return userService.save(userDTO);
    }

    @PutMapping("/users/password")
    public Boolean updatePassword(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User password: {}", userDTO);
        return userService.updateUserPassword(userDTO);
    }

    @PutMapping("/users/password/token")
    public Boolean updatePasswordByToken(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User password from token: {}", userDTO);
        return userService.updateUserPasswordFromToken(userDTO);
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        log.debug("REST request to get all Users");
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        log.debug("REST request to get User: {}", id);
        return userService.findById(id);
    }

    @PostMapping("/users/token-user")
    public UserDTO getUserByToken(@RequestBody @Valid UserDTO userDTO) {
        log.debug("REST request to get User by token: {}");
        return userService.findByResetToken(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.debug("REST request do delete User: {}", id);
        userService.deleteUser(id);
    }

    
    @GetMapping("/users/logged")
    public UserDTO getUserLoggedUsername() {
        log.debug("REST request to get User logged username");
        return userService.getUsernameLogged();
    }

}

package com.mateo9x.shop.controller;

import javax.validation.Valid;

import com.mateo9x.shop.dto.UserDTO;
import com.mateo9x.shop.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to save User: {}", userDTO);
        return userService.save(userDTO);
    }

    @GetMapping("/users/resetPassword/{mail}")
    public void resetPassword(@PathVariable String mail) {
        log.debug("REST request to reset password for User with mail: {}", mail);
        userService.resetPassword(mail);
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

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        log.debug("REST request to get User: {}", id);
        return userService.findById(id);
    }

    @GetMapping("/users/mail/{mail}")
    public Boolean doesUserWithEmailExists(@PathVariable String mail) {
        log.debug("REST request to find User by mail: {}", mail);
        return userService.doesUserWithEmailExists(mail);
    }

    @PostMapping("/users/token-user")
    public UserDTO getUserByToken(@RequestBody @Valid UserDTO userDTO) {
        log.debug("REST request to get User by token: {}", userDTO.getResetToken());
        return userService.findByResetToken(userDTO);
    }

    @GetMapping("/users/logged")
    public UserDTO getUserLoggedUsername() {
        log.debug("REST request to get User logged username");
        return userService.getUsernameLogged();
    }

}

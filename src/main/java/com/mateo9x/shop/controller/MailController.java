package com.mateo9x.shop.controller;

import javax.validation.Valid;

import com.mateo9x.shop.dto.UserDTO;
import com.mateo9x.shop.service.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/new-user-welcome-email")
    public void newUserSendWelcomeEmail(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to send welcome email for new User: {}", userDTO);
        mailService.newUserEmailMessage(userDTO);
    }

}

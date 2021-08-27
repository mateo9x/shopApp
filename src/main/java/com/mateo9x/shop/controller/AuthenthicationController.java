package com.mateo9x.shop.controller;
import javax.validation.Valid;

import com.mateo9x.shop.configuration.LoginCredentials;
import com.mateo9x.shop.service.AuthenthicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthenthicationController {

    private final Logger log = LoggerFactory.getLogger(AuthenthicationController.class);
    private AuthenthicationService authenthicationService;

    public AuthenthicationController(AuthenthicationService authenthicationService) {
        this.authenthicationService = authenthicationService;
    }

    @PostMapping("login")
    public Boolean signIn(@Valid @RequestBody LoginCredentials model) {
        log.debug("REST request to login User: {}", model.getUsername());
        return authenthicationService.logIn(model);
    }

    @GetMapping("logout")
    public Boolean logOut(@Valid @RequestBody LoginCredentials model) {
        log.debug("REST request to logout User: {}", model.getUsername());
        return authenthicationService.logOut(model);
    }

    @GetMapping("is-user-logged")
    public Boolean isUserLogged() {
        log.debug("REST request to check if User is logged");
        return authenthicationService.isUserLogged();
    }
    
}

package com.mateo9x.shop.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mateo9x.shop.configuration.LoginCredentials;

public interface AuthenthicationService {

    Boolean logIn(LoginCredentials model);

    void logOut(HttpServletRequest request, HttpServletResponse response);

    Boolean isUserLogged();
    
}

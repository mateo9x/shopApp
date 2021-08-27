package com.mateo9x.shop.service;

import com.mateo9x.shop.configuration.LoginCredentials;

public interface AuthenthicationService {

    Boolean logIn(LoginCredentials model);

    Boolean logOut(LoginCredentials model);

    Boolean isUserLogged();
    
}

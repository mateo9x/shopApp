package com.mateo9x.shop.serviceImpl;

import com.mateo9x.shop.configuration.LoginCredentials;
import com.mateo9x.shop.repository.UserRepository;
import com.mateo9x.shop.service.AuthenthicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenthicationServiceImpl implements AuthenthicationService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    private UserDetailsServiceImpl userDetailsServiceImpl;

    public AuthenthicationServiceImpl(UserRepository userRepository, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    public Boolean logOut(LoginCredentials model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean logIn(LoginCredentials model) {

        if (userDetailsServiceImpl.loadUserByUsername(model.getUsername()) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean isUserLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() != "anonymousUser") {
            return true;
        } else {
            return false;
        }
    }

}

package com.mateo9x.shop.serviceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mateo9x.shop.configuration.LoginCredentials;
import com.mateo9x.shop.repository.UserRepository;
import com.mateo9x.shop.service.AuthenthicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthenthicationServiceImpl implements AuthenthicationService {

    private final Logger log = LoggerFactory.getLogger(AuthenthicationServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    private UserDetailsServiceImpl userDetailsServiceImpl;

    public AuthenthicationServiceImpl(UserRepository userRepository, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    public void logOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        } else {
            log.info("User already logged out");
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

package com.mateo9x.shop.serviceImpl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mateo9x.shop.configuration.LoginCredentials;
import com.mateo9x.shop.domain.User;
import com.mateo9x.shop.dto.UserDTO;
import com.mateo9x.shop.mapper.UserMapper;
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

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private UserRepository userRepository;
    private UserMapper userMapper;

    public AuthenthicationServiceImpl(UserDetailsServiceImpl userDetailsServiceImpl, UserRepository userRepository,
            UserMapper userMapper) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
    public UserDTO isUserLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (auth.isAuthenticated() && auth.getPrincipal() != "anonymousUser") {
            UserDTO userDTO = userMapper.toDTO(user.get());
            return userDTO;
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(auth.getPrincipal().toString());
            userDTO.setId(9999L);
            return userDTO;
        }
    }

}

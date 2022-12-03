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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenthicationServiceImpl implements AuthenthicationService {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
        return userDetailsServiceImpl.loadUserByUsername(model.getUsername()) != null;
    }

    @Override
    public UserDTO isUserLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userRepository.findByUsername(auth.getPrincipal().toString());
        if (auth.isAuthenticated() && auth.getPrincipal() != "anonymousUser") {
            return userMapper.toDTO(user.get());
        }
        return null;
    }
}

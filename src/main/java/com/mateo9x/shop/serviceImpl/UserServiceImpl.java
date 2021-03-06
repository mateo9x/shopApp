package com.mateo9x.shop.serviceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mateo9x.shop.domain.User;
import com.mateo9x.shop.dto.UserDTO;
import com.mateo9x.shop.mapper.UserMapper;
import com.mateo9x.shop.repository.UserRepository;
import com.mateo9x.shop.service.MailService;
import com.mateo9x.shop.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MailService mailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Request to delete User: {}", id);
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }

    @Override
    public List<UserDTO> findAll() {
        log.info("Request to find all Users: ");
        return userRepository.findAll().stream().map(userMapper::toDTO)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public UserDTO findById(Long id) {
        log.info("Request to find User: {}", id);
        User user = userRepository.getById(id);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO findByResetToken(UserDTO userDTO) {
        log.info("Request to find User by Reset Token: {}");
        Optional<User> user = userRepository.findByResetToken(userDTO.getResetToken());
        if (user.isPresent()) {
            return userMapper.toDTO(user.get());
        } else {
            return null;
        }
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByMail(userDTO.getMail());
        if (userOptional.isPresent()) {
            log.error("User with that email already exists :", userDTO.getMail());
            return null;
        } else {
            log.info("Request to save User: {}", userDTO);
            User user = userMapper.toEntity(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUsername(user.getUsername().toLowerCase());
            user = userRepository.save(user);
            return userMapper.toDTO(user);
        }
    }

    @Override
    public Boolean updateUserPassword(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByMail(userDTO.getMail());
        if (userOptional.isPresent()) {
            if (!passwordEncoder.matches(userDTO.getPassword(), userOptional.get().getPassword())) {
                log.info("Request to update User password: {}:", userDTO.getUsername());
                userOptional.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userRepository.save(userOptional.get());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public UserDTO getUsernameLogged() {
        Object username = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(username.toString());
        if (user.isPresent()) {
            return userMapper.toDTO(user.get());
        } else {
            log.info("User not found: {}");
            return null;
        }
    }

    @Override
    public void resetPswd(String mail) {
        UUID uuid = UUID.randomUUID();
        Optional<User> user = userRepository.findByMail(mail);
        if (user.isPresent()) {
            user.get().setResetToken(uuid.toString());
            userRepository.save(user.get());
            UserDTO dto = userMapper.toDTO(user.get());
            mailService.sendResetPasswordToken(dto);
        }
    }

    @Override
    public Boolean updateUserPasswordFromToken(UserDTO userDTO) {
        Optional<User> user = userRepository.findByResetToken(userDTO.getResetToken());
        if (user.isPresent()) {
            if (!passwordEncoder.matches(userDTO.getPassword(), user.get().getPassword())) {
                log.info("Request to update User password: {}:", userDTO.getUsername());
                user.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));
                user.get().setResetToken(null);
                userRepository.save(user.get());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}

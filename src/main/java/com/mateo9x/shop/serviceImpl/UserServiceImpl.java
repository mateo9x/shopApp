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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;

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
        log.info("Request to find User by Reset Token");
        Optional<User> user = userRepository.findByResetToken(userDTO.getResetToken());
        return user.map(userMapper::toDTO).orElse(null);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByMail(userDTO.getMail());
        if (userOptional.isPresent()) {
            log.error("User with that email already exists: {}", userDTO.getMail());
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
        Optional<User> userSavedOnBaseOptional = userRepository.findByMail(userDTO.getMail());
        if (userSavedOnBaseOptional.isPresent()) {
            if (!doesBothPasswordMatches(userDTO, userSavedOnBaseOptional.get())) {
                log.info("Request to update User password: {}:", userDTO.getUsername());
                userSavedOnBaseOptional.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userRepository.save(userSavedOnBaseOptional.get());
                return true;
            }
        }
        return false;
    }

    @Override
    public UserDTO getUsernameLogged() {
        Object username = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(username.toString());
        if (user.isPresent()) {
            return userMapper.toDTO(user.get());
        }
        log.info("User not found!");
        return null;
    }

    @Override
    public void resetPassword(String mail) {
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
        Optional<User> userSavedOnBaseOptional = userRepository.findByResetToken(userDTO.getResetToken());
        if (userSavedOnBaseOptional.isPresent()) {
            User userSavedOnBase = userSavedOnBaseOptional.get();
            if (!doesBothPasswordMatches(userDTO, userSavedOnBase)) {
                log.info("Request to update User password: {}:", userDTO.getUsername());
                userSavedOnBase.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userSavedOnBase.setResetToken(null);
                userRepository.save(userSavedOnBase);
                return true;
            }
        }
        return false;
    }

    private boolean doesBothPasswordMatches(UserDTO userDTO, User userSavedOnBase) {
        return passwordEncoder.matches(userDTO.getPassword(), userSavedOnBase.getPassword());
    }
}

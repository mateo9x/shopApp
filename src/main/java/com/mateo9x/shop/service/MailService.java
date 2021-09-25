package com.mateo9x.shop.service;

import com.mateo9x.shop.dto.UserDTO;

public interface MailService {

    void newUserEmailMessage(UserDTO userDTO);

    void sendResetPasswordToken(UserDTO userDTO);

}

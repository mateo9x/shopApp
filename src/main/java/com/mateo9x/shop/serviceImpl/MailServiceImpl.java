package com.mateo9x.shop.serviceImpl;

import com.mateo9x.shop.dto.UserDTO;
import com.mateo9x.shop.service.MailService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void newUserEmailMessage(UserDTO userDTO) {
        String user;
        if (isFirstNameAndLastNameNotNull(userDTO)) {
            user = userDTO.getFirstName() + " " + userDTO.getLastName() + " (" + userDTO.getUsername() + ")";
        } else {
            user = userDTO.getUsername();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@onlineshop.com");
        message.setTo(userDTO.getMail());
        message.setSubject("Online Shop - Konto");
        message.setText("Witaj " + user
                + "!\n\nTwoje konto zostało pomyślnie utworzone w serwisie Online Shop.\n\nDziękujemy i zapraszamy do korzystania z aplikacji!"
                + "\n\nOnline Shop\nul. Programistów 3\n40-400 Warszawa\nKRS: XXXXXXXXXX");
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Nie udało się wysłać maila powitalnego");
        }
    }

    @Override
    public void sendResetPasswordToken(UserDTO userDTO) {
        String user;
        String url = "http://localhost:4200/#/new-password?" + userDTO.getResetToken();
        if (isFirstNameAndLastNameNotNull(userDTO)) {
            user = userDTO.getFirstName() + " " + userDTO.getLastName() + " (" + userDTO.getUsername() + ")";
        } else {
            user = userDTO.getUsername();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@onlineshop.com");
        message.setTo(userDTO.getMail());
        message.setSubject("Online Shop - Konto");
        message.setText("Witaj " + user + "!\n\nPoniżej znajduje się link do zresetowania hasła:\n\n" + url);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Nie udało się wysłać maila resetującego hasła");
        }
    }

    private boolean isFirstNameAndLastNameNotNull(UserDTO userDTO) {
        return userDTO.getFirstName() != null && userDTO.getLastName() != null;
    }

}

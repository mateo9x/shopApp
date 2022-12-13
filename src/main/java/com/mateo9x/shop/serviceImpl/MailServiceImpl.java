package com.mateo9x.shop.serviceImpl;

import com.mateo9x.shop.configuration.AdditionalAppProperties;
import com.mateo9x.shop.dto.UserDTO;
import com.mateo9x.shop.service.MailService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final AdditionalAppProperties additionalAppProperties;

    @Override
    public void newUserEmailMessage(UserDTO userDTO) {
        String user;
        if (isFirstNameAndLastNameNotNull(userDTO)) {
            user = userDTO.getFirstName() + " " + userDTO.getLastName() + " (" + userDTO.getUsername() + ")";
        } else {
            user = userDTO.getUsername();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@wszystkowsieci.pl");
        message.setTo(userDTO.getMail());
        message.setSubject("Wszystko w sieci - rejestracja użytkownika");
        message.setText("Witaj " + user
                + "!\n\nTwoje konto zostało pomyślnie utworzone w serwisie Wszystko w sieci.\n\nDziękujemy i zapraszamy do korzystania z aplikacji!"
                + "\n\nWszystko w sieci\nul. Programistów 3\n40-400 Warszawa\nKRS: XXXXXXXXXX");
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Nie udało się wysłać maila powitalnego: {}", e.getMessage());
        }
    }

    @Override
    public void sendResetPasswordToken(UserDTO userDTO) {
        String user;
        String url = additionalAppProperties.getFrontendUrl() + "/#/new-password?" + userDTO.getResetToken();
        if (isFirstNameAndLastNameNotNull(userDTO)) {
            user = userDTO.getFirstName() + " " + userDTO.getLastName() + " (" + userDTO.getUsername() + ")";
        } else {
            user = userDTO.getUsername();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@wszystkowsieci.pl");
        message.setTo(userDTO.getMail());
        message.setSubject("Online Shop - Konto");
        message.setText("Witaj " + user + "!\n\nPoniżej znajduje się link do zresetowania hasła:\n\n" + url);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Nie udało się wysłać maila resetującego hasła: {}", e.getMessage());
        }
    }

    @Override
    public void notifySellerAboutHisItemProductBuy(String message, String to) {
        SimpleMailMessage spm = new SimpleMailMessage();
        spm.setFrom("noreply@wszystkowsieci.pl");
        spm.setTo(to);
        spm.setSubject("Wszystko w sieci - zakupiono produkt");
        spm.setText(message);
        try {
            javaMailSender.send(spm);
            log.info("Wysłano maila do sprzedawcy: {} o zakupie jednego z jego produktów", to);
        } catch (Exception e) {
            log.error("Nie udało się wysłać maila resetującego hasła: {}", e.getMessage());
        }
    }

    private boolean isFirstNameAndLastNameNotNull(UserDTO userDTO) {
        return userDTO.getFirstName() != null && userDTO.getLastName() != null;
    }
}

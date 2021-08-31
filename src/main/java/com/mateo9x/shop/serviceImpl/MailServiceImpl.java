package com.mateo9x.shop.serviceImpl;

import com.mateo9x.shop.dto.UserDTO;
import com.mateo9x.shop.service.MailService;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void newUserEmailMessage(UserDTO dto) {

        String user = "";
        if (dto.getFirstName() != null && dto.getLastName() != null) {
            user = dto.getFirstName() + " " + dto.getLastName() + " (" + dto.getUsername() + ")";
        } else {
            user = dto.getUsername();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@onlineshop.com");
        message.setTo(dto.getMail());
        message.setSubject("Online Shop - Konto");
        message.setText("Witaj " + user
                + "!\n\nTwoje konto zostało pomyślnie utworzone w serwisie Online Shop.\n\nDziękujemy i zapraszamy do korzystania z aplikacji!"
                + "\n\nOnline Shop\nul. Programistów 3\n40-400 Warszawa\nKRS: XXXXXXXXXX");
        javaMailSender.send(message);
    }

}

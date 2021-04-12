package com.comp3000.project.cms.DAL.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void notifyApplicant(
            String to, String firstName, boolean approved) {
        this.notifyApplicant(to, firstName, "", approved);
    }

    public void notifyApplicant(
            String to, String firstName, String password, boolean approved) {

        String text = "Dear, " + firstName + "\nYour application has been "
                + (approved ? "approved" : "denied");

        if (approved) {
            text += "\n\nYour login is " + to + ", password: " + password;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("CMS3004: Application status");
        message.setText(text);
        emailSender.send(message);
    }
}

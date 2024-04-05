package com.natwest.notificationservice.service;

import com.natwest.notificationservice.exception.UserNotFoundException;
import com.natwest.notificationservice.model.User;
import com.natwest.notificationservice.utility.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailSenderService {
    private final JavaMailSender javaMailSender;
    @Autowired
    private final RestTemplate restTemplate;

    private String userEmail;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender, RestTemplate restTemplate) {
        this.javaMailSender = javaMailSender;
        this.restTemplate = restTemplate;
    }

    public User fetchUser(String username) throws UserNotFoundException {
        String url = "http://localhost:9092/api/v1/user/v1/" + username;
        try {
            return restTemplate.getForObject(url, User.class);
        } catch (HttpClientErrorException ex) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
    }

    public String storeUserEmail(String username) throws UserNotFoundException {
        if (userEmail == null) {
            User user = fetchUser(username);
            if (user != null) {
                userEmail = user.getEmail();
            }
        }
        return userEmail;
    }

    public void sendEmail(String username) throws UserNotFoundException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nehasaraf22@navgurukul.org");
        storeUserEmail(username);
        message.setTo(userEmail);
        message.setText(AppConstant.NOTIFICATION_REGISTRATION_MESSAGE);
        message.setSubject(AppConstant.NOTIFICATION_SUBJECT_MESSAGE);
        userEmail = null;
        javaMailSender.send(message);
    }
}
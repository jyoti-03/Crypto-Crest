package com.natwest.notificationservice.controller;

import com.natwest.notificationservice.exception.UserNotFoundException;
import com.natwest.notificationservice.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailSendController {
    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/send/{username}")
    public String sendMail(@PathVariable String username) throws UserNotFoundException {
        emailSenderService.sendEmail(username);
        return "Email sent Successfully";
    }
}


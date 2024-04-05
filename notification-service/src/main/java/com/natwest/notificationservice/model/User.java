package com.natwest.notificationservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class User {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String state;
    private String country;
    private String phoneNumber;
    private String email;
    private String username;
}

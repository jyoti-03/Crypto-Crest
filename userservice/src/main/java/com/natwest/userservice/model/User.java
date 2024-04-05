package com.natwest.userservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @NotEmpty(message = "Please provide your first name")
    @Pattern(regexp = "^[A-Za-z]+$",message = "Please Enter valid name.\nName must be contain only characters ")
    private String firstName;

    @NotEmpty(message = "Please provide your last name")
    @Pattern(regexp = "^[A-Za-z]+$",message = "Please Enter valid name.\nName must be contain only characters ")
    private String lastName;

    @Column(name = "dob")
    @NotNull(message = "Please provide your date of birth")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Please specify your gender")
    @Pattern(regexp="^(Male|Female|Transgender)$", message = "Gender must be 'male', 'female', or 'transgender'")
    private String gender;

    @NotEmpty(message = "Please provide your state")
    private String state;

    @NotEmpty(message = "Please provide your country")
    private String country;

    @Column(unique = true)
    @Pattern(regexp="\\d{10}", message = "Please provide a valid 10-digit phone number")
    private String phoneNumber;

    @Column(unique = true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message = "Please provide a valid email address")
    private String email;

    @Min(value = 1000L, message = "Minimum Account Balance Required 1000 Rs")
    private double accountBalance;

    @Id
    @NotEmpty(message = "Please choose a username")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    private String password;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime registrationDate = LocalDateTime.now();
}


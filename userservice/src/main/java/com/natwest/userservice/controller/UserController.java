package com.natwest.userservice.controller;

import com.natwest.userservice.exception.InvalidAgeException;
import com.natwest.userservice.exception.MandatoryFieldException;
import com.natwest.userservice.exception.UserAlreadyExistException;
import com.natwest.userservice.exception.UserNotFoundException;
import com.natwest.userservice.model.User;
import com.natwest.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")

    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) throws UserAlreadyExistException, InvalidAgeException, MandatoryFieldException {
        return new ResponseEntity<>(userService.registerNewUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllRegisteredUser(){
        return new ResponseEntity<>(userService.getAllRegisteredUser(), HttpStatus.OK);
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user,@PathVariable String username) throws UserNotFoundException, MandatoryFieldException {
        return new ResponseEntity<>(userService.updateUserInformation(user, username),HttpStatus.OK);
    }

    @GetMapping("/user/{emailId}")
    public ResponseEntity<User> getUserByUsingEmailId(@PathVariable String emailId) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUserByEmailId(emailId),HttpStatus.OK);
    }

    @GetMapping("/user/v1/{username}")
    public ResponseEntity<User> getUserByUsingUsername(@PathVariable String username) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUserByUsername(username),HttpStatus.OK);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<String> deleteUserByUsingUsername(@PathVariable String username) throws UserNotFoundException {
        return new ResponseEntity<>(userService.deleteUserByUsername(username),HttpStatus.OK);
    }

    @GetMapping("/user/pass/{username}")
    public ResponseEntity<String> getPasswordByUsername(@PathVariable String username) throws UserNotFoundException{
        return new ResponseEntity<>(userService.getPasswordByUsername(username),HttpStatus.OK);
    }
}

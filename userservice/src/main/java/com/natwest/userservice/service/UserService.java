package com.natwest.userservice.service;

import com.natwest.userservice.exception.InvalidAgeException;
import com.natwest.userservice.exception.MandatoryFieldException;
import com.natwest.userservice.exception.UserAlreadyExistException;
import com.natwest.userservice.exception.UserNotFoundException;
import com.natwest.userservice.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface UserService {
    String registerNewUser(User user) throws UserAlreadyExistException, InvalidAgeException, MandatoryFieldException;

    String updateUserInformation(User user,String username) throws UserNotFoundException, MandatoryFieldException;
    String deleteUserByUsername(String username) throws UserNotFoundException;

    List<User> getAllRegisteredUser();
    User getUserByUsername(String username)throws UserNotFoundException;
    User getUserByEmailId(String emailId) throws UserNotFoundException;

    String getPasswordByUsername(String username) throws UserNotFoundException;
}

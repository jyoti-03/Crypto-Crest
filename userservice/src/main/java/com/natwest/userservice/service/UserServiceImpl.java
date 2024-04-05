package com.natwest.userservice.service;

import com.natwest.userservice.exception.InvalidAgeException;
import com.natwest.userservice.exception.MandatoryFieldException;
import com.natwest.userservice.exception.UserAlreadyExistException;
import com.natwest.userservice.exception.UserNotFoundException;
import com.natwest.userservice.model.User;
import com.natwest.userservice.repository.UserRepository;
import com.natwest.userservice.utility.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate,UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository= userRepository;
    }
    public String sendMail(String username){
        String url="http://localhost:9090/mail/send/"+username;
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        if (response != null && response.getBody() != null) {
            return response.getBody();
        }else {
            return "Failed to send mail";
        }
    }

    @Override
    public String registerNewUser(User user) throws UserAlreadyExistException, InvalidAgeException, MandatoryFieldException {
        if (userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistException(AppConstant.USER_ALREADY_EXIST_MESSAGE);
        }
        int age=Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
        if(age<=18){
            throw new InvalidAgeException(AppConstant.INVALID_AGE_MESSAGE);
        }
        if(userRepository.findUserByEmail(user.getEmail())!=null){
            throw new MandatoryFieldException(AppConstant.VALIDATE_EMAIL_ID_MESSAGE);
        }
        userRepository.save(user);
        sendMail(user.getUsername());
        return AppConstant.USER_ADDED_SUCCESSFULLY;
    }

    @Override
    public String updateUserInformation(User user, String username) throws UserNotFoundException, MandatoryFieldException {
        Optional<User> existingUser = userRepository.findById(username);
        if (existingUser.isPresent()) {
            if(!existingUser.get().getFirstName().equals(user.getFirstName())){
                throw new MandatoryFieldException(AppConstant.INVALID_FIRST_NAME_MESSAGE);
            }
            if (!existingUser.get().getLastName().equals(user.getLastName())){
                throw new MandatoryFieldException(AppConstant.INVALID_LAST_NAME_MESSAGE);
            }
            if(!existingUser.get().getDateOfBirth().equals(user.getDateOfBirth())){
                throw new MandatoryFieldException(AppConstant.INVALID_DATE_OF_BIRTH_MESSAGE);
            }
            if(!existingUser.get().getEmail().equals(user.getEmail())){
                throw new MandatoryFieldException(AppConstant.INVALID_EMAIL_ID_MESSAGE);
            }
            if(!existingUser.get().getUsername().equals(user.getUsername())){
                throw new MandatoryFieldException(AppConstant.INVALID_USERNAME_MESSAGE);
            }
            userRepository.save(user);
            return AppConstant.USER_INFORMATION_UPDATE_SUCCESSFULLY;
        }
        throw new UserNotFoundException(AppConstant.USER_NOT_FOUND_MESSAGE);
    }

    @Override
    public String deleteUserByUsername(String username) throws UserNotFoundException {
        if(userRepository.existsById(username)) {
            userRepository.deleteById(username);
            return AppConstant.USER_DELETE_MESSAGE;
        }
        throw new UserNotFoundException(AppConstant.USER_NOT_FOUND_MESSAGE);
    }

    @Override
    public List<User> getAllRegisteredUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findById(username).orElseThrow(()-> new UserNotFoundException(AppConstant.USER_NOT_FOUND_MESSAGE));
    }

    @Override
    public User getUserByEmailId(String emailId) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(emailId);
        if (user != null) {
            return user;
        }
        throw new UserNotFoundException(AppConstant.USER_NOT_FOUND_MESSAGE);
    }

    @Override
    public String getPasswordByUsername(String username) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(AppConstant.USER_NOT_FOUND_MESSAGE);
        }
        return userOptional.get().getPassword();
    }
}

package com.natwest.userservice.service;

import com.natwest.userservice.exception.InvalidAgeException;
import com.natwest.userservice.exception.MandatoryFieldException;
import com.natwest.userservice.exception.UserAlreadyExistException;
import com.natwest.userservice.exception.UserNotFoundException;
import com.natwest.userservice.model.User;
import com.natwest.userservice.repository.UserRepository;
import com.natwest.userservice.utility.AppConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserServiceImpl userService;
    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        testUser = new User("Kunal", "Singh", LocalDate.of(2000, 5, 15), "Male", "Maharashtra", "India",
                "1234567890", "kunal@example.com", 1200, "kunal123", "password", LocalDateTime.now());

    }

    @Test
    void testRegisterNewUserSuccess() throws UserAlreadyExistException, InvalidAgeException, MandatoryFieldException {
        when(userRepository.existsById(testUser.getUsername())).thenReturn(false);
        when(userRepository.save(testUser)).thenReturn(testUser);
        String result = userService.registerNewUser(testUser);
        verify(userRepository, times(1)).save(testUser);
        assertEquals(AppConstant.USER_ADDED_SUCCESSFULLY, result);
    }

    @Test
    void testRegisterNewUserUserAlreadyExists() {
        when(userRepository.existsById(testUser.getUsername())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUser(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterNewUserInvalidAge() {
        testUser.setDateOfBirth( LocalDate.of(2020, 5, 15));
        assertThrows(InvalidAgeException.class, () -> userService.registerNewUser(testUser));
        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    void testRegisterNewUserMandatoryFields() {
        when(userRepository.existsById(testUser.getUsername())).thenReturn(false);
        when(userRepository.findUserByEmail(testUser.getEmail())).thenReturn(testUser);
        assertThrows(MandatoryFieldException.class, () -> userService.registerNewUser(testUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateUserInformationSuccess() throws UserNotFoundException, MandatoryFieldException {
        when(userRepository.findById(testUser.getUsername())).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);
        String result = userService.updateUserInformation(testUser, testUser.getUsername());
        verify(userRepository, times(1)).findById(testUser.getUsername());
        verify(userRepository, times(1)).save(testUser);
        assertEquals(AppConstant.USER_INFORMATION_UPDATE_SUCCESSFULLY, result);
    }
    @Test
    void testUpdateUserInformationUserNotFound() {
        when(userRepository.existsById(testUser.getUsername())).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.updateUserInformation(testUser, testUser.getUsername()));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUserByUsernameSuccess() throws UserNotFoundException {
        when(userRepository.existsById(testUser.getUsername())).thenReturn(true);
        String result = userService.deleteUserByUsername(testUser.getUsername());
        verify(userRepository, times(1)).existsById(testUser.getUsername());
        verify(userRepository, times(1)).deleteById(testUser.getUsername());
        assertEquals("User deleted successfully", result.trim());
    }
    @Test
    void testDeleteUserByUsernameUserNotFound(){
        when(userRepository.existsById(testUser.getUsername())).thenReturn(false);
        assertThrows(UserNotFoundException.class,()->userService.deleteUserByUsername(testUser.getUsername()));
        verify(userRepository,never()).deleteById(anyString());
    }

    @Test
    void testGetAllRegisteredUser() {
        List<User> userList = List.of(testUser);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.getAllRegisteredUser();
        assertEquals(userList, result);
    }

    @Test
    void testGetUserByUsernameSuccess() throws UserNotFoundException {
        when(userRepository.findById(testUser.getUsername())).thenReturn(Optional.of(testUser));
        User result=userService.getUserByUsername(testUser.getUsername());
        verify(userRepository,times(1)).findById(testUser.getUsername());
        assertEquals(testUser,result);

    }
    @Test
    void testGetUserByUsernameUserNotFound(){
        when(userRepository.findById(testUser.getUsername())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->userService.getUserByUsername(testUser.getUsername()));
        verify(userRepository, times(1)).findById(testUser.getUsername());
    }

    @Test
    void testGetUserByEmailIdSuccess() throws UserNotFoundException {
        when(userRepository.findUserByEmail(testUser.getEmail())).thenReturn(testUser);
        User result=userService.getUserByEmailId(testUser.getEmail());
        verify(userRepository,times(1)).findUserByEmail(testUser.getEmail());
        assertEquals(testUser,result);
    }
    @Test
    void testGetUserByEmailIdUserNotFound() {
        when(userRepository.findUserByEmail(testUser.getEmail())).thenReturn(null);
        assertThrows(UserNotFoundException.class,()->userService.getUserByEmailId(testUser.getEmail()));
        verify(userRepository,times(1)).findUserByEmail(testUser.getEmail());
    }
    @Test
    void testGetPasswordByUsernameSuccess() throws UserNotFoundException {
        when(userRepository.findById(testUser.getUsername())).thenReturn(Optional.of(testUser));
        String result= userService.getPasswordByUsername(testUser.getUsername());
        verify(userRepository,times(1)).findById(testUser.getUsername());
        assertEquals(testUser.getPassword(),result);

    }

    @Test
    void testGetPasswordByUsernameUserNotFound() {
        when(userRepository.findById(testUser.getUsername())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getPasswordByUsername(testUser.getUsername()));
        verify(userRepository, times(1)).findById(testUser.getUsername());
    }

}
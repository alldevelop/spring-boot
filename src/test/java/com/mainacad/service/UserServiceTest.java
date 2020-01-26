package com.mainacad.service;

import com.mainacad.AppRunner;
import com.mainacad.dao.UserDAO;
import com.mainacad.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppRunner.class)
class UserServiceTest {

    @MockBean
    UserDAO userDAO;

    @Autowired
    UserService userService;

    @Test
    void getByLoginAndPassword() {
        User user = new User("testLogin", "testPassword", "testName", "testSurname");
        when(userDAO.getFirstByLoginAndPassword("testLogin", "testPassword")).thenReturn(user);

        User savedUser = userService.getByLoginAndPassword("testLogin", "testPassword");

        assertEquals(savedUser.getFirstName(), "testName");

        verify(userDAO, times(1)).getFirstByLoginAndPassword("testLogin", "testPassword");
    }

    @Test
    void delete() {
        User user = new User("testLogin", "testPassword", "testName", "testSurname");
        doNothing().when(userDAO).delete(any(User.class));

        userService.delete(user);

        verify(userDAO, times(1)).delete(any(User.class));
    }
}
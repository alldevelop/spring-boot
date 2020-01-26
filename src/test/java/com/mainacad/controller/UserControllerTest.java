package com.mainacad.controller;

import com.mainacad.model.User;
import com.mainacad.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void save() throws URISyntaxException {
        User user = new User();
        RequestEntity<User> request = new RequestEntity<>(user, HttpMethod.PUT, new URI("/user"));

        when(userService.save(any(User.class))).thenReturn(user);
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void update() {
    }

    @Test
    void getByLoginAndPassword() {
    }

    @Test
    void getByLoginAndPasswordNegativeCase() {

        URI uri = URI.create("/user/auth");
        RequestEntity request = new RequestEntity("{\"login\":\"ignatenko2207\",\"password\":\"123456\"}", HttpMethod.POST, uri);

        when(userService.getByLoginAndPassword("ignatenko2207", "123456")).thenReturn(null);
        ResponseEntity response =
                testRestTemplate.postForEntity
                        (uri, request, Object.class);

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
        assertTrue(response.getBody() == null);

        verify(userService, times(1))
                .getByLoginAndPassword("ignatenko2207", "123456");

    }


    @Test
    void getUserById() {
    }

    @Test
    void getAllUsers() {
        User user1 = new User();
        User user2 = new User();

        List<User> users = Arrays.asList(user1,user2);
        RequestEntity request = new RequestEntity<>(HttpMethod.GET, URI.create("/user"));

        when(userService.getAll()).thenReturn(users);
        ResponseEntity<List<User>> response = testRestTemplate.exchange(request, new ParameterizedTypeReference<List<User>>() {
        });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(User.class, response.getBody().get(0).getClass());

        verify(userService, times(1)).getAll();
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}

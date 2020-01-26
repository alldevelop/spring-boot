package com.mainacad.dao;

import com.mainacad.AppRunner;
import com.mainacad.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(AppRunner.class)
class UserDAOTest {

    @Autowired
    UserDAO userDAO;

    @Test
    void getAllBySomeFilters() {
        User user = new User("testLogin", "testPassword", "testName", "testSurname");
        User savedUser = userDAO.saveAndFlush(user);

        List<User> foundUser = userDAO.getAllBySomeFilters("testName", "testSurname");

        assertNotNull(foundUser);
        assertTrue(!foundUser.isEmpty());
        assertTrue(foundUser.size() == 1);

        assertEquals(foundUser.get(0).getFirstName(), savedUser.getFirstName());

        try {
            userDAO.deleteById(Integer.MAX_VALUE);
            fail();
        } catch (Exception e) {
            assertTrue( e instanceof RuntimeException );
        }
    }
}

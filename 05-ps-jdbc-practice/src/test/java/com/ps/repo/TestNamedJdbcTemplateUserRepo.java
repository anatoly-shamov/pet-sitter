package com.ps.repo;

import com.ps.config.AppConfig;
import com.ps.config.TestDataConfig;
import com.ps.ents.User;
import com.ps.repos.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Created by iuliana.cosmina on 6/4/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataConfig.class, AppConfig.class})
@ActiveProfiles("dev")
// TODO 30. [BONUS] Write test methods to cover all methods in JdbcNamedTemplateUserRepo
public class TestNamedJdbcTemplateUserRepo {

    @Autowired
    @Qualifier("userNamedTemplateRepo")
    UserRepo userRepo;

    @Before
    public void setUp() {
        assertNotNull(userRepo);
    }

    @Test
    public void testFindById() {
        User user = userRepo.findById(2L);
        assertEquals("Mary", user.getUsername());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testNoFindById() {
        User user = userRepo.findById(99L);
        assertEquals("John", user.getUsername());
    }

    @Test
    public void testFindAll() {
        Set<User> users = userRepo.findAll();
        assertEquals(4, users.size());
    }

    @Test
    public void testCreateDeleteUser() {
        int result = userRepo.createUser(1234L, "Test", "test", "test@test.test");
        assertEquals(1, result);
        result = userRepo.deleteById(1234L);
        assertEquals(1, result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFindAllAsMaps() {
        List<Map<String, Object>> result = userRepo.findAllAsMaps();
        assertEquals(4, result.size());
        result.forEach(m -> assertEquals(4, m.size()));
    }

    @Test
    public void testFindAllByUserName() {
        Set<User> result = userRepo.findAllByUserName("ohn", false);
        assertEquals(2, result.size());
    }

    @Test
    public void testUpdatePassword() {
        int result = userRepo.updatePassword(1L, "newPass");
        assertEquals(1, result);
        User user = userRepo.findById(1L);
        assertEquals("newPass", user.getPassword());
    }

    @Test
    public void testUpdateUsername() {
        int result = userRepo.updateUsername(1L, "Jonhito");
        assertEquals(1, result);
        User user = userRepo.findById(1L);
        assertEquals("Jonhito", user.getUsername());
    }

    @Test
    public void testFindUsernameById() {
        String username = userRepo.findUsernameById(2L);
        assertEquals("Mary", username);
    }

    @Test
    public void testFindByIdAsMap() {
        Map<String, Object> result = userRepo.findByIdAsMap(2L);
        assertEquals(7, result.size());
        assertEquals("Mary", result.get("username"));
    }

    @Test
    public void testCountUsers() {
        int result = userRepo.countUsers();
        assertEquals(4, result);
    }
}

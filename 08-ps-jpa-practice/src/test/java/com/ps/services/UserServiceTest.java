package com.ps.services;

import com.ps.base.PetType;
import com.ps.base.UserType;
import com.ps.config.AppConfig;
import com.ps.config.TestDataConfig;
import com.ps.ents.Pet;
import com.ps.ents.User;
import com.ps.init.DBInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import static com.ps.util.RecordBuilder.buildUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by iuliana.cosmina on 7/15/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataConfig.class, AppConfig.class})
@ActiveProfiles("dev")
public class UserServiceTest {

    @Autowired
    DBInitializer initializer;

    @Autowired
    UserService userService;

    User defaultUser;

    @Before
    public void setUp() {
        assertNotNull(userService);
        initializer.initDb();
        defaultUser = userService.create("ann.lim@example.com", "12334", UserType.SITTER);
    }

    @Test
    public void testFindById() {
        User user = userService.findById(defaultUser.getId());
        assertNotNull(user);
    }
}

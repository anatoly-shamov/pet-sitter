package com.ps.repo;

import com.ps.base.UserType;
import com.ps.config.AppConfig;
import com.ps.config.TestDataConfig;
import com.ps.ents.User;
import com.ps.repos.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by iuliana.cosmina on 6/4/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataConfig.class, AppConfig.class})
@ActiveProfiles("dev")
public class TestJdbcTemplateUserRepo {

    @Autowired
    @Qualifier("userTemplateRepo")
    UserRepo userRepo;

    @Before
    public void setUp() {
        assertNotNull(userRepo);
    }

    @Test
    public void testFindById() {
        User user = userRepo.findById(1L);
        assertEquals("John", user.getUsername());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testNoFindById() {
        User user = userRepo.findById(99L);
        assertEquals("Darius", user.getUsername());
    }

    @Test
    public void testSpecial(){
        //System.out.println(userRepo.findByIdAsMap(1L).toString());
        //System.out.println(userRepo.findAllAsMaps().toString());
        userRepo.htmlAllByName("John");
    }

    @Test
    @SqlGroup({
            @Sql(statements = "delete from p_user where id = 5"),
            @Sql(statements = "delete from p_user where id = 5", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void testCreate(){
        int result  = userRepo.createUser(5L, "Diana", "mypass", "diana@opympus.com", UserType.BOTH);
        assertEquals(1, result);
        Set<User> dianas = userRepo.findAllByUserName("Diana", true);
        assertTrue(dianas.size() == 1);
    }

    @Test
    public void testUpdate(){
        int result  = userRepo.updatePassword(1L, "newpass");
        assertEquals(1, result);
    }

    @Test
    @Sql(statements = "INSERT INTO P_USER(ID, USERNAME, PASSWORD, EMAIL, USER_TYPE) " +
                        "VALUES (4, 'Rob', 'test', 'rob@pet.com', 'OWNER')",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void testDelete(){
        int result  = userRepo.deleteById(4L);
        assertEquals(1, result);
    }

    @Test
    @Sql(statements = "drop table if exists new_p_user")
    public void testCreateTable(){
        int result  = userRepo.createTable("new_p_user");
        // table exists but is empty
        assertEquals(0, result);
    }
}

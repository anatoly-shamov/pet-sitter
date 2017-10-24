package com.ps.web;

import com.ps.ents.User;
import com.ps.problem.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by iuliana.cosmina on 8/15/16.
 */
public class UserControllerTest {

    private UserController userController;

    @Before
    public void setUp(){
        userController = new UserController();
        userController.setUserService(new StubUserService());
    }


    @Test
    public void testFindAllHandler(){
        ExtendedModelMap model = new ExtendedModelMap();
        String viewName = userController.list(model);
        List<User> users = (List<User>) model.get("users");
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(Long.valueOf(0), users.get(0).getId());
        assertEquals("users/list", viewName);
    }

    @Test
    public void testFindOneHandler() throws NotFoundException {
        // TODO 47: Complete this test for the show() method of UserController
        ExtendedModelMap model = new ExtendedModelMap();
        Long id = 1L;
        String viewName = userController.show(id, model);
        User user = (User) model.get("user");
        assertNotNull(user);
        assertEquals(Long.valueOf(1L), user.getId());
        assertEquals("users/show", viewName);
    }
}

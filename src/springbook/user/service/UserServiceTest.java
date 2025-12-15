package springbook.user.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.DUserDao;
import springbook.user.dao.Level;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;
    List<User> users;

    @Before
    public void setUp(){
        users = Arrays.asList(
                new User("test1", "test1", "p1", Level.BASIC, 49, 0),
                new User("test2", "test2", "p2", Level.BASIC, 50, 0),
                new User("test3", "test3", "p3", Level.SILVER, 60, 29),
                new User("test4", "test4", "p4", Level.SILVER, 60, 30),
                new User("test5", "test5", "p5", Level.GOLD, 100, 100)
        );
    }

    @Test
    public void bean(){
        assertThat(this.userService, is(notNullValue()));
    }

    @Test
    public void updateLevels(){
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        userService.upgradeLevels();
        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }
}

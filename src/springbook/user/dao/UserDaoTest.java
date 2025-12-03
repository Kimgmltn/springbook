package springbook.user.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class UserDaoTest {
    private static final Log log = LogFactory.getLog(UserDaoTest.class);
    private UserDao dao;

    public static void main(String[] args) throws ClassNotFoundException,SQLException {
        JUnitCore.main("springbook.user.dao.UserDaoTest");
    }

    @Before
    public void setUp(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        dao = context.getBean("userDao", UserDao.class);
    }

    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {
        User user1 = new User("test1", "test1", "test1");
        User user2 = new User("test2", "test2", "test2");

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));

    }

    @Test
    public void count() throws SQLException{
        User user1 = new User("test1", "test1", "test1");
        User user2 = new User("test2", "test2", "test2");
        User user3 = new User("test3", "test3", "test3");

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    @Test(expected = NoSuchElementException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown_id");
    }
}

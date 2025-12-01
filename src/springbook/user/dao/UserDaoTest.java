package springbook.user.dao;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException,SQLException {
        JUnitCore.main("springbook.user.dao.UserDaoTest");
    }

    @Test
    public void addAndGet() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);
        User user1 = new User();
        user1.setId("test1");
        user1.setName("test1");
        user1.setPassword("test1");

        dao.add(user1);

        User user2 = dao.get(user1.getId());

        Assert.assertThat(user2.getName(), CoreMatchers.is(user1.getName()));
        Assert.assertThat(user2.getPassword(), CoreMatchers.is(user1.getPassword()));
    }
}

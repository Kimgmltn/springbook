package springbook.user.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //JAVA 파일을 설정으로 사용
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        //XML 파일을 설정으로 사용
        // 경로 : src/applicationContext.xmlÎ
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);
        User user = dao.get("test");

        System.out.println("user name = " + user.getName());

//        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
//        System.out.println("connection count : " + ccm.getCounter());
    }
}

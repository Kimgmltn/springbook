package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() {
        //생성자 방식
//        return new UserDao(connectionMaker());

        //메서드 방식
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker());
        return userDao;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnection());
    }

    @Bean
    public ConnectionMaker realConnection() {
        return new DConnectionMaker();
    }
}

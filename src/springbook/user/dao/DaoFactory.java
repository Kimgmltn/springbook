package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao(){
        return new UserDao(getDConnectionMaker());
    }

    @Bean
    public DConnectionMaker getDConnectionMaker() {
        return new DConnectionMaker();
    }


}

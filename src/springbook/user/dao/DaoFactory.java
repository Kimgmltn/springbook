package springbook.user.dao;

import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao(){
        return new UserDao(getDConnectionMaker());
    }

    @Bean
    public DataSource getDConnectionMaker() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("heesu");
        dataSource.setPassword(null);
        return dataSource;
    }


}

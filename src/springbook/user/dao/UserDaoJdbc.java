package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;
import springbook.user.exceptions.DuplicateUserIdException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao{

    private Map<String, Object> sqlMap;

    public void setSqlMap(Map<String, Object> sqlMap) {
        this.sqlMap = sqlMap;
    }

    private JdbcTemplate jdbcTemplate;  //spring에서 제공하는 jdbcTemplate사용

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    };

    public UserDaoJdbc() {}

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) throws DuplicateUserIdException {
        this.jdbcTemplate.update((String) this.sqlMap.get("add"),
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
    }

    public User get(String id){
        return this.jdbcTemplate.queryForObject((String) this.sqlMap.get("get"),
                new Object[] {id},
                userMapper);
    }

    public int getCount(){
        return this.jdbcTemplate.queryForInt((String) this.sqlMap.get("getCount"));
    }

    public void deleteAll(){
        this.jdbcTemplate.update((String) this.sqlMap.get("deleteAll"));
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query((String) this.sqlMap.get("getAll"),
                userMapper);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update((String) this.sqlMap.get("update"),
                user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail(), user.getId());
    }
}

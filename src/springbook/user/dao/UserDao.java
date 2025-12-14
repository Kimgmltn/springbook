package springbook.user.dao;

import springbook.user.domain.User;

import java.util.List;

public interface UserDao {

    void add(User user);
    User get(String id);
    int getCount();
    void deleteAll();
    List<User> getAll();

    void update(User user);
}

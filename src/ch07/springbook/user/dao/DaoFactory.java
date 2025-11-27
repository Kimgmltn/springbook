package ch07.springbook.user.dao;

public class DaoFactory {
    public UserDao userDao(){
        DConnectionMaker connectionMaker = new DConnectionMaker();
        return new UserDao(connectionMaker);
    }
}

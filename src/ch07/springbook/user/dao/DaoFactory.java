package ch07.springbook.user.dao;

public class DaoFactory {
    public UserDao userDao(){
        return new UserDao(getDConnectionMaker());
    }

    public DConnectionMaker getDConnectionMaker() {
        return new DConnectionMaker();
    }


}

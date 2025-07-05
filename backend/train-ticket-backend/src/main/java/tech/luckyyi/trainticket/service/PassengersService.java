package tech.luckyyi.trainticket.service;

public interface PassengersService {
    // 校验用户名和密码
    boolean checkLogin(String username, String password);
}

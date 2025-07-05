package tech.luckyyi.trainticket.service;

import tech.luckyyi.trainticket.entity.User;

public interface UserService {
    // 校验用户名和密码，返回用户对象或 null
    User login(String username, String password);
}

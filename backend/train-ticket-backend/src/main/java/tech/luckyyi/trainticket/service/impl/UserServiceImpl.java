package tech.luckyyi.trainticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tech.luckyyi.trainticket.entity.User;
import tech.luckyyi.trainticket.mapper.UserMapper;
import tech.luckyyi.trainticket.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            // 用 BCrypt 校验明文密码和数据库密文是否匹配
            if (encoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}

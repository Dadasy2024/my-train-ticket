package tech.luckyyi.trainticket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.luckyyi.trainticket.mapper.PassengersMapper;
import tech.luckyyi.trainticket.service.PassengersService;

@Service
public class PassengersServiceImpl implements PassengersService {

    @Autowired
    private PassengersMapper passengersMapper;

    @Override
    public boolean checkLogin(String username, String password) {
        // 查询数据库，判断用户名和密码是否匹配
        Integer count = passengersMapper.countByUsernameAndPassword(username, password);
        return count != null && count > 0;
    }
}

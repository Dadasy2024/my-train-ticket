package tech.luckyyi.trainticket.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.luckyyi.trainticket.entity.Passengers;
import tech.luckyyi.trainticket.mapper.PassengersMapper;

/**
 * 测试用控制器，提供简单的接口用于验证数据库连接
 */
@RestController
public class TestController {

    @Resource
    private PassengersMapper passengersMapper;

    /**
     * 查询所有乘客信息
     * @return 乘客列表
     */
    @GetMapping("/test/passengers")
    public List<Passengers> getAllPassengers() {
        return passengersMapper.selectList(null); // 查询所有乘客
    }
}

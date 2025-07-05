package tech.luckyyi.trainticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.luckyyi.trainticket.entity.LoginRequest;
import tech.luckyyi.trainticket.entity.Result;
import tech.luckyyi.trainticket.entity.User;
import tech.luckyyi.trainticket.service.UserService;

// 标记为 REST 控制器，返回 JSON 数据
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    // 登录接口，接收 POST 请求
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            // 登录成功，可以返回用户信息（不含密码）
            user.setPassword(null); // 不返回密码
            return Result.success(user);
        } else {
            return Result.fail("用户名或密码错误");
        }
    }
}

package tech.luckyyi.trainticket.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tech.luckyyi.trainticket.entity.LoginRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // 用于把对象转成 JSON

    /**
     * 测试登录成功（user表）
     * 请确保user表中有该用户
     */
    @Test
    public void testLoginSuccess() throws Exception {
        // 构造登录请求参数
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin"); // 替换为user表中的用户名
        loginRequest.setPassword("123456"); // 替换为user表中的密码

        // 使用 MockMvc 发送 POST 请求到 /api/login
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON) // 设置请求体类型为 JSON
                .content(objectMapper.writeValueAsString(loginRequest))) // 请求体内容为 loginRequest 的 JSON 字符串
                .andExpect(status().isOk()) // 期望 HTTP 状态码为 200
                .andExpect(jsonPath("$.code").value(0)) // 期望返回 JSON 的 code 字段为 0（登录成功）
                .andExpect(jsonPath("$.msg").value("登录成功"))
                .andExpect(jsonPath("$.data.username").value("admin")); // 断言返回的用户名
    }

    /**
     * 测试登录失败（user表）
     */
    @Test
    public void testLoginFail() throws Exception {
        // 构造错误的登录请求参数
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("not_exist_user");
        loginRequest.setPassword("wrong_password");

        // 使用 MockMvc 发送 POST 请求到 /api/login
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk()) // 期望 HTTP 状态码为 200
                .andExpect(jsonPath("$.code").value(1)) // 期望 code 字段为 1（登录失败）
                .andExpect(jsonPath("$.msg").value("用户名或密码错误")); // 期望 msg 字段为 "用户名或密码错误"
    }
}

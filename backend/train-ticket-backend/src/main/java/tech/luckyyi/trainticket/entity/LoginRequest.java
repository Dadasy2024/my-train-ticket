package tech.luckyyi.trainticket.entity;

// 登录请求参数实体类
public class LoginRequest {
    private String username; // 用户名
    private String password; // 密码

    // getter 和 setter 方法
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

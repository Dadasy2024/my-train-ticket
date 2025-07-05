package tech.luckyyi.trainticket.entity;

// 用户实体类，对应 user 表
public class User {
    private Integer id;         // 用户ID
    private String username;    // 登录用户名
    private String password;    // 密码（加密存储）
    private String role;        // 角色（如 admin/user）

    // getter 和 setter 省略，可用IDE自动生成

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

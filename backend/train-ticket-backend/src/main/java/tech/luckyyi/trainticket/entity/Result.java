package tech.luckyyi.trainticket.entity;

// 统一返回结果实体类
public class Result<T> {
    private int code; // 0: 成功, 1: 失败
    private String msg; // 提示信息
    private T data; // 返回数据

    // 构造方法
    public Result() {}
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 静态方法快速返回
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "登录成功", data);
    }
    public static <T> Result<T> fail(String msg) {
        return new Result<>(1, msg, null);
    }

    // getter 和 setter
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}

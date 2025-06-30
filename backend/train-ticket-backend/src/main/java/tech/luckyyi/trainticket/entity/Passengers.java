package tech.luckyyi.trainticket.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 乘客实体类，对应数据库 passengers 表
 * 使用 Lombok 的 @Data 自动生成 getter/setter/toString/equals/hashCode 方法
 */
@Data
@TableName("passengers") // 指定对应的数据库表名
public class Passengers {
    @TableId // 指定主键字段
    private Integer passengerId; // 乘客ID（主键，自增）
    private String realName;     // 真实姓名
    private String idType;       // 证件类型
    private String idNumber;     // 证件号码
    private String phone;        // 手机号
}
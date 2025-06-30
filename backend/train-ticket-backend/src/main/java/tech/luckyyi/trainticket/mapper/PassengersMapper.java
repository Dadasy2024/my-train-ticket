package tech.luckyyi.trainticket.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import tech.luckyyi.trainticket.entity.Passengers;

/**
 * 乘客表数据库操作接口
 * 继承 MyBatis-Plus 的 BaseMapper，自动拥有常用的增删改查方法
 */
@Mapper
public interface PassengersMapper extends BaseMapper<Passengers> {
    // 无需手写方法，BaseMapper 已包含常用 CRUD
}

package tech.luckyyi.trainticket.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import tech.luckyyi.trainticket.entity.Passengers;

/**
 * 乘客表数据库操作接口
 * 继承 MyBatis-Plus 的 BaseMapper，自动拥有常用的增删改查方法
 */
@Mapper
public interface PassengersMapper extends BaseMapper<Passengers> {
    // 查询用户名和密码是否匹配
    @Select("SELECT COUNT(*) FROM passengers WHERE username = #{username} AND password = #{password}")
    Integer countByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}

package com.eason.seckill.seckill.dao;

import com.eason.seckill.seckill.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: eason
 * @Date: Created in 16:24 2020/2/28
 * @Description:
 */
@Mapper
public interface UsersDao {

    @Select("select * from miaosha_user where id=#{id}")
    User getUserByMobile(@Param("id") int mobile);
}

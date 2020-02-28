package com.eason.seckill.seckill.dao;

import com.eason.seckill.seckill.entity.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: eason
 * @Date: Created in 20:05 2020/2/23
 * @Description:
 */
@Mapper
public interface GoodsDao {

    @Select("select * from goods")
    List<Good> getAllGoods();
}

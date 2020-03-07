package com.eason.seckill.seckill.dao;

import com.eason.seckill.seckill.entity.Good;
import com.eason.seckill.seckill.entity.SeckillOrder;
import com.eason.seckill.seckill.vo.GoodVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: eason
 * @Date: Created in 20:05 2020/2/23
 * @Description:
 */
@Mapper
public interface SeckillOrderDao {

    @Select("select count(1) from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    int getSeckillOrderByUserIdAndGoodsId(@Param("userId")long userId,@Param("goodsId") long goodsId);
}

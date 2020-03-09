package com.eason.seckill.seckill.dao;

import com.eason.seckill.seckill.entity.SeckillOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @Author: eason
 * @Date: Created in 20:05 2020/2/23
 * @Description:
 */
@Mapper
public interface SeckillOrderDao {

    @Select("select count(1) from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    int getSeckillOrderByUserIdAndGoodsId(@Param("userId")long userId,@Param("goodsId") long goodsId);

    @Insert("insert into miaosha_order(user_id,order_id,goods_id) values " +
            "(#{userId},#{orderId},#{goodsId})")
    void saveSeckillOrder(SeckillOrder seckillOrder);
}

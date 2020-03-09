package com.eason.seckill.seckill.dao;

import com.eason.seckill.seckill.entity.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @Author: eason
 * @Date: Created in 21:07 2020/3/8
 * @Description:
 */
@Mapper
public interface OrderInfoDao {

    @Insert("insert into order_info(user_id,goods_id,delivery_addr_id,goods_name,goods_count,goods_price,order_channel,status) values " +
            "(#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    int saveOrderInfo(OrderInfo order);

}

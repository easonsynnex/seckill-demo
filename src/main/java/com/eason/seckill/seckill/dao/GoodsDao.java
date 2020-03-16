package com.eason.seckill.seckill.dao;

import com.eason.seckill.seckill.entity.Good;
import com.eason.seckill.seckill.entity.SeckillGood;
import com.eason.seckill.seckill.vo.GoodVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("select mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    List<GoodVo> getAllSeckillGoods();

    @Select("select g.goods_name,mg.goods_id,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg inner join goods g on mg.goods_id=g.id and g.id=#{id}")
    GoodVo getSeckillGoodById(@Param("id") long id);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id=#{goodsId} and stock_count > 0")
    int subtractOneGood(SeckillGood seckillGood);
}

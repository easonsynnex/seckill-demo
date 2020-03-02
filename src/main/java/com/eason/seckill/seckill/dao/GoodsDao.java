package com.eason.seckill.seckill.dao;

import com.eason.seckill.seckill.entity.Good;
import com.eason.seckill.seckill.vo.GoodVo;
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


    @Select("select mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    List<GoodVo> getAllSeckillGoods();
}

package com.eason.seckill.seckill.dao;

import com.eason.seckill.seckill.SeckillApplication;
import com.eason.seckill.seckill.entity.Good;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: eason
 * @Date: Created in 20:12 2020/2/23
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class GoodTest {
    @Autowired
    GoodsDao goodsDao;

    @Test
    public void testGetAllGoods(){
        List<Good> allGoods = goodsDao.getAllGoods();
        Assert.assertEquals(2, allGoods.size());
    }
}

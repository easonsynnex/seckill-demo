package com.eason.seckill.seckill.vo;

import java.util.Date;

/**
 * @Author: eason
 * @Date: Created in 21:52 2020/3/2
 * @Description:
 */
public class GoodVo {
    double miaoshaPrice;
    Integer stockCount;
    Date startDate;
    Date endDate;

    public double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

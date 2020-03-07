package com.eason.seckill.seckill.entity;

/**
 * @Author: eason
 * @Date: Created in 19:56 2020/2/23
 * @Description: 商品信息类 goods表
 */
public class Good {
    /**
     * 商品ID
     */
    private long id;
    /**
     * 商品名字
     */
    private String goodsName;
    /**
     * 商品标题
     */
    private String goodsTitle;
    /**
     * 商品图片路径
     */
    private String goodsImg;
    /**
     * 商品详情
     */
    private String goodsDetail;
    /**
     * 库存
     */
    private int goodsStock;

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                ", goodsStock=" + goodsStock +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public int getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(int goodsStock) {
        this.goodsStock = goodsStock;
    }
}

package com.eason.seckill.seckill.entity;

/**
 * @Author: eason
 * @Date: Created in 19:56 2020/2/23
 * @Description: 商品信息类
 */
public class Good {
    /**
     * 商品ID
     */
    private Integer id;
    /**
     * 商品名字
     */
    private String goodsName;
    /**
     * 商品标题
     */
    private String goodTitle;
    /**
     * 商品图片路径
     */
    private String goodImg;
    /**
     * 商品详情
     */
    private String goodDetail;
    /**
     * 库存
     */
    private int goodsStock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodTitle() {
        return goodTitle;
    }

    public void setGoodTitle(String goodTitle) {
        this.goodTitle = goodTitle;
    }

    public String getGoodImg() {
        return goodImg;
    }

    public void setGoodImg(String goodImg) {
        this.goodImg = goodImg;
    }

    public String getGoodDetail() {
        return goodDetail;
    }

    public void setGoodDetail(String goodDetail) {
        this.goodDetail = goodDetail;
    }

    public int getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(int goodsStock) {
        this.goodsStock = goodsStock;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", goodTitle='" + goodTitle + '\'' +
                ", goodImg='" + goodImg + '\'' +
                ", goodDetail='" + goodDetail + '\'' +
                ", goodsStock=" + goodsStock +
                '}';
    }
}

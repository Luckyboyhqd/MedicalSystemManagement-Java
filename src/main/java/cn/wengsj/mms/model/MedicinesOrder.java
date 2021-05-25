package cn.wengsj.mms.model;


import java.util.Date;

/**
 * 药品订单类
 */
public class MedicinesOrder {

    private int medicineId; // 药品ID
    private int medicineSaleNum; // 药品购买数量
    private double medicinePrice; // 药品价格
    private int userId; // 购买者的ID
    private String orderDate; // 订单生成日期，值为时间戳，值在初始化对象的时候生成

    public MedicinesOrder() {
        // 通过System.currentTimeMills()方法获得时间戳
        this.orderDate = new Long(System.currentTimeMillis()).toString();
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getMedicineSaleNum() {
        return medicineSaleNum;
    }

    public void setMedicineSaleNum(int medicineSaleNum) {
        this.medicineSaleNum = medicineSaleNum;
    }

    public double getMedicinePrice() {
        return medicinePrice;
    }

    public void setMedicinePrice(double medicinePrice) {
        this.medicinePrice = medicinePrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }
}

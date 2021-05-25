package cn.wengsj.mms.model.dto;

public class StockForIndexBean {
    private String name;                //药品名
    private int stockNumber;           //仓库剩余数量
    private int daySale[]=new int[8];  //七天销量
    private int saleCount;              //总销售量

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public int[] getDaySale() {
        return daySale;
    }

    public void setDaySale(int[] daySale) {
        this.daySale = daySale;
    }
}

package cn.wengsj.mms.model.dto;

public class DaySaleBean {
    private double[] daySale; //日销售总金额
    private int[] dayNumber;//日销售总量
    private String[] dayDate;
    public double[] getDaySale() {
        return daySale;
    }

    public String[] getDayDate() {
        return dayDate;
    }

    public void setDayDate(String[] dayDate) {
        this.dayDate = dayDate;
    }

    public void setDaySale(double[] daySale) {
        this.daySale = daySale;
    }

    public int[] getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int[] dayNumber) {
        this.dayNumber = dayNumber;
    }
}

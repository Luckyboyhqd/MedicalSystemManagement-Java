package cn.wengsj.mms.model;

public class SaleLogBean {
    private int medicine_Log_Id;
    private String sale_Date;
    private String medicine_GB;
    private int sale_Number;
    private double sale_Price;
    private String user_Account;

    public int getMedicine_Log_Id() {
        return medicine_Log_Id;
    }

    public void setMedicine_Log_Id(int medicine_Log_Id) {
        this.medicine_Log_Id = medicine_Log_Id;
    }

    public String getSale_Date() {
        return sale_Date;
    }

    public void setSale_Date(String sale_Date) {
        this.sale_Date = sale_Date;
    }


    public int getSale_Number() {
        return sale_Number;
    }

    public void setSale_Number(int sale_Number) {
        this.sale_Number = sale_Number;
    }

    public double getSale_Price() {
        return sale_Price;
    }


    public String getMedicine_GB() {
        return medicine_GB;
    }

    public void setMedicine_GB(String medicine_GB) {
        this.medicine_GB = medicine_GB;
    }

    public String getUser_Account() {
        return user_Account;
    }

    public void setUser_Account(String user_Account) {
        this.user_Account = user_Account;
    }

    public void setSale_Price(double sale_Price) {
        this.sale_Price = sale_Price;
    }


}

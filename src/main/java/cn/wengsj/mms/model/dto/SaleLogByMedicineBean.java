package cn.wengsj.mms.model.dto;

public class SaleLogByMedicineBean implements Comparable<SaleLogByMedicineBean>{
    private String medicine_Name;    //药品名称
    private int sale_Count;         //销售数量
    private int human_Count;        //购买人数


    public String getMedicine_Name() {
        return medicine_Name;
    }

    public void setMedicine_Name(String medicine_Name) {
        this.medicine_Name = medicine_Name;
    }

    public int getSale_Count() {
        return sale_Count;

    }

    public void setSale_Count(int sale_Count) {
        this.sale_Count = sale_Count;
    }

    public int getHuman_Count() {
        return human_Count;
    }

    public void setHuman_Count(int human_Count) {
        this.human_Count = human_Count;
    }

    @Override
    public int compareTo(SaleLogByMedicineBean o) {
        return o.getSale_Count()-this.getSale_Count();
    }
}

package cn.wengsj.mms.model.dto;

public class SaleLogByUserBean implements Comparable<SaleLogByUserBean>{
    private String user_Name;       //用户名
    private int monetary;           //总消费金额
    private int purchase_Times;    //购买次数

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public int getMonetary() {
        return monetary;
    }

    public void setMonetary(int monetary) {
        this.monetary = monetary;
    }

    public int getPurchase_Times() {
        return purchase_Times;
    }

    public void setPurchase_Times(int purchase_Times) {
        this.purchase_Times = purchase_Times;
    }

    @Override
    public int compareTo(SaleLogByUserBean o) {
        return o.getPurchase_Times()-this.getPurchase_Times();
    }
}

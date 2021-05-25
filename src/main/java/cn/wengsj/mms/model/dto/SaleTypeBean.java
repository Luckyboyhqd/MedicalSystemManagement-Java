package cn.wengsj.mms.model.dto;

public class SaleTypeBean implements Comparable<SaleTypeBean>{
    private String name; //药品种类名
    private int value; //销售量

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(SaleTypeBean o) {
        return o.getValue()-this.getValue();
    }
}

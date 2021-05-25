package cn.wengsj.mms.model;

import java.io.Serializable;

/**
 * 药品实体类
 */

public class Drug implements Serializable {
    private Integer medicineId;                 //药品ID
    private String medicineGB;                  //药品编号
    private String medicineName;                //药品名字
    private Double medicinePrice;               //药品价格
    private String medicineFirm;                //药品厂商
    private Integer type;                       //药品类别
    private String typeName;                    //药品类别名字
    private Integer medicineStockNumber;        //药品库存量
    private String medicinePD;                  //药品生产日期

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getMedicineStockNumber() {
        return medicineStockNumber;
    }

    public void setMedicineStockNumber(Integer medicineStockNumber) {
        this.medicineStockNumber = medicineStockNumber;
    }

    public String getMedicinePD() {
        return medicinePD;
    }

    public void setMedicinePD(String medicinePD) {
        this.medicinePD = medicinePD;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineGB() {
        return medicineGB;
    }

    public void setMedicineGB(String medicineGB) {
        this.medicineGB = medicineGB;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Double getMedicinePrice() {
        return medicinePrice;
    }

    public void setMedicinePrice(Double medicinePrice) {
        this.medicinePrice = medicinePrice;
    }

    public String getMedicineFirm() {
        return medicineFirm;
    }

    public void setMedicineFirm(String medicineFirm) {
        this.medicineFirm = medicineFirm;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

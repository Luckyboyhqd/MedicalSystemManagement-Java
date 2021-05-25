package cn.wengsj.mms.service;

import cn.wengsj.mms.model.Drug;
import cn.wengsj.mms.model.MedicinesOrder;

import java.io.IOException;
import java.sql.SQLException;

public interface IOrderService {
    // 返回插入是否成功
    boolean addOrder(MedicinesOrder mo, int stockNumber) throws IOException,SQLException;

    // 返回更新库存是否成功
    boolean updateStock(MedicinesOrder mo,int stockNumber) ;

    // 获得用户要购买的药品
    Drug selectDrugById(int medicineId) throws IOException,SQLException;
}

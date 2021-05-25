package cn.wengsj.mms.service.impl;

import cn.wengsj.mms.dao.impl.DrugDao;
import cn.wengsj.mms.dao.impl.OrderDao;
import cn.wengsj.mms.model.Drug;
import cn.wengsj.mms.model.MedicinesOrder;
import cn.wengsj.mms.service.IOrderService;
import cn.wengsj.mms.utils.DBUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderService implements IOrderService {
    private Connection conn = DBUtils.getConnection();
    private OrderDao od = new OrderDao(conn);

    /**
     * 向数据库插入订单数据
     */
    @Override
    public boolean addOrder(MedicinesOrder mo, int stockNumber) throws IOException, SQLException {
        int count = od.save(mo);
        if(count > 0){
            if(updateStock(mo,stockNumber)){
                return true;
            }
        }
        return false;
    }

    /**
     * 购买成功后更新库存数量
     */
    @Override
    public boolean updateStock(MedicinesOrder mo,int stockNumber)  {
        String updateSql = "update medicine_stock set medicine_stock_number=? where medicine_id=?";
        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(updateSql);
            ptmt.setInt(1,stockNumber-mo.getMedicineSaleNum());
            ptmt.setInt(2,mo.getMedicineId());
            ptmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过medicineId查找药品的详细属性，用于显示在购买页面上
     */
    @Override
    public Drug selectDrugById(int medicineId) throws IOException, SQLException {
        DrugDao dd = new DrugDao();
        return dd.selectById(medicineId);
    }
}

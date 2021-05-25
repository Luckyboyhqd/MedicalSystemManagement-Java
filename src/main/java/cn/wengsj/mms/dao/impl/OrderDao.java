package cn.wengsj.mms.dao.impl;

import cn.wengsj.mms.dao.BaseDao;
import cn.wengsj.mms.dao.IOrderDao;
import cn.wengsj.mms.model.MedicinesOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单的dao层，仅仅只有插入
 */
public class OrderDao implements IOrderDao {
    private Connection conn = null;
    public OrderDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<MedicinesOrder> selectByList() throws SQLException {
        return null;
    }

    @Override
    public List<MedicinesOrder> selectByPage(int begin, int limit) throws SQLException {
        return null;
    }

    @Override
    public MedicinesOrder selectById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public int selectCount() throws SQLException {
        return 0;
    }

    /**
     * 向数据库插入订单数据
     * @param entity 封装好的订单bean
     */
    @Override
    public int save(MedicinesOrder entity) throws SQLException {
        String insertSql = "insert into medicine_log (sale_date,medicine_id,sale_number,sale_price,user_id) " +
                "values(?,?,?,?,?)";
        PreparedStatement ptmt = conn.prepareStatement(insertSql);
        ptmt.setString(1,entity.getOrderDate());
        ptmt.setInt(2,entity.getMedicineId());
        ptmt.setInt(3,entity.getMedicineSaleNum());
        ptmt.setDouble(4,entity.getMedicinePrice());
        ptmt.setInt(5,entity.getUserId());
        int count = ptmt.executeUpdate();
        if(count > 0)
            return count;
        return 0;
    }

    @Override
    public int delete(Integer id) throws SQLException {
        return 0;
    }
}

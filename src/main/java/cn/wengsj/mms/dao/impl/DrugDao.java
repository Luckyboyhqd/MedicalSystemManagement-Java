package cn.wengsj.mms.dao.impl;

import cn.wengsj.mms.dao.IDrugDao;
import cn.wengsj.mms.model.Drug;
import cn.wengsj.mms.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DrugDao implements IDrugDao {

    Connection conn = DBUtils.getConnection();
    PreparedStatement pstm = null;

    /**
     * 封装获取药品实体类的语句
     *
     * @param rs
     * @throws SQLException
     */
    private Drug getDrug(ResultSet rs) throws SQLException {
        Drug drug = new Drug();
        drug.setMedicineId(rs.getInt("Medicine_Id"));
        drug.setMedicineGB(rs.getString("Medicine_GB"));
        drug.setMedicineName(rs.getString("Medicine_Name"));
        drug.setMedicinePrice(rs.getDouble("Medicine_Price"));
        drug.setMedicineFirm(rs.getString("Medicine_Firm"));
        drug.setType(rs.getInt("Medicine_Type"));
        drug.setMedicinePD(new SimpleDateFormat("yyyy-MM-dd").format(Long.parseLong(rs.getString("Medicine_PD"))));
        drug.setMedicineStockNumber(rs.getInt("Medicine_Stock_Number"));
        drug.setTypeName(new MedicineTypeDao().selectById(drug.getType()).getTypeName());
        return drug;
    }


    @Override
    public List<Drug> selectByList() throws SQLException {
        String sql = " SELECT medicine.*,Medicine_Stock_Number,Medicine_PD FROM  medicine,medicine_stock where medicine.Medicine_Id=medicine_stock.Medicine_Id ";
        pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        Drug drug;
        List<Drug> list = new ArrayList<>();
        while (rs.next()) {
            drug = getDrug(rs);
            list.add(drug);
        }
        return list;
    }

    @Override
    public List<Drug> selectByPage(int begin, int limit) throws SQLException {
        StringBuffer sql = new StringBuffer(" SELECT medicine.*,Medicine_Stock_Number,Medicine_PD FROM  medicine,medicine_stock WHERE medicine.Medicine_Id=medicine_stock.Medicine_Id ");
        //添加页码和该页数据个数的限制条件
        sql.append(" ORDER BY medicine.Medicine_Id DESC ");
        sql.append("LIMIT ?,?");
        pstm = conn.prepareStatement(sql.toString());
        pstm.setInt(1, begin);
        pstm.setInt(2, limit);
        ResultSet rs = pstm.executeQuery();
        List<Drug> list = new ArrayList<>();
        Drug drug;
        while (rs.next()) {
            drug = getDrug(rs);
            list.add(drug);
        }
        return list;
    }


    @Override
    public Drug selectById(Integer id) throws SQLException {
        String sql = " SELECT medicine.*,Medicine_Stock_Number,Medicine_PD FROM  medicine,medicine_stock where medicine.Medicine_Id=medicine_stock.Medicine_Id AND medicine.Medicine_Id=? ";
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        Drug drug = null;
        while (rs.next()) {
            drug = getDrug(rs);
        }
        return drug;
    }

    @Override
    public Drug selectByGB(String Medicine_GB) throws SQLException {
        String sql = " SELECT medicine.*,Medicine_Stock_Number,Medicine_PD FROM  medicine,medicine_Stock where medicine.Medicine_Id=medicine_stock.Medicine_Id AND Medicine_GB=? ";
        pstm = conn.prepareStatement(sql);
        pstm.setString(1, Medicine_GB);
        ResultSet rs = pstm.executeQuery();
        Drug drug = null;
        while (rs.next()) {
            drug = getDrug(rs);
        }
        return drug;
    }

    @Override
    public int updateDrug(Drug entity) throws SQLException {
        String sql = " UPDATE medicine SET Medicine_GB=?,Medicine_Name=?,Medicine_Price=?,Medicine_Firm=?,Medicine_Type=? WHERE Medicine_Id=? ";
        pstm = conn.prepareStatement(sql);
        pstm.setString(1, entity.getMedicineGB());
        pstm.setString(2, entity.getMedicineName());
        pstm.setDouble(3, entity.getMedicinePrice());
        pstm.setString(4, entity.getMedicineFirm());
        pstm.setInt(5, entity.getType());
        pstm.setInt(6, entity.getMedicineId());

        int flag;

        flag = pstm.executeUpdate();

        sql = " UPDATE medicine_stock SET Medicine_Stock_Number=?,Medicine_PD=? WHERE Medicine_Id=?";
        pstm = conn.prepareStatement(sql);

        pstm.setInt(1, entity.getMedicineStockNumber());
        try {
            //将日期格式转为时间戳
            pstm.setString(2, new SimpleDateFormat("yyyy-MM-dd").parse(entity.getMedicinePD()).getTime() + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pstm.setInt(3, entity.getMedicineId());

        return pstm.executeUpdate() & flag;
    }

    @Override
    public List<Drug> listBySearch(int begin, int limit, String condition) throws SQLException {
        StringBuffer sql = new StringBuffer(" SELECT medicine.*,Medicine_Stock_Number,Medicine_PD FROM  medicine,medicine_stock WHERE medicine.Medicine_Id=medicine_stock.Medicine_Id AND Medicine_Name LIKE ? ");
        sql.append(" ORDER BY medicine.Medicine_Id DESC ");
        sql.append(" LIMIT ?,? ");
        pstm = conn.prepareStatement(sql.toString());
        pstm.setString(1, "%" + condition + "%");
        pstm.setInt(2, begin);
        pstm.setInt(3, limit);
        ResultSet rs = pstm.executeQuery();
        List<Drug> list = new ArrayList<>();
        Drug drug;
        while (rs.next()) {
            drug = getDrug(rs);
            list.add(drug);
        }
        return list;
    }

    @Override
    public int selectCount() throws SQLException {
        String sql = "select count(*) from medicine";
        pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    @Override
    public int save(Drug entity) throws SQLException {

        // 需求不明，暂不判断
        // 判断是否重复添加
        // if (selectByGB(entity.getMedicineGB()) != null) {
        //     return 0;
        // }

        String sql = " INSERT INTO medicine VALUES (null,?,?,?,?,?) ";
        pstm = conn.prepareStatement(sql);
        pstm.setString(1, entity.getMedicineGB());
        pstm.setString(2, entity.getMedicineName());
        pstm.setDouble(3, entity.getMedicinePrice());
        pstm.setString(4, entity.getMedicineFirm());
        pstm.setInt(5, entity.getType());

        // 因为涉及两个表的添加，flag用来判断是否两个表都添加成功
        int flag = pstm.executeUpdate();

        // 添加到药品表
        sql = " SELECT max(Medicine_Id) from medicine ";
        pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        rs.next();
        entity.setMedicineId(rs.getInt(1));

        // 添加到药品库存表
        sql = " INSERT INTO medicine_stock VALUES(?,?,?) ";
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1, entity.getMedicineId());
        pstm.setInt(2, entity.getMedicineStockNumber());
        try {
            pstm.setString(3, new SimpleDateFormat("yyyy-MM-dd").parse(entity.getMedicinePD()).getTime() + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return pstm.executeUpdate() & flag;
    }

    @Override
    public int delete(Integer id) throws SQLException {

        // 从库存表中删除
        String  sql = " DELETE FROM medicine_stock WHERE Medicine_Id = ? ";
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        int flag = pstm.executeUpdate();

        // 从药品表中删除
        sql = " DELETE FROM medicine WHERE Medicine_Id=? ";
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);



        return pstm.executeUpdate() & flag;
    }

}

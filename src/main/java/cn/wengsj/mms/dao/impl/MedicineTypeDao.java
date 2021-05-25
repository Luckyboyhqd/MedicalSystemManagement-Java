package cn.wengsj.mms.dao.impl;

import cn.wengsj.mms.dao.IMedicineTypeDao;
import cn.wengsj.mms.model.MedicineType;
import cn.wengsj.mms.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicineTypeDao implements IMedicineTypeDao {
    Connection conn = DBUtils.getConnection();
    PreparedStatement pstmt = null;
    String sql = null;

    /**
    * select all
    */
    @Override
    public List<MedicineType> selectByList() throws SQLException {
        List<MedicineType> list = new ArrayList<MedicineType>();
        MedicineType medicineType = null;
        sql = "select * from medicine_type";
        pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            medicineType = new MedicineType();
            medicineType.setId(rs.getInt("Medicine_Type"));
            medicineType.setTypeName(rs.getString("Medicine_Type_Name"));
            medicineType.setDate(timestampToTime(rs.getString("Medicine_Type_Date")));
            medicineType.setDest(rs.getString("Medicine_Type_De"));
            list.add(medicineType);
        }
        return list;
    }

    @Override
    public List<MedicineType> selectByPage(int begin, int limit) throws SQLException {
        List<MedicineType> list = new ArrayList<MedicineType>();
        MedicineType medicineType = null;
        sql = "select * from medicine_type order by Medicine_Type desc limit ?,?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,begin);
        pstmt.setInt(2,limit);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            medicineType = new MedicineType();
            medicineType.setId(rs.getInt("Medicine_Type"));
            medicineType.setTypeName(rs.getString("Medicine_Type_Name"));
            medicineType.setDate(timestampToTime(rs.getString("Medicine_Type_Date")));
            medicineType.setDest(rs.getString("Medicine_Type_De"));
            list.add(medicineType);
        }
        return list;
    }

    @Override
    public MedicineType selectById(Integer id) throws SQLException {
        MedicineType medicineType = null;
        sql = "select * from medicine_type where Medicine_Type=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            medicineType = new MedicineType();
            medicineType.setId(rs.getInt("Medicine_Type"));
            medicineType.setTypeName(rs.getString("Medicine_Type_Name"));
            medicineType.setDate(timestampToTime(rs.getString("Medicine_Type_Date")));
            medicineType.setDest(rs.getString("Medicine_Type_De"));
        }

        return medicineType;
    }

    @Override
    public int selectCount() throws SQLException {
        int count = 0;
        sql = "select count(*) from medicine_type";
        pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
           count = rs.getInt(1);
        }
        return count;
    }

    @Override
    public int save(MedicineType entity) throws SQLException {
        sql = "insert into medicine_type(Medicine_Type_Name, Medicine_Type_Date, Medicine_Type_De) values(?,?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,entity.getTypeName());
        pstmt.setString(2,entity.getDate());
        pstmt.setString(3,entity.getDest());
        if(pstmt.executeUpdate() > 0){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int delete(Integer id) throws SQLException {
        sql = "delete from medicine_type where Medicine_Type=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        if(pstmt.executeUpdate() > 0){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public Boolean edit(String typeName, String dest, Integer id) throws SQLException {
        sql = "update medicine_type set Medicine_Type_Name=?, Medicine_Type_De=? where Medicine_Type=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,typeName);
        pstmt.setString(2,dest);
        pstmt.setInt(3,id);
        if(pstmt.executeUpdate() > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<MedicineType> selectByKey(String key, Integer begin, Integer limit) throws SQLException {
        List<MedicineType> list = new ArrayList<MedicineType>();
        MedicineType medicineType = new MedicineType();
        sql = "select * from medicine_type " +
                "where Medicine_Type like '%"+key+"%'" +
                " or Medicine_Type_Name like '%"+ key +"%'" +
                " or Medicine_Type_Date like '%"+ key +"%'" +
                " or Medicine_Type_De like '%"+ key +"%' " +
                " order by Medicine_Type desc" +
                " limit ?,?";
//        System.out.println("sql:"+sql);
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,begin);
        pstmt.setInt(2,limit);
        ResultSet rs= pstmt.executeQuery();
        while(rs.next()) {
            medicineType = new MedicineType();
            medicineType.setId(rs.getInt("Medicine_Type"));
            medicineType.setTypeName(rs.getString("Medicine_Type_Name"));
            medicineType.setDate(timestampToTime(rs.getString("Medicine_Type_Date")));
            medicineType.setDest(rs.getString("Medicine_Type_De"));
            list.add(medicineType);
        }
        return list;
    }

    @Override
    public Integer selectPageCount(String key) throws SQLException {
        Integer count = 0;
        sql = "select * from medicine_type " +
                "where Medicine_Type like '%"+key+"%'" +
                " or Medicine_Type_Name like '%"+ key +"%'" +
                " or Medicine_Type_Date like '%"+ key +"%'" +
                " or Medicine_Type_De like '%"+ key +"%' ";
        pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public static String timestampToTime(String timestmap) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        Long ln = new Long(timestmap);
        Date date = new Date(ln);
        return (String)sdf.format(date);
    }
}

package cn.wengsj.mms.dao;

import cn.wengsj.mms.model.MedicineType;

import java.sql.SQLException;
import java.util.List;

public interface IMedicineTypeDao extends BaseDao<MedicineType> {
    Boolean edit(String typeName, String dest, Integer id) throws SQLException;
    List<MedicineType> selectByKey(String key, Integer begin, Integer limit) throws SQLException;
    Integer selectPageCount(String key) throws SQLException;
}

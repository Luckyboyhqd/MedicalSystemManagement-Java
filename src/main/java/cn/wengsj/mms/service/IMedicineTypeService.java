package cn.wengsj.mms.service;

import cn.wengsj.mms.model.MedicineType;
import com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer;

import java.sql.SQLException;
import java.util.List;

public interface IMedicineTypeService {
//  获取全部药品类的记录
    List<MedicineType> getTypeNameAndId() throws SQLException;
//  获取分页的药品类的记录
    List<MedicineType> getMedicineTypeByPage(int begin, int limit) throws SQLException;
//  获取MedicineType_Type为id的药品类
    MedicineType getMedicineTypeById(Integer id) throws  SQLException;
//  获取药品类的记录数
    int getCount() throws SQLException;
//  添加药品类
    void addMedicineType(MedicineType medicineType) throws SQLException;
//  删除药品类
    void removeMedicineType(Integer id) throws SQLException;
//  编辑药品类
    Boolean eidtMedicineType(String typeName, String dest, Integer id) throws SQLException;
//  模糊查询
    List<MedicineType> getMedicineTypeByKey(String key, Integer begin, Integer limit) throws SQLException;
//  分页的总记录数
    Integer getPageCount(String key) throws SQLException;


}

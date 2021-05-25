package cn.wengsj.mms.service.impl;

import cn.wengsj.mms.action.MedicineTypeServlet;
import cn.wengsj.mms.dao.impl.MedicineTypeDao;
import cn.wengsj.mms.model.MedicineType;
import cn.wengsj.mms.service.IMedicineTypeService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineTypeService implements IMedicineTypeService {
    List<MedicineType> list = new ArrayList<MedicineType>();
    MedicineTypeDao dao = new MedicineTypeDao();

    @Override
    public List<MedicineType> getTypeNameAndId() throws SQLException {
        list = dao.selectByList();
        return list;
    }

    @Override
    public List<MedicineType> getMedicineTypeByPage(int begin, int limit) throws SQLException {
        list = dao.selectByPage(begin, limit);
        return list;
    }

    @Override
    public MedicineType getMedicineTypeById(Integer id) throws SQLException {
        MedicineType medicineType = new MedicineType();
        medicineType = dao.selectById(id);
        return medicineType;
    }

    @Override
    public int getCount() throws SQLException {
        return dao.selectCount();
    }

    @Override
    public void addMedicineType(MedicineType medicineType) throws SQLException {
        dao.save(medicineType);
    }

    @Override
    public void removeMedicineType(Integer id) throws SQLException {
        dao.delete(id);
    }

    @Override
    public Boolean eidtMedicineType(String typeName, String dest, Integer id) throws SQLException {
        return dao.edit(typeName, dest, id);
    }

    @Override
    public List<MedicineType> getMedicineTypeByKey(String key, Integer begin, Integer limit) throws SQLException {
        list = dao.selectByKey(key, begin, limit);
        return list;
    }

    @Override
    public Integer getPageCount(String key) throws SQLException {
        return dao.selectPageCount(key);
    }
}

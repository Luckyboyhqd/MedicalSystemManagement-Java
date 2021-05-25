package cn.wengsj.mms.service.impl;

import cn.wengsj.mms.dao.impl.SaleLogDao;
import cn.wengsj.mms.model.SaleLogBean;
import cn.wengsj.mms.model.dto.SaleLogByMedicineBean;
import cn.wengsj.mms.model.dto.SaleLogByUserBean;
import cn.wengsj.mms.service.ISaleLogService;

import java.sql.SQLException;
import java.util.List;

public class SaleLogService implements ISaleLogService {
    SaleLogDao dao;
    public SaleLogService(){
        dao = new SaleLogDao();
    }
    public List<SaleLogBean> selectByKey(String str) throws SQLException {
        return dao.selectByKey(str);
    }


}

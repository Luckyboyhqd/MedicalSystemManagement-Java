package cn.wengsj.mms.service;

import cn.wengsj.mms.model.SaleLogBean;
import cn.wengsj.mms.model.dto.SaleLogByMedicineBean;
import cn.wengsj.mms.model.dto.SaleLogByUserBean;

import java.sql.SQLException;
import java.util.List;

public interface ISaleLogService {
    /**
     * 销售记录模糊查询
     * @param str
     * @return
     * @throws SQLException
     */
    List<SaleLogBean> selectByKey(String str) throws SQLException;

}

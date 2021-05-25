package cn.wengsj.mms.dao;

import cn.wengsj.mms.model.SaleLogBean;
import cn.wengsj.mms.model.dto.SaleTypeBean;
import cn.wengsj.mms.model.dto.SaleLogByMedicineBean;
import cn.wengsj.mms.model.dto.SaleLogByUserBean;

import java.sql.SQLException;
import java.util.List;

public interface ISaleLogDao extends BaseDao<SaleLogBean>{

    List<SaleLogBean> selectByKey(String str) throws SQLException;

    int selectCount() throws SQLException;

    List<SaleLogByMedicineBean> queryHotMedicine(int size) throws SQLException;

    List<SaleLogByUserBean> queryUserRank(int size) throws SQLException;

    List<SaleTypeBean> querySaleTypeSort(int size) throws  SQLException;

    int queryTotalSale() throws SQLException;
}

package cn.wengsj.mms.dao;

import cn.wengsj.mms.model.Drug;

import java.sql.SQLException;
import java.util.List;

public interface IDrugDao extends BaseDao<Drug> {
    /**
     * 根据药品的GB来检索药品
     *
     * @param Medicine_GB
     * @return 检索到的药品，无该药品则为null
     * @throws SQLException
     */
    Drug selectByGB(String Medicine_GB) throws SQLException;

    /**
     * 更新药品
     *
     * @param entity
     * @return 1代表更新成功，0代表更新失败
     * @throws SQLException
     */
    int updateDrug(Drug entity) throws SQLException;

    /**
     * 根据搜索条件来检索满足条件的数据
     *
     * @param begin
     * @param limit
     * @param condition
     * @return 返回检索到的数据，无符合的数据，List长度为0
     * @throws SQLException
     */
    List<Drug> listBySearch(int begin, int limit, String condition) throws SQLException;
}

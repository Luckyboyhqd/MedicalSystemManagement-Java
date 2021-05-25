package cn.wengsj.mms.service;

import cn.wengsj.mms.model.Drug;
import cn.wengsj.mms.model.PageBean;

import java.sql.SQLException;

public interface IDrugService {

    /**
     * 根据输入的页码，每页的限制条数来检索符合的数据，并封装到PageBean中
     * condition为搜索时候所用的限制条件
     *
     * @param page
     * @param limit
     * @param condition
     * @return 符合条件的PageBean
     * @throws SQLException
     */
    PageBean<Drug> selectDrugsByPage(int page, int limit, String condition) throws SQLException;

    /**
     * 添加用户
     *
     * @param entity
     * @return 1代表添加成功，0代表失败
     * @throws SQLException
     */
    int save(Drug entity) throws SQLException;

    /**
     * 删除用户
     *
     * @param id
     * @return 1代表成功，0代表失败
     * @throws SQLException
     */
    int delete(Integer id) throws SQLException;

    /**
     * 更新用户
     *
     * @param entity
     * @return 1代表成功，0代表失败
     * @throws SQLException
     */
    int updateDrug(Drug entity) throws SQLException;
}

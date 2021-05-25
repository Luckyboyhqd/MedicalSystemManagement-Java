package cn.wengsj.mms.dao;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<T> {
    List<T> selectByList() throws SQLException;

    List<T> selectByPage(int begin, int limit) throws SQLException;

    T selectById(Integer id) throws SQLException;

    int selectCount() throws SQLException;

    int save(T entity) throws SQLException;

    int delete(Integer id) throws SQLException;
}

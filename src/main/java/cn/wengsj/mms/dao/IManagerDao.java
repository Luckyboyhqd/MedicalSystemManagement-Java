package cn.wengsj.mms.dao;

import cn.wengsj.mms.model.Manager;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IManagerDao extends BaseDao<Manager>{

    Manager selectByUsernameAndPassword(String username, String password) throws SQLException;

    Manager selectByUsername(String username) throws SQLException;

    List<Manager> selectByPageAndUsername(int begin, int limit, String condition) throws SQLException;

    int selectCountByUsername(String condition) throws SQLException;

    Set<String> getRoles(String username) throws SQLException;

    Set<String> getPermissions(String username) throws SQLException;
}

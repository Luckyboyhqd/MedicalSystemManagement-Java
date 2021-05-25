package cn.wengsj.mms.service;

import cn.wengsj.mms.model.Manager;
import cn.wengsj.mms.model.PageBean;

import java.sql.SQLException;
import java.util.Set;

public interface IManagerService {
    /**
     * 以用户名和密码登录
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    Manager LoginByUsernameAndPassword(String username, String password) throws SQLException;

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     * @throws SQLException
     */
    Manager selectManagerById(Integer id) throws SQLException;

    /**
     * 分页查询用户，以及模糊查询
     * @param curr
     * @param limit
     * @param condition
     * @return
     * @throws SQLException
     */
    PageBean<Manager> selectManagersByPage(int curr, int limit, String condition) throws SQLException ;

    /**
     * 添加用户业务
     * @param manager
     * @return
     * @throws SQLException
     */
    int addManager(Manager manager) throws SQLException;

    /**
     * 删除用户业务
     * @param id
     * @return
     * @throws SQLException
     */
    int removeManager(Integer id) throws SQLException;

    /**
     * 更新用户业务
     * @param manager
     * @return
     * @throws SQLException
     */
    int updateManager(Manager manager) throws SQLException;

    /**
     * 重置用户密码
     * @param id
     * @return
     * @throws SQLException
     */
    int resetManagerPassword(Integer id) throws SQLException;

    /**
     * 修改用户密码业务
     * @param id
     * @param password
     * @return
     * @throws SQLException
     */
    int changeManagerPassword(Integer id,String password) throws SQLException;

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    Manager selectManegerByName(String username);

    /**
     * 根据用户名查询用户角色
     * @param username
     * @return
     */
    Set<String> getRoles(String username);

    /**
     * 根据用户名查询用户权限
     * @param username
     * @return
     */
    Set<String> getPermissions(String username);
}

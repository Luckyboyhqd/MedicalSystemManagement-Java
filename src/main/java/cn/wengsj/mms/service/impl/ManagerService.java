package cn.wengsj.mms.service.impl;

import cn.wengsj.mms.dao.IManagerDao;
import cn.wengsj.mms.dao.impl.ManagerDao;
import cn.wengsj.mms.model.Manager;
import cn.wengsj.mms.model.PageBean;
import cn.wengsj.mms.service.IManagerService;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class ManagerService implements IManagerService {

    IManagerDao dao = new ManagerDao();

    @Override
    public Manager LoginByUsernameAndPassword(String username, String password) throws SQLException {
        return dao.selectByUsernameAndPassword(username,password);
    }

    @Override
    public Manager selectManagerById(Integer id) throws SQLException {
        return dao.selectById(id);
    }

    @Override
    public Manager selectManegerByName(String username) {
        Manager manager = null;
        try {
            manager = dao.selectByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manager;
    }

    @Override
    public Set<String> getRoles(String username) {
        Set<String> roles = null;

        try {
            roles = dao.getRoles(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    @Override
    public Set<String> getPermissions(String username) {
        Set<String> permissions = null;

        try {
            permissions = dao.getPermissions(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return permissions;
    }

    @Override
    public PageBean<Manager> selectManagersByPage(int curr, int limit, String condition) throws SQLException {
        PageBean<Manager> page = new PageBean<>();
        //设置总记录数
        page.setTotalCount(dao.selectCountByUsername(condition));
        //设置当前页
        page.setCurrentPage(curr);
        //每页记录数
        page.setPageCount(limit);
        int begin = (curr-1)*limit;
        //查询数据
        List<Manager> data = dao.selectByPageAndUsername(begin,limit,condition);
        page.setPageData(data);
        return page;
    }

    @Override
    public int addManager(Manager manager) throws SQLException {
        //判断是否有该用户名
        Manager existManager = dao.selectByUsername(manager.getUsername());
        if(existManager!=null){
            return -1;
        }
        return dao.save(manager);
    }

    @Override
    public int removeManager(Integer id) throws SQLException {
        return dao.delete(id);
    }

    @Override
    public int updateManager(Manager manager) throws SQLException {
        return dao.save(manager);
    }

    @Override
    public int resetManagerPassword(Integer id) throws SQLException {
        Manager manager = selectManagerById(id);
        //设置初始密码为dd
        manager.setPassword("dd");
        return dao.save(manager);
    }

    @Override
    public int changeManagerPassword(Integer id,String password) throws SQLException {
        Manager manager = selectManagerById(id);
        manager.setPassword(password);
        return dao.save(manager);
    }
}

package cn.wengsj.mms.dao.impl;

import cn.wengsj.mms.dao.IManagerDao;
import cn.wengsj.mms.model.Manager;
import cn.wengsj.mms.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagerDao implements IManagerDao {
    Connection conn = DBUtils.getConnection();
    PreparedStatement pstm = null;

    @Override
    public List<Manager> selectByList() throws SQLException {
        String sql = "select * from manager";
        pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        Manager manager;
        List<Manager> list = new ArrayList<>();
        while (rs.next()){
            manager = DBUtils.getManager(rs);
            list.add(manager);
        }
        return list;
    }

    @Override
    public List<Manager> selectByPage(int begin, int limit) throws SQLException {
        StringBuffer sql = new StringBuffer("select * from manager limit ?,?");

        pstm = conn.prepareStatement(sql.toString());
        pstm.setInt(1,begin);
        pstm.setInt(2,limit);

        ResultSet rs = pstm.executeQuery();
        Manager manager;
        List<Manager> list = new ArrayList<>();
        while (rs.next()){
            manager = DBUtils.getManager(rs);
            list.add(manager);
        }
        return list;
    }

    @Override
    public int selectCount() throws SQLException {
        String sql = "select count(*) from manager";
        pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public Manager selectById(Integer id) throws SQLException {
        String sql = "select * from manager where Manager_Id = ?";
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();
        Manager manager = null;
        if (rs.next()){
            manager = DBUtils.getManager(rs);
        }
        return manager;
    }

    @Override
    public int save(Manager manager) throws SQLException {
        String sql;
        Integer id = manager.getId();
        if(id != null){
            sql = "update manager set Manager_Account =?, Manager_Password=?, Manager_Name=?, Salt=?, Manager_Tel=?, Popedom_Id=? where Manager_Id = ?";

        }else{
            sql = "insert into manager(Manager_Name, Manager_Password, Manager_Account, Salt, Manager_Tel, Popedom_Id) values(?, ?, ?, ?, ?, ?)";
        }
        pstm = conn.prepareStatement(sql);
        pstm.setString(1,manager.getUsername());
        pstm.setString(2,manager.getPassword());
        pstm.setString(3,manager.getNickname());
        pstm.setString(4,"heheda");
        pstm.setString(5,manager.getMobile());
        pstm.setInt(6,manager.getType());
        if(id != null) {
            pstm.setInt(7, manager.getId());
        }
        return pstm.executeUpdate();
    }

    @Override
    public int delete(Integer id) throws SQLException {
        String sql = "delete from manager where id = ?";
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1,id);

        return pstm.executeUpdate();
    }

    @Override
    public Manager selectByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "select * from manager where Manager_Account = ? and Manager_Password = ?";
        pstm = conn.prepareStatement(sql);
        pstm.setString(1,username);
        pstm.setString(2,password);
        ResultSet rs = pstm.executeQuery();
        Manager manager = null;
         if (rs.next()){
             manager = DBUtils.getManager(rs);
         }
        return manager;
    }



    @Override
    public Manager selectByUsername(String username) throws SQLException {
        String sql = "select * from manager where Manager_Account = ?";
        pstm = conn.prepareStatement(sql);
        pstm.setString(1,username);
        ResultSet rs = pstm.executeQuery();
        Manager manager = null;
        if (rs.next()){
            manager = DBUtils.getManager(rs);
        }
        return manager;
    }

    @Override
    public List<Manager> selectByPageAndUsername(int begin, int limit, String condition) throws SQLException {
        StringBuffer sql = new StringBuffer("select * from manager ");

        if(condition != null && !"".equals(condition)){
            sql.append("where Manager_Account like ? limit ?,?");
        }else{
            sql.append("limit ?,?");
        }
        pstm = conn.prepareStatement(sql.toString());

        if(condition != null && !"".equals(condition)) {
            pstm.setString(1, "%" + condition + "%");
            pstm.setInt(2, begin);
            pstm.setInt(3, limit);
        }else{
            pstm.setInt(1,begin);
            pstm.setInt(2,limit);
        }

        ResultSet rs = pstm.executeQuery();
        Manager manager;
        List<Manager> list = new ArrayList<>();
        while (rs.next()){
            manager = DBUtils.getManager(rs);
            list.add(manager);
        }
        return list;
    }

    @Override
    public int selectCountByUsername(String condition) throws SQLException {
        StringBuffer sql = new StringBuffer("select count(*) from manager ");
        if(condition != null && !"".equals(condition)){
            sql.append("where Manager_Account like ?");
        }
        pstm = conn.prepareStatement(sql.toString());
        if(condition != null && !"".equals(condition)){
            pstm.setString(1,"%"+condition+"%");
        }
        ResultSet rs = pstm.executeQuery();
        if (rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public Set<String> getRoles(String username) throws SQLException {
        String sql = " SELECT Popedom_Name FROM popedom,manager WHERE manager.Manager_Account = ? AND popedom.Popedom_Id = manager.Popedom_Id ";

        pstm = conn.prepareStatement(sql);
        pstm.setString(1,username);
        ResultSet rs = pstm.executeQuery();
        Set<String> roles = new HashSet<>();
        while(rs.next()) {
            roles.add(rs.getString("Popedom_Name"));
        }

        return roles;
    }

    @Override
    public Set<String> getPermissions(String username) throws SQLException {
        Set<String> permissions = new HashSet<>();

        String sql = " SELECT Permission_Id FROM popedom p,manager m WHERE m.Manager_Account = ? AND m.Popedom_Id = p.Popedom_Id ";

        // String sql = "SELECT name FROM popedom p,manager m,permission per WHERE m.Manager_Account = ? AND m.Popedom_Id = p.Popedom_Id AND p.Popedom_Id = per.Popedom_Id";
        pstm = conn.prepareStatement(sql);
        pstm.setString(1,username);
        ResultSet rs = pstm.executeQuery();
        String permissionsStr  = null;
        if(rs.next()) {
            permissionsStr = rs.getString("Permission_Id");
        }

        if(permissionsStr == null) return permissions;
        String[] array = permissionsStr.split(",");

        for(int i = 0; i<array.length; ++i) {
            sql = " SELECT Permission FROM manager_permission WHERE Permission_Id=? ";
            pstm = conn.prepareStatement(sql);
            pstm.setString(1,array[i]);
            rs = pstm.executeQuery();
            if(rs.next()) {
                permissions.add(rs.getString("Permission"));
            }
        }

        return permissions;
    }

}

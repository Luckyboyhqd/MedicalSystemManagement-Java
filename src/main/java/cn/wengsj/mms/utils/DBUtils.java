package cn.wengsj.mms.utils;

import cn.wengsj.mms.model.Manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class DBUtils {
    private static Connection conn = null;
    private static String driverPath;
    private static String dbUrl;
    private static String dbUserName;
    private static String dbPassword;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(DBUtils.class.getResource("/").getPath()+"/main/resource/jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverPath = properties.getProperty("driverPath");
        dbUserName = properties.getProperty("dbUserName");
        dbPassword = properties.getProperty("dbPassword");
        dbUrl = properties.getProperty("dbUrl");

        try {
            Class.forName(driverPath);
            conn  = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return conn;
    }

    /**
     * 使用rs包装Manager方法提取
     * @param rs
     * @return manager
     * @throws SQLException
     */
    public static Manager getManager(ResultSet rs) throws SQLException {
        Manager manager;
        manager = new Manager();
        manager.setId(rs.getInt(1));
        manager.setUsername(rs.getString(2));
        manager.setPassword(rs.getString(4));
        manager.setNickname(rs.getString(3));
        manager.setSalt(rs.getString(5));
        manager.setMobile(rs.getString(6));
        manager.setType(rs.getInt(7));
        return manager;
    }

}

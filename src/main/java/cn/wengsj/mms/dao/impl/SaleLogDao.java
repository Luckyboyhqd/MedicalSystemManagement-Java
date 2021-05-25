package cn.wengsj.mms.dao.impl;

import cn.wengsj.mms.dao.ISaleLogDao;
import cn.wengsj.mms.model.SaleLogBean;
import cn.wengsj.mms.model.dto.*;
import cn.wengsj.mms.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SaleLogDao implements ISaleLogDao {
    Connection conn = DBUtils.getConnection();
    PreparedStatement pstm = null;
    /**
     * 时间戳-->时间
     */
    private static String stampToDate(String stamp){
        String res;
        SimpleDateFormat dataFormat =new SimpleDateFormat("yyyy-MM-dd");
        long t = new Long(stamp);
        Date date = new Date(t);
        res = dataFormat.format(date);
        return res;
    }
    private List<SaleLogBean> resultSetToList(ResultSet res) throws SQLException {
        List<SaleLogBean> list  = new ArrayList<>();
        SaleLogBean saleLog;
        String date;
        while(res.next()){
            saleLog = new SaleLogBean();
            date = res.getString("Sale_Date");
            saleLog.setSale_Date(SaleLogDao.stampToDate(date));
            saleLog.setMedicine_Log_Id(res.getInt("Medicine_Log_Id"));
            //改为Medicine_GB
            saleLog.setMedicine_GB(res.getString("Medicine_GB"));
            saleLog.setSale_Number(res.getInt("Sale_Number"));
            saleLog.setSale_Price(res.getDouble("Sale_Price"));
            //ID改为用户User_Account
            saleLog.setUser_Account(res.getString("Manager_Account"));
            list.add(saleLog);
        }
        return list;
    }
    @Override
    public List<SaleLogBean> selectByList() throws SQLException {
        String sql = "select * from medicine_log";
        this.pstm = conn.prepareStatement(sql);
        ResultSet res = this.pstm.executeQuery();
        return this.resultSetToList(res);
    }

    @Override
    public List<SaleLogBean> selectByPage(int begin, int limit) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public SaleLogBean selectById(Integer id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int selectCount() throws SQLException {
        throw new UnsupportedOperationException();
    }


    @Override
    public int save(SaleLogBean entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SaleLogBean> selectByKey(String str) throws SQLException{
        String sql = " select medicine_log.*, manager.Manager_Account ,medicine.Medicine_GB " +
                     " from medicine_log,`manager`,`medicine` " +
                     " where medicine_log.User_Id = manager.Manager_Id " +
                        " AND medicine_log.Medicine_Id = medicine.Medicine_Id AND (" +
                        " Medicine_Log_Id LIKE '%" + str + "%' OR "+
                        " medicine.Medicine_GB LIKE '%" + str + "%' OR "+
                        " manager.Manager_Account LIKE '%" + str + "%' ) " +
                        " ORDER BY Medicine_Log_Id DESC; ";
        this.pstm = conn.prepareStatement(sql);
        ResultSet res = this.pstm.executeQuery();
        return this.resultSetToList(res);
    }

    /**
     * 获得前20种药品销售量排行和购买人数，以销售量从大到小排序
     * @return Bean
     */
    @Override
    public List<SaleLogByMedicineBean> queryHotMedicine(int size) throws SQLException {
        String sql =" select medicine.Medicine_Name, medicine_log.User_Id, medicine_log.Sale_Number " +
                " from medicine, medicine_log " +
                " where medicine.Medicine_Id = medicine_log.Medicine_Id " +
                " ORDER BY medicine.Medicine_Id ASC ";
        SaleLogByMedicineBean bean = null;
        List<SaleLogByMedicineBean> list = new ArrayList<>();
        try {
            this.pstm = conn.prepareStatement(sql);
            ResultSet res = this.pstm.executeQuery();
            int sale_num=0;
            int human_num=0;
            String name= null;
            while(res.next()){
                String resName=res.getString("medicine.Medicine_Name");
                int ressaleNum = res.getInt("medicine_log.Sale_Number");
                if(name == null || resName.equals(name)){
                    name = resName;
                    human_num++;
                    sale_num += ressaleNum;
                }else{
                    bean = new SaleLogByMedicineBean();
                    bean.setMedicine_Name(name);
                    bean.setHuman_Count(human_num);
                    bean.setSale_Count(sale_num);
                    list.add(bean);
                    name = resName;
                    human_num = 1;
                    sale_num = ressaleNum;
                }
            }
            bean = new SaleLogByMedicineBean();
            bean.setMedicine_Name(name);
            bean.setHuman_Count(human_num);
            bean.setSale_Count(sale_num);
            list.add(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(list);
        List<SaleLogByMedicineBean> relist = new ArrayList<>();
        for(int i = 0;i<size && i<list.size();i++)
            relist.add(list.get(i));
        return  relist;

    }

    /**
     * 获得前8位用户排行购买情况，以总消费金额从大到小排序
     * @return  用户排行数据
     */
    @Override
    public List<SaleLogByUserBean> queryUserRank(int size) {
        String sql = " select manager.Manager_Name, medicine_log.Medicine_Id, medicine_log.Sale_Price ,medicine_log.Sale_Number " +
                " from manager, medicine_log " +
                " where manager.Manager_Id = medicine_log.User_Id " +
                " ORDER BY manager.Manager_Id ASC";
        SaleLogByUserBean bean = null;
        List<SaleLogByUserBean> list = new ArrayList<>();
        int price = 0;  //消费金额
        int times = 0;  //次数
        String name = null;
        try {
            this.pstm = conn.prepareStatement(sql);
            ResultSet res = this.pstm.executeQuery();
            while(res.next()){
                String resName = res.getString("manager.Manager_Name");
                int resPrice = res.getInt("medicine_log.Sale_Price");
                int resNum = res.getInt("medicine_log.Sale_Number");
                if(name == null || resName.equals(name)){
                    name = resName;
                    price += resPrice*resNum;
                    times++;
                }else{
                    bean = new SaleLogByUserBean();
                    bean.setUser_Name(name);
                    bean.setPurchase_Times(times);
                    bean.setMonetary(price);
                    list.add(bean);
                    name = resName;
                    price = resPrice*resNum;
                    times = 1;
                }
            }
            bean = new SaleLogByUserBean();
            bean.setUser_Name(name);
            bean.setMonetary(price);
            bean.setPurchase_Times(times);
            list.add(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //排序
        Collections.sort(list);
        List<SaleLogByUserBean> relist = new ArrayList<>();
        for(int i = 0;i<size && i<list.size();i++)
            relist.add(list.get(i));

        return relist;
    }
    //药品type排序
    @Override
    public List<SaleTypeBean> querySaleTypeSort(int size){
        String sql = " select medicine_log.Sale_Number, medicine_type.Medicine_Type_Name " +
                " from medicine_log , medicine , medicine_type " +
                " where medicine.Medicine_Id = medicine_log.Medicine_Id and " +
                " medicine.Medicine_Type = medicine_type.Medicine_Type ORDER BY medicine.Medicine_Type ASC";
        List<SaleTypeBean> list  = new ArrayList<>();
        SaleTypeBean bean;
        String name = null;
        int count  = 0;
        try {
            this.pstm = conn.prepareStatement(sql);
            ResultSet res = this.pstm.executeQuery();
            while(res.next()){
                String resName = res.getString("medicine_type.Medicine_Type_Name");
                int resCount = res.getInt("medicine_log.Sale_Number");
                if(name == null || resName.equals(name)){
                    name = resName;
                    count += resCount;
                }else{
                    bean = new SaleTypeBean();
                    bean.setName(name);
                    bean.setValue(count);
                    list.add(bean);
                    count = resCount;
                    name = resName;
                }

            }//while()结束后，最后的一条没有加入，所以要在加入一次
            bean = new SaleTypeBean();
            bean.setName(name);
            bean.setValue(count);
            list.add(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(list);
        List<SaleTypeBean> relist = new ArrayList<>();
        for(int i = 0;i<size && i<list.size();i++)
            relist.add(list.get(i));

        return relist;
    }

    @Override
    public int queryTotalSale() {
        String sql = "select sum(Sale_Number * Sale_Price) from medicine_log";
        try {
            this.pstm = conn.prepareStatement(sql);

            ResultSet res = this.pstm.executeQuery();
            if(res.next()){
                return (int)res.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void initDays(int[] d){
        for(int i = 0;i<d.length;i++)
            d[i]=0;
    }

    /**
     * 获取近7天的时间戳
     * @return
     */
    private long[] getDays(){
        long time = System.currentTimeMillis();//获得当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        String d = format.format(date);
        long[] days = new long[9];//0无用 小于day[1]表示无效，day[1]<x<day[2]表示第一天
        long day = 86400000;//精度13位时间戳每日相差86400000,十位的差86400；
        try {
            long now= format.parse(d).getTime();//获取当日0时的时间
            for(int i = 1;i<9;i++)
                days[i] = now-(7-i)*day;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    private int toDay(String stamp,long[] days){
        long t = Long.parseLong(stamp);
        for(int i = 1;i<days.length;i++){
            if(t<days[i])
                return i-1;
        }
        return 0;
    }

    public List<StockForIndexBean> getDaySale(List<StockForIndexBean> paramList){
        String sql = "select DISTINCT medicine.Medicine_Type, medicine_log.Sale_Date, medicine_log.Sale_Number,medicine_type.Medicine_Type_Name \n" +
                " from medicine, medicine_type, medicine_log ,medicine_stock\n" +
                " where medicine.Medicine_Type = medicine_type.Medicine_Type  AND medicine_log.Medicine_Id = medicine.Medicine_Id  AND \n" +
                "(medicine_type.Medicine_Type_Name = '清热解毒类' OR  medicine_type.Medicine_Type_Name = '抗菌消炎类' OR  medicine_type.Medicine_Type_Name = '止咳平喘类' OR  medicine_type.Medicine_Type_Name = '胃肠道类' OR  medicine_type.Medicine_Type_Name = '维生素类' OR  medicine_type.Medicine_Type_Name = '心脑血管类' OR  medicine_type.Medicine_Type_Name = '五官外用类')\n" +
                "ORDER BY medicine_type.Medicine_Type ASC;\n";
        long[] days = getDays();           //获得7天的时间戳
        StockForIndexBean bean = null;
        //bean字段
        String name = null;
        int stockNumber = 0;
        int[] daySale = new int[8];
        initDays(daySale);		//字段设0;
        //数据库读取字段
        String resName;		//名字
        int resDaySale;		//当日销售
        int resDay;		//日期
        String resStamp;	//时间戳
        try {
            this.pstm = conn.prepareStatement(sql);
            ResultSet res = this.pstm.executeQuery();
            while(res.next()){
                resName = res.getString("medicine_type.Medicine_Type_Name");	//获取名
                resDaySale = res.getInt("medicine_log.Sale_Number");//获取单日售量
                resStamp = res.getString("medicine_log.Sale_Date");	//获取销售日
                resDay = toDay(resStamp,days);      //销售日转化日 1 - 7;
                if(name==null || resName.equals(name)){
                    name = resName;
                    if(resDay != 0)
                        daySale[resDay]+=resDaySale;
                }else{
                    for(int i = 0;i<paramList.size();i++)
                        if(paramList.get(i).getName().equals(name)){
                            paramList.get(i).setDaySale(daySale);
                            break;
                        }
                    name = resName;
                    daySale = new int[8];
                    initDays(daySale);
                    if(resDay!=0)
                        daySale[resDay]=resDaySale;
                }
            };//最后的记录添加
            for(int i = 0;i<paramList.size();i++)
                if(paramList.get(i).getName().equals(name)){
                    paramList.get(i).setDaySale(daySale);
                    break;
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i=0;i<paramList.size();i++){
            int[] c = paramList.get(i).getDaySale();
            int m = 0;
            for(int j=0;j<c.length;j++)
                m += c[j];
            paramList.get(i).setSaleCount(m);
        }
        return paramList;
    }
    public List<StockForIndexBean> getStock(){
        String sql ="select medicine_type.Medicine_Type_Name, medicine_stock.Medicine_Stock_Number\n" +
                "from medicine, medicine_type,medicine_stock\n" +
                "where medicine.Medicine_Type = medicine_type.Medicine_Type AND\n" +
                "\t\t\tmedicine.Medicine_Id = medicine_stock.Medicine_Id AND \n" +
                "\t\t\t(medicine_type.Medicine_Type_Name = '清热解毒类' OR  \n" +
                "       medicine_type.Medicine_Type_Name = '抗菌消炎类' OR \n" +
                "       medicine_type.Medicine_Type_Name = '止咳平喘类' OR  \n" +
                "       medicine_type.Medicine_Type_Name = '胃肠道类' OR\n" +
                "       medicine_type.Medicine_Type_Name = '维生素类' OR  \n" +
                "       medicine_type.Medicine_Type_Name = '心脑血管类' OR\n" +
                "       medicine_type.Medicine_Type_Name = '五官外用类')\n" +
                "      ORDER BY medicine_type.Medicine_Type ASC;";
        String name = null;
        int num = 0;
        String resName;
        int resNum;
        List<StockForIndexBean> list = new ArrayList<>();
        StockForIndexBean bean = null;
        try {
            this.pstm = conn.prepareStatement(sql);
            ResultSet res = this.pstm.executeQuery();
            while(res.next()){
                resName = res.getString("medicine_type.Medicine_Type_Name");
                resNum  = res.getInt("medicine_stock.Medicine_Stock_Number");
                if(name == null || resName.equals(name)){
                    name = resName;
                    num+=resNum;
                }else {
                    bean = new StockForIndexBean();
                    bean.setName(name);
                    bean.setStockNumber(num);
                    list.add(bean);
                    name = resName;
                    num = resNum;
                }
            }
            bean = new StockForIndexBean();
            bean.setName(name);
            bean.setStockNumber(num);
            list.add(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private String[] getDateString(long[] date){
        long time = System.currentTimeMillis();//获得当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] res = new String[7];
        Date da;
        for(int i=1;i<8;i++){
            da = new Date(date[i]);
            res[i-1] = format.format(da);
        }
        return res;

    }
    public DaySaleBean getDaySale() {
        String sql = "select medicine_log.Sale_Date,medicine_log.Sale_Number, medicine_log.Medicine_Log_Id, medicine_log.Sale_Price\n" +
                "from medicine_log\n" +
                "ORDER BY Medicine_Log_Id ASC;";
        long[] days = getDays();           //获得7天的时间戳
        DaySaleBean bean = new DaySaleBean();
        double resSale;     //销售总金额
        int resCount;       //销售总量
        String resStamp;    //时间戳
        int resDay;         //日期
        double[] sale = new double[8];  //销售总额
        int[] count = new int[8];       //销售总量
        for (int i = 0; i < 8; i++) {
            sale[i] = 0.00;
            count[i] = 0;
        }
        try {
            this.pstm = conn.prepareStatement(sql);
            ResultSet res = this.pstm.executeQuery();
            while (res.next()) {
                resStamp = res.getString("medicine_log.Sale_Date");
                resDay = toDay(resStamp, days);
                if (resDay != 0) {
                    resCount = res.getInt("medicine_log.Sale_Number");
                    resSale = res.getDouble("medicine_log.Sale_Price") * resCount;
                    sale[resDay] += (double)(Math.round(resSale*100)/100);
                    count[resDay] += resCount;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bean.setDaySale(sale);
        bean.setDayNumber(count);
        bean.setDayDate(this.getDateString(days));
        return bean;
    }
}

package cn.wengsj.mms.service.impl;

import cn.wengsj.mms.dao.impl.SaleLogDao;
import cn.wengsj.mms.model.dto.*;
import com.alibaba.fastjson.JSON;

import java.sql.SQLException;
import java.util.List;

public class SaleLogForIndexService {
    SaleLogDao dao = new SaleLogDao();
    /**
     *  获得热门药
     * @param num 需要的条数
     * @return
     */
    public List<SaleLogByMedicineBean> queryByMedicine(int num){
        List<SaleLogByMedicineBean> list = null;
        try {
            list = dao.queryHotMedicine(num);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @return 默认返回20条记录
     */
    public List<SaleLogByMedicineBean> queryByMedicine(){
        return this.queryByMedicine(20);

    }

    /**
     * 查询土豪
     * @param num 记录条数
     * @return
     */
    public List<SaleLogByUserBean> queryByUser(int num){
        return dao.queryUserRank(num);
    }
    public  List<SaleLogByUserBean> queryByUser(){
        return this.queryByUser(5);
    }

    /**
     * 查询热门药品种类
     * @param num
     * @return
     */
    public List<SaleTypeBean> querySale_Type(int num){
        return dao.querySaleTypeSort(num);
    }
    public List<SaleTypeBean> querySale_Type(){
        return this.querySale_Type(8);
    }
    public int querySale_Total(){
        return this.dao.queryTotalSale();
    }

    /**
     * 库存和一周每天的销售量
     * 其中的日期下标从1开始
     * 即周一到周日对应 1-7
     * 无传入参数，放回固定类别的库存和销售量（懒要修改其他的，请自行修改sql语句）
     * @return 返回
     */
    public List<StockForIndexBean> queryStock(){
        List<StockForIndexBean> list = dao.getStock();
       // return list;
        return dao.getDaySale(list);
    }

    /**
     * 0位无用
     * 1-7对应周一到周日
     * @return
     */
    public DaySaleBean getDaySale(){
        return dao.getDaySale();
    }
}
//class M{
//    public static void main(String[] args) {
//        SaleLogForIndexService service = new SaleLogForIndexService();
//        String res ;
//        System.out.println("-药-");
//        res = JSON.toJSONString(service.queryByMedicine(100));
//        System.out.println(res);
//        System.out.println("----");
//        res = JSON.toJSONString(service.queryByMedicine());
//        System.out.println(res);
//        System.out.println("-人-");
//        res = JSON.toJSONString(service.queryByUser(100));
//        System.out.println(res);
//        System.out.println("----");
//        res = JSON.toJSONString(service.queryByUser());
//        System.out.println(res);
//        System.out.println("-类-");
//        res = JSON.toJSONString(service.querySale_Type(100));
//        System.out.println(res);
//        System.out.println("----");
//        res = JSON.toJSONString(service.querySale_Type());
//        System.out.println(res);
//        System.out.println("----");
//        res = JSON.toJSONString(service.queryStockAndSale());
//        System.out.println(res);
//    }
//}

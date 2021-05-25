package cn.wengsj.mms.action;

import cn.wengsj.mms.model.SaleLogBean;
import cn.wengsj.mms.model.dto.*;
import cn.wengsj.mms.service.impl.SaleLogForIndexService;
import cn.wengsj.mms.service.impl.SaleLogService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleLogServlet extends BaseServlet {
    SaleLogService saleLogService = new SaleLogService();
    SaleLogForIndexService saleLogForIndexService = new SaleLogForIndexService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        super.doPost(req, resp);

        String url = req.getRequestURI();
        String method = url.substring(1, url.length() - 8);
        try {
            switch (method) {
                case "list":
                    this.getList(req, resp);
                    break;
                case "hotMed":
                    this.hotMedicines(req, resp);
                    break;
                case "userRank":
                    this.userRank(req, resp);
                    break;
                case "medType":
                    this.medType(req, resp);
                    break;
                case "stock":
                    this.stock(req, resp);
                    break;
                case "daySale":
                    this.daySale(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void daySale(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DaySaleBean daySaleBean = saleLogForIndexService.getDaySale();
        JSONObject jsonObject = new JSONObject();
        if(daySaleBean !=null ){
            jsonObject.put("code",0);
            jsonObject.put("msg","");
            jsonObject.put("data",daySaleBean);
        }else{
            jsonObject.put("code",0);
            jsonObject.put("msg","查询数据失败");
            jsonObject.put("count","");
        }
        String json = jsonObject.toString();
        PrintWriter out = resp.getWriter();
        out.print(json);
    }

    private void stock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<StockForIndexBean> stockForIndexList = saleLogForIndexService.queryStock();
        JSONObject jsonObject = new JSONObject();
        if(stockForIndexList !=null && stockForIndexList.size()!=0){
            jsonObject.put("code",0);
            jsonObject.put("msg","");
            jsonObject.put("count",stockForIndexList.size());
            jsonObject.put("data",stockForIndexList);
        }else{
            jsonObject.put("code",0);
            jsonObject.put("msg","查询数据失败");
            jsonObject.put("count","");
            jsonObject.put("data","");
        }
        String json = jsonObject.toString();
        PrintWriter out = resp.getWriter();
        out.print(json);
    }


    private void medType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //调用热门药品种类service
        List<SaleTypeBean> saleTypeBeanList = saleLogForIndexService.querySale_Type();
        JSONObject jsonObject = new JSONObject();
        if(saleTypeBeanList !=null && saleTypeBeanList.size()!=0){
            jsonObject.put("code",0);
            jsonObject.put("msg","");
            jsonObject.put("count",saleTypeBeanList.size());
            jsonObject.put("data",saleTypeBeanList);
            jsonObject.put("total",saleLogForIndexService.querySale_Total());
        }else{
            jsonObject.put("code",0);
            jsonObject.put("msg","查询数据失败");
            jsonObject.put("count","");
            jsonObject.put("data","");
            jsonObject.put("total","");
        }
        String json = jsonObject.toString();
        PrintWriter out = resp.getWriter();
        out.print(json);
    }

    private void hotMedicines(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        //调用热门药品service
        List<SaleLogByMedicineBean> saleLogByMedicineBeanList = saleLogForIndexService.queryByMedicine();
        JSONObject jsonObject = new JSONObject();
        if(saleLogByMedicineBeanList !=null && saleLogByMedicineBeanList.size()!=0){
            jsonObject.put("code",0);
            jsonObject.put("msg","");
            jsonObject.put("count",saleLogByMedicineBeanList.size());
            jsonObject.put("data",saleLogByMedicineBeanList);
        }else{
            jsonObject.put("code",0);
            jsonObject.put("msg","查询数据失败");
            jsonObject.put("count","");
            jsonObject.put("data","");
        }
        String json = jsonObject.toString();
        PrintWriter out = resp.getWriter();
        out.print(json);
    }

    private void userRank(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        //调用热门用户service
        List<SaleLogByUserBean> saleLogByUserBeanList = saleLogForIndexService.queryByUser();
        JSONObject jsonObject = new JSONObject();
        if(saleLogByUserBeanList !=null && saleLogByUserBeanList.size()!=0){
            jsonObject.put("code",0);
            jsonObject.put("msg","");
            jsonObject.put("count",saleLogByUserBeanList.size());
            jsonObject.put("data",saleLogByUserBeanList);
        }else{
            jsonObject.put("code",0);
            jsonObject.put("msg","查询数据失败");
            jsonObject.put("count","");
            jsonObject.put("data","");
        }
        String json = jsonObject.toString();
        PrintWriter out = resp.getWriter();
        out.print(json);
    }

    private void getList(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        System.out.println(req.getParameter("page"));
        int page=(new Integer(req.getParameter("page")));
        int limit=(new Integer(req.getParameter("limit")));
        String key = req.getParameter("key");
        if(key == null)  key="";
        List<SaleLogBean> resList = new ArrayList<>();
        int index = (page-1)*limit;
        List<SaleLogBean> list = saleLogService.selectByKey(key);
        while(limit> 0 && index<list.size()){
            resList.add(list.get(index));
            limit--;
            index++;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("msg","");
        jsonObject.put("count",list.size());
        jsonObject.put("data",resList);
        String json = jsonObject.toString();
        PrintWriter out = resp.getWriter();
        out.print(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req,resp);
    }
}

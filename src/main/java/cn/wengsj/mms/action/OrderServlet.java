package cn.wengsj.mms.action;

import cn.wengsj.mms.model.Drug;
import cn.wengsj.mms.model.Manager;
import cn.wengsj.mms.model.MedicinesOrder;
import cn.wengsj.mms.model.PageBean;
import cn.wengsj.mms.service.IOrderService;
import cn.wengsj.mms.service.impl.DrugService;
import cn.wengsj.mms.service.impl.OrderService;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OrderServlet extends BaseServlet {
    DrugService drugService = new DrugService();
    IOrderService ios = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        super.doPost(req, resp);

        // 获取请求路径，请求路径中带有行为如
        String requestPath = req.getServletPath(); // 地址lhttp://localhost:8080/buy.order
//        System.out.println(requestPath); // 打印结果值/buy.order
        String requestAction = requestPath.substring(1, requestPath.indexOf("."));
        // 下面三局是测试用户用的
//        Manager manager = new Manager();
//        manager.setId(1001);
//        req.getSession().setAttribute("manager", manager);

        // 根据行为调用不同的方法
        try {
            switch (requestAction) {
                case "buy":
                    addOrder(req, resp);
                    break;
                case "list":
                    getDrugList(req, resp);
                    break;
                case "get":
                    getDrugById(req, resp);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加订单
     */
    private void addOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        Map<String, Object> data = new HashMap<>();
        Manager currentUser = (Manager) req.getSession().getAttribute("manager");
        // 获取订单中的药品ID，购买数量，库存数量，药品单价，用户ID
        int medicinId = Integer.parseInt(req.getParameter("medicineId"));
        int saleNumber = Integer.parseInt(req.getParameter("saleNumber"));
        int stockNumber = Integer.parseInt(req.getParameter("medicineStockNumber"));
        double medicinePrice = Double.parseDouble(req.getParameter("medicinePrice"));
        int userId = currentUser.getId();
//        System.out.println(medicinId+" "+saleNumber+" "+stockNumber+" "+medicinePrice+" ");
        if (saleNumber > stockNumber) {
            data.put("state", -1);
            data.put("msg", "购买数量不能大于库存数量");
        } else if (saleNumber <= 0) {
            data.put("state", 0);
            data.put("msg", "购买数量必须大于0");
        } else {
            // 调用dao层插入数据
            MedicinesOrder mo = new MedicinesOrder();
            // 依次设置MedicineOrder的药品Id，购买价格，购买数量，购买者的Id
            mo.setMedicineId(medicinId);
            mo.setMedicinePrice(medicinePrice);
            mo.setMedicineSaleNum(saleNumber);
            mo.setUserId(userId);
            boolean flag = ios.addOrder(mo, stockNumber);
            if (flag) {
                data.put("state", 1);
                data.put("msg", "购买成功");

            } else {
                data.put("state", -1);
                data.put("msg", "购买失败");
            }
        }
        resp.getWriter().write(JSON.toJSONString(data));
    }

    /**
     * 用户点击立即购买时弹出窗体所需要的药品
     */
    private void getDrugById(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int medicineId = Integer.valueOf(req.getParameter("id"));
        Drug drug = ios.selectDrugById(medicineId);
        if (drug != null) {
            resp.getWriter().write(JSON.toJSONString(drug));
        }
    }

    /**
     * 初次点击购买页面中显示数据的方法以及输入查询信息显示
     */
    private void getDrugList(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String condition = req.getParameter("condition");
        // System.out.println(condition);// 这是用来测试的
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        PageBean<Drug> pageBean = drugService.selectDrugsByPage(page, limit, condition);
        Map<String, Object> data = new HashMap<>();
        data.put("code", "0");
        data.put("msg", "");
        data.put("count", pageBean.getTotalCount());
        data.put("data", pageBean.getPageData());
        resp.getWriter().write(JSON.toJSONString(data));
    }


}

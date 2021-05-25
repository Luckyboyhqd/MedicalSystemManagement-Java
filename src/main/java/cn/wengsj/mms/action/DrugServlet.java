package cn.wengsj.mms.action;

import cn.wengsj.mms.model.Drug;
import cn.wengsj.mms.model.PageBean;
import cn.wengsj.mms.service.IDrugService;
import cn.wengsj.mms.service.impl.DrugService;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DrugServlet extends BaseServlet {

    private IDrugService drugService = new DrugService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        super.doPost(req, resp);
        String url = req.getRequestURI();
        String method = url.substring(1, url.length() - 5);
        try {
            switch (method) {
                case "list":
                    this.getDrugList(req, resp);
                    break;
                case "add":
                    this.saveDrug(req, resp);
                    break;
                case "delete":
                    this.deleteDrug(req, resp);
                    break;
                case "update":
                    this.updateDrug(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新药品
     *
     * @param req
     * @param resp
     * @throws SQLException
     * @throws IOException
     */
    private void updateDrug(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String drugData = req.getParameter("data");
        Drug drug = JSON.parseObject(drugData, Drug.class);
        int flag = drugService.updateDrug(drug);
        Map<String, Object> data = new HashMap<>();
        data.put("flag", flag);
        data.put("msg", ((flag == 0) ? "更新失败" : ""));
        resp.getWriter().write(JSON.toJSONString(data));
    }


    /**
     * 删除药品
     *
     * @param req
     * @param resp
     * @throws SQLException
     * @throws IOException
     */
    private void deleteDrug(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String drugData = req.getParameter("medicineId");
        int flag = 0;
        flag = drugService.delete(Integer.parseInt(drugData));
        Map<String, Object> data = new HashMap<>();
        data.put("flag", flag);
        data.put("msg", ((flag == 0) ? "删除失败" : ""));

        resp.getWriter().write(JSON.toJSONString(data));
    }

    /**
     * 保存药品
     *
     * @param req
     * @param resp
     * @throws SQLException
     * @throws IOException
     */
    private void saveDrug(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String drugData = req.getParameter("data");
        Drug drug = JSON.parseObject(drugData, Drug.class);

        int flag = drugService.save(drug);

        Map<String, Object> data = new HashMap<>();
        data.put("flag", flag);
        data.put("msg", ((flag == 0) ? "添加失败" : ""));

        resp.getWriter().write(JSON.toJSONString(data));
    }

    /**
     * 获得药品列表
     *
     * @param req
     * @param resp
     * @throws SQLException
     * @throws IOException
     */
    private void getDrugList(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String condition = req.getParameter("condition");
        int page = Integer.parseInt(req.getParameter("curr"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        PageBean<Drug> pageBean = drugService.selectDrugsByPage(page, limit, condition);
        Map<String, Object> data = new HashMap<>();
        data.put("code", "0");
        data.put("msg", "");
        data.put("count", pageBean.getTotalCount());
        data.put("data", pageBean.getPageData());

        resp.getWriter().write(JSON.toJSONString(data));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }
}

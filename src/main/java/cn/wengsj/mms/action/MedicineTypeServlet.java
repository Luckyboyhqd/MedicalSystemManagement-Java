package cn.wengsj.mms.action;

import cn.wengsj.mms.dao.IMedicineTypeDao;
import cn.wengsj.mms.dao.impl.MedicineTypeDao;
import cn.wengsj.mms.model.MedicineType;
import cn.wengsj.mms.model.TypeBean;
import cn.wengsj.mms.service.impl.MedicineTypeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicineTypeServlet extends BaseServlet {
    public Integer page = null;
    public Integer limit = null;
    public Integer count = null;
    public String which = null;
    PrintWriter out = null;
    //    IMedicineTypeDao dao = new MedicineTypeDao();
    MedicineTypeService service = new MedicineTypeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        super.doPost(request, response);
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        super.doGet(request, response);
        which = request.getRequestURI().substring(1).split("\\.")[0];
        // System.out.println(which);
//        System.out.println("123");
//        select(request,response);
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (which) {
            case "select":
                select(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            case "edit":
                edit(request, response);
                break;
            case "selectByKey":
                selectByKey(request, response);
                break;
            case "add":
                add(request, response);
                break;
            case "selectTypeNameAndId":
                selectTypeNameAndId(request, response);
                break;
        }
    }

    public void select(HttpServletRequest request, HttpServletResponse response) {
        List<MedicineType> list = new ArrayList<MedicineType>();
        page = Integer.valueOf(request.getParameter("page"));
        limit = Integer.valueOf(request.getParameter("limit"));
        try {
//            list = dao.selectByList();
//            list = dao.selectByPage((page-1)*limit,limit);
            list = service.getMedicineTypeByPage((page - 1) * limit, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        try {
//            count = dao.selectCount();
            count = service.getCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jsonObject.put("count", count);
        jsonObject.put("data", list);
        out.print(jsonObject.toJSONString());
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getRequestURI());
        int id = Integer.valueOf(request.getParameter("id"));
        try {
//            dao.delete(id);
            service.removeMedicineType(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) {
        String typeName = request.getParameter("typeName");
        String dest = request.getParameter("dest");
        Integer id = Integer.valueOf(request.getParameter("id"));
        Boolean result = false;
//        System.out.println("typeName:"+typeName);
//        System.out.println("dest:"+dest);
//        System.out.println("id:"+id);
        try {
//            result = dao.edit(typeName, dest, id);
            result = service.eidtMedicineType(typeName, dest, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isEdit", result);
            out.print(jsonObject.toJSONString());
        }
    }

    public void selectByKey(HttpServletRequest request, HttpServletResponse response) {
        List<MedicineType> list = new ArrayList<MedicineType>();
        page = Integer.valueOf(request.getParameter("page"));
        limit = Integer.valueOf(request.getParameter("limit"));
        String key = request.getParameter("key");
        try {
//            list = dao.selectByKey(key,(page-1)*limit,limit);
            list = service.getMedicineTypeByKey(key, (page - 1) * limit, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        try {
//            count = dao.selectPageCount(key);
            count = service.getPageCount(key);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jsonObject.put("count", count);
        jsonObject.put("data", list);
        out.print(jsonObject.toJSONString());
    }

    public void add(HttpServletRequest request, HttpServletResponse response) {
        String typeName = request.getParameter("typeName");
        String dest = request.getParameter("dest");
        String date = Long.toString(System.currentTimeMillis());
        MedicineType medicineType = new MedicineType();
        medicineType.setTypeName(typeName);
        medicineType.setDate(date);
        medicineType.setDest(dest);
        try {
//            dao.save(medicineType);
            service.addMedicineType(medicineType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("add", "true");
        out.print(jsonObject.toJSONString());
    }

    public void selectTypeNameAndId(HttpServletRequest request, HttpServletResponse response) {
        List<MedicineType> list = new ArrayList<MedicineType>();
        try {
//            list = dao.selectByList();
            list = service.getTypeNameAndId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<TypeBean> arr = new ArrayList<>();

        for (MedicineType m : list) {
            TypeBean temp = new TypeBean();
            temp.setId(m.getId());
            temp.setName(m.getTypeName());
            arr.add(temp);
        }
        out.print(JSON.toJSONString(arr));
    }
}

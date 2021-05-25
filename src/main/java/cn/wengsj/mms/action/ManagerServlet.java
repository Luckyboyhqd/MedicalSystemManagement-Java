package cn.wengsj.mms.action;

import cn.wengsj.mms.model.Manager;
import cn.wengsj.mms.model.PageBean;
import cn.wengsj.mms.service.IManagerService;
import cn.wengsj.mms.service.impl.ManagerService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ManagerServlet extends BaseServlet {
    private IManagerService managerService = new ManagerService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        super.doPost(request,response);
        String uri = request.getRequestURI();
        String method = uri.split("\\.")[0].substring(1);
//        System.out.println(method);
        try {
            switch (method) {
                case "getUser":  //获取用户昵称
                    this.getSessoinUserName(request,response); break;
                case "isLogin": //用户登录
                    this.isLogin(request,response); break;
                case "logout": //用户退出登录
                    this.logout(request,response); break;
                case "add": //添加用户
                    this.addManager(request,response); break;
                case "remove": //删除用户
                    this.removeManager(request,response); break;
                case "update": //更新用户
                    this.updateManager(request,response); break;
                case "list": //查询用户列表，（包括了分页查询和搜索）
                    this.getManagerList(request,response); break;
                case "get": //查找某一用户
                    this.getManager(request,response); break;
                case "reset": //重置密码
                    this.resetPassword(request,response); break;
                case "changePwd": //修改密码
                    this.changePassword(request,response); break;
                case "role": //获取用户角色
                    this.getRole(request,response);break;
                default: return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("admin")) {
            map.put("role",1);
        }else if(subject.hasRole("common")) {
            map.put("role",2);
        }
        response.getWriter().write(JSON.toJSONString(map));
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 无用到的
        // HttpSession session = request.getSession();
        // Subject subject = SecurityUtils.getSubject();
        // subject.logout();
        // session.invalidate();
        // response.getWriter().print("已退出");
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            IOException {
        String id = null;
        String newpwd = request.getParameter("newpwd");
        Manager manager = (Manager) request.getSession().getAttribute("manager");
        int count = 0;
        String msg = null;
        if(manager !=null) {
           count = managerService.changeManagerPassword(manager.getId(),newpwd);
        }else{
            count = -1;
            msg = "当前用户未登录";
        }

        Map<String,Object> map = new HashMap<>();
        map.put("state",count);
        map.put("code",0);
        map.put("msg", msg);

        response.getWriter().print(JSON.toJSONString(map));
    }

    private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            IOException {
        String id = request.getParameter("id");

        int count = managerService.resetManagerPassword(Integer.valueOf(id));

        Map<String,Object> map = new HashMap<>();
        map.put("state",count);
        map.put("code",0);
        map.put("msg", "");

        response.getWriter().print(JSON.toJSONString(map));
    }

    private void updateManager(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        //得到json格式对象，转为实体类
        String data = request.getParameter("data");
        Manager manager = JSONObject.parseObject(data, Manager.class);

        int count = managerService.updateManager(manager);

        Map<String,Object> map = new HashMap<>();
        map.put("state",count);
        map.put("code",0);
        map.put("msg", "");

        response.getWriter().print(JSON.toJSONString(map));
    }

    private void getManager(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");
        Manager manager = managerService.selectManagerById(Integer.valueOf(id));
        if(manager !=null){
            response.getWriter().print(JSON.toJSONString(manager));
        }
    }

    private void removeManager(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");
        int deleteCount = managerService.removeManager(Integer.valueOf(id));

        Map<String,Object> map = new HashMap<>();
        map.put("state",deleteCount);
        map.put("code",0);
        map.put("msg","");

        response.getWriter().print(JSON.toJSONString(map));
    }

    private void addManager(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        //得到json格式对象，转为实体类
        String data = request.getParameter("data");
        Manager manager = JSONObject.parseObject(data, Manager.class);

        int count = managerService.addManager(manager);

        Map<String,Object> map = new HashMap<>();
        map.put("state",count);
        map.put("code",0);
        map.put("msg",((count == -1) ? "该用户已存在":""));

        response.getWriter().print(JSON.toJSONString(map));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            this.doPost(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getManagerList(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String condition = request.getParameter("condition");
        int curr = Integer.valueOf(request.getParameter("curr"));
        int limit = Integer.valueOf(request.getParameter("limit"));
        PageBean<Manager> pageBean = managerService.selectManagersByPage(curr,limit,condition);
        List<Manager> list = pageBean.getPageData();
        for (Manager m :list) {
            if(m.getType() == 1){
                m.set_type("管理员");
            }else if(m.getType() == 2){
                m.set_type("普通用户");
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageBean.getTotalCount());
        map.put("data",pageBean.getPageData());

        response.getWriter().print(JSON.toJSONString(map));
    }


    private void isLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        Subject subject =SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try{
            subject.login(token);
        }catch (AuthenticationException e) {
            e.printStackTrace();
            //System.out.println("用户名或者密码错误,异常信息:"+e.getLocalizedMessage());
            // request.setAttribute("errorInfo","用户名或者密码错误");
            // request.getRequestDispatcher("login.jsp").forward(req,resp);//转发
        }

//        System.out.println(username+"-"+password);

        // Manager manager = null;
        // Map<String, Integer> map = new HashMap<>();
        // try {
        //     manager = managerService.LoginByUsernameAndPassword(username, password);
        //     String isLogin = null;
        //     if (manager != null) {
        //         HttpSession session = request.getSession();
        //         session.setAttribute("manager", manager);
        //         map.put("isLogin",1);
        //         isLogin = JSON.toJSONString(map);
        //     } else {
        //         map.put("isLogin",-1);
        //         isLogin = JSON.toJSONString(map);
        //     }
        //     response.getOutputStream().print(isLogin);
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
    }

    private void getSessoinUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Manager manager = (Manager) request.getSession().getAttribute("manager");
        if(manager !=null) {
            Map<String, String> map = new HashMap<>();
            map.put("nickname",manager.getNickname());
            map.put("password",manager.getPassword());
            String nickname = JSON.toJSONString(map);
            response.getWriter().print(nickname);
        }
    }


}

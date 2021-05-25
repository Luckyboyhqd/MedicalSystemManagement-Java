package cn.wengsj.mms.shiro;

import cn.wengsj.mms.model.Manager;
import cn.wengsj.mms.service.IManagerService;
import cn.wengsj.mms.service.impl.ManagerService;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class LoginFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);

    // @Override
    // protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
    //     return pathsMatch(/isLogin.manager,request);
    // }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return super.onAccessDenied(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        //强转为HttpServlet
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        //用户放入session
        String username = (String) token.getPrincipal();
        IManagerService managerService = new ManagerService();
        Manager manager = managerService.selectManegerByName(username);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("manager", manager);

        if(!"XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"))) {
            // 如果不是ajax请求,继续普通执行
            issueSuccessRedirect(request,response);
        }else {
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            Map<String, Integer> map = new HashMap<>();
            map.put("isLogin",1);
            out.write(JSON.toJSONString(map));
        }
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if(!"XMLHttpRequest".equals(((HttpServletRequest)request).getHeader("X-Requested-With"))) {
            // 如果不是ajax请求,继续普通执行
            setFailureAttribute(request,e);
            return true;
        }else {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                Map<String, Integer> map = new HashMap<>();
                map.put("isLogin",-1);
                out.write(JSON.toJSONString(map));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            out.flush();
            out.close();
        }
        return false;
    }
}

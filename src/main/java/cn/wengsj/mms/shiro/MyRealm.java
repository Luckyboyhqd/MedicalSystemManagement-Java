package cn.wengsj.mms.shiro;

import cn.wengsj.mms.model.Manager;
import cn.wengsj.mms.service.IManagerService;
import cn.wengsj.mms.service.impl.ManagerService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 用户授权的Realm
 */
public class MyRealm extends AuthorizingRealm {

    private IManagerService managerService = new ManagerService();

    /**
     * 为登陆成功的用户授予角色和权限
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.setRoles(managerService.getRoles(username));
        simpleAuthorizationInfo.setStringPermissions(managerService.getPermissions(username));

        return simpleAuthorizationInfo;
    }

    /**
     * 获取认证信息，验证当前登陆的用户
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户名
        String username = (String) token.getPrincipal();

        Manager manager = managerService.selectManegerByName(username);
        if (manager != null) {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, manager.getPassword(), "login");
            return authenticationInfo;
        }

        return null;
    }
}

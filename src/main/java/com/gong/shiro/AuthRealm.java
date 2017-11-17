package com.gong.shiro;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gong.model.sys.Menu;
import com.gong.model.sys.Role;
import com.gong.model.sys.User;
import com.gong.service.sys.UserService;
import com.gong.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;




import java.util.List;


public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    //注入数据库中的授权信息到reaml
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        //获取当前用户
        User user = (User) getAvailablePrincipal(principalCollection);
        //得到权限字符串
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加用户权限
        info.addStringPermission("user");
        user.setRoleList(userService.getRoleListByUserId(user.getUserId()));
        List<Role> roles = user.getRoleList();
        for(Role role :roles){
            //添加用户角色信息
            info.addRole(role.getName());
        }
            List<Menu> menus = UserUtils.getMenuList();
            for(Menu menu:menus){
                if (StringUtils.isNotBlank(menu.getPermission())){
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(menu.getPermission(),",")){
                        info.addStringPermission(permission);
                    }
                }
            }
        return info;
    }
    //注入数据库中的认证信息到reaml
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        EntityWrapper<User> ew=new EntityWrapper<>();
        ew.where("login_name={0}",upToken.getUsername());//登录名字具有唯一性
        User user = userService.selectOne(ew);
        if (user == null) {
            return null;
        }
        if (user.getDeleteFlag()==1){//判断账户是否禁止登录
            throw new LockedAccountException();
        }
        else {
            AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            return info;
        }
    }
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
    /*
    修改权限时需要手动清缓存
    在其他代码中调用：先注入reaml，在执行clearCached方法

   @Autowired
   private AuthRealm authRealm;

   authRealm.clearCached();
     */
}

package com.gong.utils;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gong.model.sys.Menu;
import com.gong.model.sys.Role;
import com.gong.model.sys.User;
import com.gong.service.sys.MenuService;
import com.gong.service.sys.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


import java.util.List;
@SuppressWarnings("unchecked")
public class UserUtils {
    private static RoleService roleService=SpringContextHolder.getBean(RoleService.class);
   // private static UserService userService=SpringContextHolder.getBean(UserService.class);
    private static MenuService menuService=SpringContextHolder.getBean(MenuService.class);
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";

    /**
     * 获取当前用户
     * @return 取不到返回 new User()
     */
    public static User getUser(){
        Subject subject = SecurityUtils.getSubject();
        User curUser = (User) subject.getPrincipal();
        if (curUser!=null)
            return curUser;
        return new User();// 如果没有登录，则返回实例化空的User对象。
    }

    public static Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
        }catch (InvalidSessionException e){
        }
        return null;
    }

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getSession().getAttribute(key);
        return obj==null?defaultValue:obj;
    }
    public static void putCache(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 获取当前用户角色列表
     * @return
     */
    public static List<Role> getRoleList(){
        List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
        if (roleList == null){
            User user = getUser();
            EntityWrapper<Role> ew=new EntityWrapper<>();
            if (user.isAdmin()){
                roleList = roleService.selectList(ew);
            }else{
                roleList = user.getRoleList();
            }
            putCache(CACHE_ROLE_LIST, roleList);
        }
        return roleList;
    }

    /**
     * 获取当前用户授权菜单
     * @return
     */
    public static List<Menu> getMenuList(){
        List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
        if (menuList == null){
            User user = getUser();
            if (user.isAdmin()){
                menuList = menuService.getAllMenuList();
            }else{
                menuList=menuService.getMenuListByUser(user.getUserId());
            }
            putCache(CACHE_MENU_LIST, menuList);
        }
        return menuList;
    }

    public static boolean judge(List<String> list,String str){
          return list!=null&&list.size()>0&&list.contains(str);
    }
}

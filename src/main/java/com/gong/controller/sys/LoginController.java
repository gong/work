package com.gong.controller.sys;



import com.gong.controller.BaseController;
import com.gong.service.sys.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gong.utils.ValidateCodeUtil.createImage;

@Controller
public class LoginController extends BaseController{
    @Autowired
    UserService userService;
    @Autowired
    SessionDAO sessionDAO;
    @RequestMapping(value = {"/","/login"},method = RequestMethod.GET)
    public String login(){
        Subject subject = SecurityUtils.getSubject();   //得到当前用户
        if (subject.getPrincipal()==null)
          return "/login";
        else{
         return "redirect:/index2";
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request) {
        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
        String message = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            message = "用户名不正确";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            message = "密码不正确";
        } else if(LockedAccountException.class.getName().equals(exceptionClassName)){
            message = "账户被禁止登录";
        }else if(exceptionClassName != null) {
            message = exceptionClassName;
        }
        //如果认证过滤器中认证成功后就直接跳转到开始访问的页面
        model.addAttribute("message", message);
        return "/login";
      /*  *//*
         * shiro登录方式：根据用户名获取密码，密码为null非法用户；有密码检查是否用户填写的密码
         * 登录成功后无需往httpsession中存放当前用户，这样就跟web容器绑定，关联太紧密；它自己创建
         * subject对象，实现自己的session。这个跟web容器脱离，实现松耦合。
         *//*

        //调用shiro判断当前用户是否是系统用户
        Subject subject = SecurityUtils.getSubject();   //得到当前用户
        Session session=subject.getSession();
        //shiro是将用户录入的登录名和密码（未加密）封装到token对象中
        UsernamePasswordToken token = new UsernamePasswordToken(loginName,password);
        try{
            subject.login(token);   //自动调用AuthRealm.doGetAuthenticationInfo

            //写seesion，保存当前user对象
            User curUser = (User)subject.getPrincipal();            //从shiro中获取当前用户
            List<Role> roles = curUser.getRoleList();
            for(Role role :roles){
                List<Menu> menus =  role.getMenuList();
                for(Menu m :menus)
                    System.out.println(m.getName());
            }
            session.setAttribute("user",curUser);  //Principal 当前用户对象
        }
        catch(UnknownAccountException e){
            model.addAttribute("message","用户名不正确");
            return "/login";
        }
        catch (IncorrectCredentialsException e){
            model.addAttribute("message","密码不正确");
            return "/login";
        }*/
       // return "redirect:/index";
    }
    @RequiresPermissions("user")
    @RequestMapping(value = "/index2")
    public String index(){
        return "index";
    }

    //配置文件配置logout不用自己写
    /*@RequestMapping(value = "/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/login";
    }*/

    @RequestMapping(value = "/getVerifyCode",method = RequestMethod.GET)
    public void getVerifyCode(HttpServletRequest request,HttpServletResponse response){
        try {
            createImage(request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

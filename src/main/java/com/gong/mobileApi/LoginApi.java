package com.gong.mobileApi;

import com.gong.annotation.MobileLoginJudge;
import com.gong.model.sys.User;
import com.gong.utils.Apires;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class LoginApi {
    @Autowired
    SessionDAO sessionDAO;
    Logger logger= LoggerFactory.getLogger(LoginApi.class);
    @RequestMapping(value = "/app/mobileLogin")
    public String mobileLogin(@RequestParam(value = "loginName")String loginName, @RequestParam(value = "password") String password){
        Subject subject = SecurityUtils.getSubject();   //得到当前用户
        Session session=subject.getSession();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName,password);
        try{
            subject.login(token);
            User curUser = (User)subject.getPrincipal();
            logger.info((String) session.getId());
            logger.info(curUser.getLoginName()+"第一次认证成功");
            session.setAttribute("user",curUser);
            return new Apires().data(session.getId()).message("token").success();
        }
        catch (IncorrectCredentialsException e){
            logger.info("第一次认证失败");
            return new Apires().message("msg3").data("第一次认证失败").success();
        }
    }

    @MobileLoginJudge
    @RequestMapping("/app/testApi")
    public String testApi(HttpServletRequest request){
        String token=request.getHeader("token");
        Session session=sessionDAO.readSession(token);
        logger.info("testApi进来了");
        return new Apires().data("上次访问时间"+session.getLastAccessTime().toLocaleString()).message("msg1").success();
    }
}

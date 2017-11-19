package com.gong.utils;

import com.gong.annotation.MobileLoginJudge;
import com.gong.model.sys.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AuthInteceptor extends HandlerInterceptorAdapter{
    Logger logger= LoggerFactory.getLogger(AuthInteceptor.class);
    @Autowired
    SessionDAO sessionDAO;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Annotation methodAnnotation=method.getAnnotation(MobileLoginJudge.class);
        response.setHeader("content-Type", "application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        if (methodAnnotation!=null){
            String sessionToken=request.getHeader("token");
            logger.info("sessionToken:"+sessionToken);
            if (StringUtils.isNoneBlank(sessionToken)){
                Session session;
                try{
                    session=sessionDAO.readSession(sessionToken);
                    session.touch();
                }catch (UnknownSessionException e){
                    session=null;
                }
                if (session!=null){
                    User u= (User) session.getAttribute("user");
                      logger.info("已登录用户:"+u.getLoginName());
                }else{
                    out.write(new Apires().data("认证已经过期").message("msg2").success().getBytes("UTF-8"));
                    return false;
                }
            }else{
                out.write(new Apires().data("需要登录才能访问此接口").message("msg4").success().getBytes("UTF-8"));
               return false;
            }
        }
        return true;
    }
}

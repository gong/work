package com.gong.controller;

import com.gong.utils.Apires;
import com.gong.utils.MyWebUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.WebAsyncUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseController {

    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class })
    public String authorizationException(HttpServletRequest request, HttpServletResponse response){
        if (MyWebUtils.isAjax(request)){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.write(new Apires().fail("没有访问权限！"));
            return null;
        }else{
            return "redirect:/error/403";
        }
    }
}

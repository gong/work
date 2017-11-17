package com.gong.utils;

import javax.servlet.http.HttpServletRequest;

public class MyWebUtils {
    public static boolean isAjax(HttpServletRequest httpServletRequest){
        String header=httpServletRequest.getHeader("x-requested-with");
        if (header!=null&&header.equalsIgnoreCase("XMLHttpRequest"))
            return true;
        else return false;
    }
}

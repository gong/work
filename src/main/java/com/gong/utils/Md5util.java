package com.gong.utils;



import org.apache.shiro.crypto.hash.Md5Hash;

public class Md5util {
    public static String  md5(String password,String salt,int hashIterations)
    {
        return new Md5Hash(password,salt,hashIterations).toString();
    }
}

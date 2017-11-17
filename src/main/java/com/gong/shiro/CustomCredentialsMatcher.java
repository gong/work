package com.gong.shiro;

import com.gong.utils.Md5util;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    //自定义密码比对
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usertoken = (UsernamePasswordToken) token;

        //注意token.getPassword()拿到的是一个char[]，不能直接用toString()，它底层实现不是我们想的直接字符串，只能强转
        Object tokenCredentials = null;
        tokenCredentials = Md5util.md5(String.valueOf(usertoken.getPassword()), usertoken.getUsername(), 2);
        Object accountCredentials = getCredentials(info);//得到数据库中的密码 这个函数来自从info中获取密码 info来自realm的认证信息初始化

        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        return equals(tokenCredentials, accountCredentials);
    }
}

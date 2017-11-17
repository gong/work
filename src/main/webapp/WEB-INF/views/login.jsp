<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp"%>
<html>
<head>
    <title>登录</title>
    <meta name="decorator" content="default"/>
    <script>
        layui.use(['form', 'layer'], function() {
            layer = layui.layer
                , form = layui.form;
            $(function () {
                var flag=${not empty message}
                if(flag)
                    layer.msg("${message}")
            })
        });
    </script>
    <style>
        .login{
            height: 300px;
            width: 320px;
            padding: 20px;
            background-color: rgba(0, 0, 0, 0.5);
            border-radius: 40px;
            position: absolute;
            left: 50%;
            top: 50%;
            margin: -150px 0 0 -160px;
            z-index: 99;
        }
        .form_code{
            position: relative;
        }
        .form_code .code{
            position: absolute;
            right: 0;
            top: 1px;
            cursor: pointer;
        }
        .login h1{
            text-align: center;
            color: #fff;
            font-size: 24px;
            margin-bottom: 20px;
        }
        .login_btn{
            width: 100%;
        }
        body{
            background-color: #dddddd;
        }
    </style>
</head>
<body>

<div class="login layui-anim layui-anim-up">
    <h1>欢迎登录通用信息系统平台</h1><p></p>
    <form class="layui-form" action="${path}/login" method="post" id="login">
        <div class="layui-form-item">
            <input autofocus type="text" name="loginName" lay-verify="required" placeholder="请输入账号" autocomplete="off" value="" class="layui-input">
        </div>
        <div class="layui-form-item">
            <input type="password" name="password" lay-verify="required" placeholder="请输入密码" autocomplete="off" value="" class="layui-input">
        </div>
        <div class="layui-form-item form_code">
            <input class="layui-input" name="validateCode" placeholder="验证码" lay-verify="required" type="text" autocomplete="off">
            <div class="code"><img src="${path}/getVerifyCode" width="116" height="36" onclick="$(this).attr('src','${path}/getVerifyCode?'+new Date().getTime())"/></div>
        </div>

        <button class="layui-btn login_btn" lay-submit="" lay-filter="login">登录</button>
        <div class="footer" style="text-align: center;color: white">
            Copyright &copy; 2017
        </div>
    </form>
</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: gongxin
  Date: 2017/10/7
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/global.jsp"%>
<html>
<head>
    <title>修改密码</title>
    <meta name="decorator" content="default"/>
    <link href="${staticPath}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
    <script src="${staticPath}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        layui.use(['form', 'layer'], function() {
            layer = layui.layer
                ,form=layui.form;
            form.on('submit(query)',function (data) {
                return true;
            });

            $(function () {
                var flag=${not empty message}
                if(flag)
                    layer.msg("${message}")
            })
            $(document).ready(function() {
                $("#oldPassword").focus();
                $("#inputForm").validate({
                    rules: {
                    },
                    messages: {
                        confirmNewPassword: {equalTo: "输入与上面相同的密码"}
                    },
                    submitHandler: function(form){
                        form.submit();
                    },
                    errorContainer: "#message",
                    errorPlacement: function(error, element) {
                       layer.msg("输入有误，请先更正。");
                        if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                            error.appendTo(element.parent().parent());
                        } else {
                            error.insertAfter(element);
                        }
                    }
                });
            });
        });

    </script>
</head>
<body>
<form class="layui-form" id="inputForm" action="${path}/sys/user/modifypwd" method="post">
    <br>
    <div class="layui-form-item">
        <label class="layui-form-label">旧密码:</label>
        <div class="layui-input-block">
            <input class="layui-input required" lay-verify="required" id="oldPassword" name="oldPassword" type="password" value="" maxlength="50" minlength="3" style="width: 400px"/>
            <span class="help-inline" style="color: red">*</span>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">新密码:</label>
        <div class="layui-input-block">
            <input class="layui-input required" lay-verify="required" id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" style="width: 400px"/>
            <span class="help-inline" style="color: red">* </span>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">确认新密码:</label>
        <div class="layui-input-block">
            <input class="layui-input required" lay-verify="required" id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword" style="width: 400px"/>
            <span class="help-inline" style="color: red">*</span>
        </div>
    </div>
    <div class="layui-input-block">
        <button id="btnSubmit" class="layui-btn layui-btn-small" lay-submit="" lay-filter="save">保存</button>
    </div>
</form>
</body>
</html>
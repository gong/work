<%--
  Created by IntelliJ IDEA.
  User: gongxin
  Date: 2017/10/27
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/global.jsp"%>
<html>
<head>
    <title>菜单添加</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<form:form class="layui-form" modelAttribute="menu" id="addForm" action="${path}/sys/menu/add" method="post">
    <br>
    <form:input path="parentId" type="hidden" value="${param.id}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单名：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" lay-verify="required" path="name"  autocomplete="off"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单链接：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" path="url"  autocomplete="off"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否显示：</label>
        <div class="layui-input-block">
            <form:checkbox path="isShow" value="1" lay-skin="switch" lay-text="显示|隐藏"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">排序序号：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" lay-verify="required" path="sort"  autocomplete="off"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">添加权限字符串：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" path="permission"  autocomplete="off"/>
        </div>
    </div>
    <div class="layui-input-block">
        <button class="layui-btn layui-btn-small" lay-submit="" lay-filter="save">保存</button>
    </div>
    <br>
</form:form>
<script>
    layui.use(['form', 'layer'], function() {//需要放在body以内
        layer = layui.layer
            ,form=layui.form;

        $(function () {
            var flag=${not empty message}
            if(flag){
                layer.msg("${message}")
            }
        });
    });
</script>
</body>
</html>

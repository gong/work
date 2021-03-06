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
    <title>菜单修改</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<form:form class="layui-form" modelAttribute="menu" id="addForm" action="${path}/sys/menu/update" method="post">
    <br>
    <form:hidden path="menuId"/>
    <div class="layui-form-item">
        <label class="layui-form-label">修改菜单名：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" lay-verify="required" path="name"  value="${menu.name}" autocomplete="off"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">修改菜单链接：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" path="url"  value="${menu.url}" autocomplete="off"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否显示：</label>
        <div class="layui-input-block">
            <input type="checkbox" <c:if test="${menu.isShow eq 1}">checked</c:if> value="1" lay-text="显示|隐藏" lay-skin="switch" lay-filter="switchTest"/>
            <form:input path="isShow" type="hidden" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">修改排序序号：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" lay-verify="required" path="sort"  value="${menu.sort}" autocomplete="off"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">修改权限字符串：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" path="permission"  value="${menu.permission}" autocomplete="off"/>
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
        form.on('switch(switchTest)',function (data) {
            $("#isShow").val(data.elem.checked?1:0);
        });
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

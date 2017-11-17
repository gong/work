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
    <title>角色添加</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<form:form class="layui-form" modelAttribute="role" id="addForm" action="${path}/sys/role/add" method="post">
    <br>
    <div class="layui-form-item">
        <label class="layui-form-label">角色名：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" lay-verify="required" path="name"  autocomplete="off"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否可用：</label>
        <div class="layui-input-block">
            <form:checkbox path="useable" value="1" lay-skin="switch" lay-text="开启|关闭"/>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">角色描述：</label>
        <div class="layui-input-block">
            <form:textarea path="description" placeholder="请输入内容" class="layui-textarea"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色拥有的菜单：</label>
        <div class="layui-input-block">
            <c:forEach items="${fns:getMenuList()}" var="menu">
                <c:if test="${menu.menuId ne '1'}">
                    <input type="checkbox" name="menus" title="${menu.name}" value="${menu.menuId}"/>
                </c:if>
            </c:forEach>
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

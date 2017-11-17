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
<form:form class="layui-form" modelAttribute="role" id="addForm" action="${path}/sys/role/update" method="post">
    <br>
    <form:hidden path="roleId"/>
    <div class="layui-form-item">
        <label class="layui-form-label">角色名：</label>
        <div class="layui-input-block">
            <form:input class="layui-input" lay-verify="required" path="name" value="${role.name}" autocomplete="off"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">是否可用：</label>
        <div class="layui-input-block">
            <input type="checkbox" <c:if test="${role.useable eq 1}">checked</c:if> value="1" lay-text="开启|关闭" lay-skin="switch" lay-filter="switchTest"/>
            <form:input path="useable" type="hidden" />
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">角色描述：</label>
        <div class="layui-input-block">
            <form:textarea path="description" placeholder="请输入内容" value="${role.name}" class="layui-textarea"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色拥有的菜单：</label>
        <div class="layui-input-block">
            <c:forEach items="${fns:getMenuList()}" var="menu">
                <c:choose>
                    <c:when test="${menu.menuId ne '1' and fns:judge(menuStr,menu.name)}">
                        <input checked type="checkbox" name="menus" title="${menu.name}" value="${menu.menuId}"/>
                    </c:when>
                    <c:when test="${menu.menuId ne '1'}">
                        <input type="checkbox" name="menus" title="${menu.name}" value="${menu.menuId}"/>
                    </c:when>
                </c:choose>
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
        form.on('switch(switchTest)',function (data) {
             $("#useable").val(data.elem.checked?1:0);
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

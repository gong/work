<%--
  Created by IntelliJ IDEA.
  User: gongxin
  Date: 2017/10/21
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/global.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <title>菜单管理</title>
</head>
<body>
    <br>
<table id="contentTable" class="layui-table" lay-size="sm">
    <colgroup>
        <col width="150">
        <col width="50">
        <col width="50">
        <col width="250">
    </colgroup>
    <thead>
    <tr>
        <th>菜单名</th>
        <th>是否显示</th>
        <th>排序序号</th>
        <shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
<c:set var="menuList" value="${fns:getMenuList()}"/><c:set var="firstMenu" value="true"/><c:set var="firstId" value="1"/>
<c:forEach items="${menuList}" var="menu">
    <c:if test="${menu.parentId eq firstId }">
        <tr>
            <td style="font-size: 15px;font-weight: bold">${menu.name}</td>
            <td>
                <c:choose>
                    <c:when test="${menu.isShow==1}">
                        显示
                    </c:when>
                    <c:otherwise>
                        隐藏
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                ${menu.sort}
            </td>
            <shiro:hasPermission name="sys:menu:edit"><td>
                <a  class="layui-btn layui-btn-mini" href="${path}/sys/menu/update?id=${menu.menuId}" ><i class="layui-icon">&#xe642;修改</i></a>
                <a  class="layui-btn layui-btn-danger layui-btn-mini" href="${path}/sys/menu/delete?id=${menu.menuId}" onclick="return del(this)"><i class="layui-icon">&#xe640;删除</i></a>
                <a  class="layui-btn layui-btn-mini" href="${path}/sys/menu/add?id=${menu.menuId}" ><i class="layui-icon">&#xe61f;添加下级菜单</i></a>
            </td></shiro:hasPermission>
        </tr>
        <c:forEach items="${menuList}" var="menu2">
            <c:if test="${menu2.parentId eq menu.menuId}">
                <tr>
                <td style="padding-left: 40px;font-size: 13px;font-weight: bold">${menu2.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${menu2.isShow==1}">
                            显示
                        </c:when>
                        <c:otherwise>
                            隐藏
                        </c:otherwise>
                    </c:choose>
                </td>
                 <td>
                     ${menu2.sort}
                 </td>
                <shiro:hasPermission name="sys:menu:edit"><td>
                    <a  class="layui-btn layui-btn-mini" href="${path}/sys/menu/update?id=${menu2.menuId}" ><i class="layui-icon">&#xe642;修改</i></a>
                    <a  class="layui-btn layui-btn-danger layui-btn-mini" href="${path}/sys/menu/delete?id=${menu2.menuId}" onclick="return del(this)"><i class="layui-icon">&#xe640;删除</i></a>
                    <a  class="layui-btn layui-btn-mini" href="${path}/sys/menu/add?id=${menu2.menuId}" ><i class="layui-icon">&#xe61f;添加下级菜单</i></a>
                </td></shiro:hasPermission>
            </tr>
                <c:forEach items="${menuList}" var="menu3">
                    <c:if test="${menu3.parentId eq menu2.menuId}">
                        <tr>
                            <td style="padding-left: 80px;">${menu3.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${menu3.isShow==1}">
                                        显示
                                    </c:when>
                                    <c:otherwise>
                                        隐藏
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                ${menu3.sort}
                            </td>
                            <shiro:hasPermission name="sys:menu:edit"><td>
                                <a  class="layui-btn layui-btn-mini" href="${path}/sys/menu/update?id=${menu3.menuId}" ><i class="layui-icon">&#xe642;修改</i></a>
                                <a  class="layui-btn layui-btn-danger layui-btn-mini" href="${path}/sys/menu/delete?id=${menu3.menuId}" onclick="return del(this)"><i class="layui-icon">&#xe640;删除</i></a>
                            </td></shiro:hasPermission>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:if>
        </c:forEach>
    </c:if>
</c:forEach>
    </tbody>
</table>

<script>
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
    });
    function del(obj) {
        layer.confirm("确定删除菜单？",{icon:3, title:'提示'},function(index){
            layer.close(index);
            window.location.href=obj.href;
        });
        return false;
    }
</script>
</body>
</html>


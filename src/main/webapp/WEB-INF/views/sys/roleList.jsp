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
    <title>角色管理</title>
    <link href="${staticPath}/css/mricode.pagination.css" rel="stylesheet" />
    <script src="${staticPath}/js/mricode.pagination.js"></script>

</head>
<body>

<form:form modelAttribute="role" id="searchForm" action="${path}/sys/role/list" method="post">
    <input id="pageNo" name="pageNo" type="hidden" />
    <input id="pageSize" name="pageSize" type="hidden" />

    <br>
    <div class="layui-inline">
        <label class="layui-form-label">角色名：</label>
        <div class="layui-input-inline">
            <form:input path="name" class="layui-input" autocomplete="off"/>
        </div>
    </div>
    <button class="layui-btn layui-btn-small" lay-submit="" lay-filter="query">查询</button>
    <br>
</form:form>
<table id="contentTable" class="layui-table" lay-size="sm">
    <colgroup>
        <col width="150">
        <col width="50">
        <col width="200">
        <col width="250">
    </colgroup>
    <thead>
    <tr>
        <th>角色名</th>
        <th>是否可用</th>
        <th>描述</th>
        <shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.records}" var="role">
        <tr>
            <td>${role.name}</td>
            <td>
                <c:choose>
                    <c:when test="${role.useable==1}">
                        可用
                    </c:when>
                    <c:otherwise>
                        不可用
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${role.description}</td>
            <shiro:hasPermission name="sys:role:edit"><td>
                <a  class="layui-btn layui-btn-mini" href="${path}/sys/role/update?id=${role.roleId}" ><i class="layui-icon">&#xe642;修改</i></a>
                <a  class="layui-btn layui-btn-danger layui-btn-mini" href="${path}/sys/role/delete?id=${role.roleId}" onclick="return del(this)"><i class="layui-icon">&#xe640;删除</i></a>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div id="page" class="m-pagination"></div>
<script>
    $("#page").pagination({
        pageIndex: "${page.current-1}",
        pageSize: "${page.size}",
        total: "${page.total}",
        showInfo: true,
        showJump: true,
        showPageSizes: true,
        pageElementSort: ['$page', '$size', '$jump', '$info'],
        jumpBtnText:'跳转',
        noInfoText:'0个数据',
        pageSizeItems:[5,10,15,20],
        infoFormat:'数据总数{total}当前数据范围:{start}~{end}'
    });
    $("#page").on("pageClicked", function (event, data) {
        $("#pageNo").val(data.pageIndex+1);
        $("#pageSize").val(data.pageSize);
        $("#searchForm").submit();
    }).on('jumpClicked', function (event, data) {
        $("#pageNo").val(data.pageIndex+1);
        $("#pageSize").val(data.pageSize);
        $("#searchForm").submit();
    }).on('pageSizeChanged', function (event, data) {
        $("#pageNo").val(data.pageIndex+1);
        $("#pageSize").val(data.pageSize);
        $("#searchForm").submit();
    });
    $('.m-pagination-size  select').css('width','70px');
    $('.m-pagination-group input').css('cssText','width:50px!important;height:34px!important');
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
        layer.confirm("确定删除角色？",{icon:3, title:'提示'},function(index){
            layer.close(index);
            window.location.href=obj.href;
        });
        return false;
    }
</script>
</body>
</html>


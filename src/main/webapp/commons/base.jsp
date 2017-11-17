<%--
  Created by IntelliJ IDEA.
  User: gongxin
  Date: 2017/10/23
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/global.jsp" %>
<% response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","No-cache");
    response.setDateHeader("Expires", -1);
    response.setHeader("Cache-Control", "No-store"); %>

<link rel="shortcut icon" href="${staticPath}/style/images/favicon.ico" />
<!--layui-->
<link href="${staticPath}/layui/css/layui.css" rel="stylesheet">
<script src="${staticPath}/layui/layui.js"></script>
<script src="${staticPath}/layui/lay/modules/element.js"></script>
<script src="${staticPath}/js/jquery.min.js"></script>


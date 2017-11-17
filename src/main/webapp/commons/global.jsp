<%--标签 --%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" isELIgnored="false" session="false" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %><%--扩展shiro标签--%>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %><%--自定义函数--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<% response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","No-cache");
    response.setDateHeader("Expires", -1);
    response.setHeader("Cache-Control", "No-store"); %>
<%--ctxPath--%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%--项目路径 --%>
<c:set var="path" value="${ctxPath}" />
<%--静态文件目录 --%>
<c:set var="staticPath" value="${ctxPath}/static" />
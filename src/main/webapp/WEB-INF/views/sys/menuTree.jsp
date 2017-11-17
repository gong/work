<%--
  Created by IntelliJ IDEA.
  User: gongxin
  Date: 2017/10/7
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/global.jsp"%>
<ul class="layui-nav layui-nav-tree" id="menu-${param.parentId}">
    <c:set var="menuList" value="${fns:getMenuList()}"/><c:set var="firstMenu" value="true"/>
    <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
        <c:if test="${menu.parentId eq (not empty param.parentId ? param.parentId:1)&&menu.isShow eq '1'}">
                <li class="layui-nav-item ${not empty firstMenu && firstMenu ? 'layui-nav-itemed' : ''}">
                    <a class="" data-toggle="collapse" data-parent="#menu-${param.parentId}" data-href="#collapse-${menu.menuId}" href="#collapse-${menu.menuId}" title="${menu.name}">${menu.name}</a>
                    <dl class="layui-nav-child" style=" background-color: rgba(255, 255, 255, 0.25)!important;">
                    <c:forEach items="${menuList}" var="menu2">
                   <c:if test="${menu2.parentId eq menu.menuId &&menu2.isShow eq '1'}">
                    <dd class="${not empty firstMenu && firstMenu ? 'layui-this' : ''}">
                        <a data-href=".menu3-${menu2.menuId}" href="${fn:indexOf(menu2.url, '://') eq -1 ? path : ''}/${not empty menu2.url ? menu2.url : '404'}" target="${not empty menu2.target ? menu2.target : 'mainFrame'}" class="a-self">${menu2.name}</a>
                    </dd>
                    <c:set var="firstMenu" value="false"/></c:if>
                    </c:forEach>
                    </dl>
                </li>
        </c:if>
    </c:forEach>
</ul>
<script>
    $('#menu-${param.parentId} .layui-nav-child .layui-this a')[0].click();//渲染后模拟点击打开菜单
</script>


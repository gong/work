<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/global.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        layui.use('element', function(){
            var element = layui.element;
            $(document).ready(function() {
                // 绑定菜单单击事件
                $("#menu a.menu").click(function(){
                    $("#menu li").removeClass("layui-nav-itemed");
                    $(this).parent('.layui-nav-item').addClass("layui-nav-itemed");
                    // 显示二级菜单
                    var menuId = "#menu-" + $(this).attr("data-id");
                    if ($(menuId).length > 0){<!--判断对应id选择器是否存在-->
                        $("#left .layui-nav-tree").hide();//隐藏所有的一级菜单的左侧菜单
                        $(menuId).show();//显示被点击的一级菜单的左侧菜单
                        // 默认选中第一个菜单
                        $(menuId + " .layui-nav-itemed .layui-nav-child dd a")[0].click();//模拟点击要使用元素
                    }else{
                        // 获取二级菜单数据
                        $.get($(this).attr("data-href"), function(data){
                            if (data.indexOf("id=\"login\"") != -1){
                                alert('未登录或登录超时。请重新登录，谢谢！');
                                top.location = "${path}";
                                return false;
                            }
                            $("#left .layui-nav-tree").hide();//隐藏所有的一级菜单的左侧菜单
                            $("#left").append(data);//将第一次加载的一级菜单页面加载进left区域
                            element.init();
                        });
                    }
                    return false;
                });
                // 初始化点击第一个一级菜单
                $("#menu a.menu:first").click();
            });

        });
    </script>
    <style>
        .a-self{
            padding-left: 40px!important;
            font-size: 10px;
        }
    </style>
</head>
<body class="layui-layout-body">
<div id="main" class="layui-layout layui-layout-admin">
    <div id="header"class="layui-header">
        <div class="layui-logo">通用信息系统权限管理</div>
        <ul id="menu" class="layui-nav layui-layout-left">
            <c:set var="firstMenu" value="true"/>
            <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                <c:if test="${menu.parentId eq '1'&&menu.isShow eq '1'}">
                    <li class="layui-nav-item ${not empty firstMenu && firstMenu ? ' layui-nav-itemed' : ''}">
                        <c:if test="${empty menu.url}">
                            <a class="menu" href="javascript:void(0)" data-href="${path}/sys/menu/tree?parentId=${menu.menuId}" data-id="${menu.menuId}">${menu.name}</a>
                        </c:if>
                        <c:if test="${not empty menu.url}">
                            <a class="menu" href="${fn:indexOf(menu.url, '://') eq -1 ? path : ''}${menu.url}" data-id="${menu.menuId}" target="mainFrame">${menu.name}</a>
                        </c:if>
                    </li>
                    <c:if test="${firstMenu}">
                        <c:set var="firstMenuId" value="${menu.menuId}"/>
                    </c:if>
                    <c:set var="firstMenu" value="false"/>
                </c:if>
            </c:forEach>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li id="userInfo" class="layui-nav-item">
                <a href="javascript:;" title="个人信息">
                    您好, ${fns:getUser().name}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="${path}/sys/user/modifypwd" target="mainFrame">修改密码</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="${path}/logout" title="退出登录">退出</a></li>
        </ul>
    </div>
    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <div id="left">
                <%--左侧菜单--%>
            </div>
        </div>
    </div>
    <div id="right" class="layui-body">
        <iframe id="mainFrame" name="mainFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="900"></iframe>
    </div>
    <div class="layui-footer">
            <!-- 底部固定区域 -->
            Copyright &copy; 2017
    </div>
</div>
</body>
</html>
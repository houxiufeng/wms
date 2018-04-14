<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>WMS</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <jsp:include page="/WEB-INF/views/common/commonCSS.jsp" flush="true"/>
  </head>

  <body>

    <header class="dark_grey"> <!-- Header start -->
        <a href="#" class="logo_image"><span class="hidden-480">WMS</span></a>
        <ul class="header_actions">
            <li class="dropdown"><a href="javascript:void(0);">${CURRENT_USER.name}</a>
                <%--<ul>--%>
                    <%--<li><a href="#"><i class="icon-user"></i>个人中心</a></li>--%>
                <%--</ul>--%>
            </li>
            <li><a id="logout" href="javascript:logout()"><i class="icon-signout"></i> <span class="hidden-768 hidden-480">Logout</span></a></li>
        </ul>
    </header>

    <!-- menu -->
    <div id="main_navigation" class="dark_navigation"> <!-- Main navigation start -->
        <div class="inner_navigation">
            <ul class="main">
                <c:forEach items="${permissionTrees}" var="permissionTree">
                    <li>
                        <a class="treeNode" href="javascript:void(0)">
                            <i class="icon-folder-close"></i>${permissionTree.treeNode.name}
                        </a>
                        <c:if test="${permissionTree.subNodes != null and permissionTree.subNodes.size() > 0}">
                            <ul class="sub_main">
                                <c:forEach items="${permissionTree.subNodes}" var="subNode">
                                    <li style="background-color: #7b7a7afa">
                                        <a class="treeNode" style="color: #efe9e9" href="javascript:App.goToPage('${ctx}${subNode.url}')" >
                                            <i class="icon-caret-right"></i> ${subNode.name}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div> 
    <!-- end menu --> 
    <div id="content">
	    <div id="main" class="row-fluid">
        </div>
    </div>
    <input type="hidden" id="point_x">
    <input type="hidden" id="point_y">

    <jsp:include page="/WEB-INF/views/common/commonJS.jsp" flush="true"/>
  </body>
</html>

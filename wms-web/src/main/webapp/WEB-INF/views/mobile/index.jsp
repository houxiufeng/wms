<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>allen</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <jsp:include page="/WEB-INF/views/common/commonCSS.jsp" flush="true"/>
  </head>

  <body>

    <header class="blue"> <!-- Header start -->
        <a href="#" class="logo_image"><span class="hidden-480">WMS ZOO</span></a>
        <ul class="header_actions">
            <li class="dropdown"><a href="#"><img src="${ctx}/flatpoint/demo/avatar_06.png" alt="User image" class="avatar"> ${CURRENT_USER.name}</a>
            </li>
            <li><a id="logout" href="javascript:logout()"><i class="icon-signout"></i> <span class="hidden-768 hidden-480">Logout</span></a></li>
        </ul>
    </header>
    <div id="content">
	    <div id="main" class="row-fluid">
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/common/commonJS.jsp" flush="true"/>
    <script>
        if (currentUserInfo.roleCode == 'engineer') {
            App.goToPage(appCtx + "/mobile/vendor");
        }
    </script>

  </body>
</html>

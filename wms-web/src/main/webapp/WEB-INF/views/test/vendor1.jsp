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
            <li class="dropdown"><a href="#"><img src="${ctx}/flatpoint/demo/avatar_06.png" alt="User image" class="avatar"> ${CURRENT_USER.name} </a>
            </li>
            <li><a id="logout" href="javascript:logout()"><i class="icon-signout"></i> <span class="hidden-768 hidden-480">Logout</span></a></li>
        </ul>
    </header>
    <div id="content">
	    <div id="main" class="row-fluid">
            <div style="margin-top: 40px; padding: 10px;">
                <form id="orderForm">
                    <div class="form_row">
                        <div class="field">
                            <span style="font-size: 14px;font-weight: 600">李先生维修公司:你好刘德华</span>
                        </div>
                    </div>
                    <div class="form_row">
                        <div class="field">
                            <img src="${ctx}/flatpoint/demo/avatar_04.png" style="width: 60%;">
                        </div>
                    </div>
                    <div class="form_row">
                        <div class="col-xs-4">
                            <label class="field_name align_right" style="width: 50%">接单中:</label>
                            <div class="field" style="margin-left: 55%; margin-top: 5px;">
                                0
                            </div>
                        </div>
                        <div class="col-xs-4">
                            <label class="field_name align_right" style="width: 50%">已完结:</label>
                            <div class="field" style="margin-left: 55%; margin-top: 5px;">
                                1
                            </div>
                        </div>
                    </div>
                    <div class="form_row">
                        <div class="col-xs-4">
                            <label class="field_name align_right" style="width: 50%">检修中:</label>
                            <div class="field" style="margin-left: 55%; margin-top: 5px;">
                                0
                            </div>
                        </div>
                        <div class="col-xs-4">
                            <label class="field_name align_right" style="width: 50%">已拒绝:</label>
                            <div class="field" style="margin-left: 55%; margin-top: 5px;">
                                2
                            </div>
                        </div>
                    </div>
                    <div class="form_row">
                        <div class="col-xs-4">
                            <label class="field_name align_right" style="width: 50%">维修中:</label>
                            <div class="field" style="margin-left: 55%; margin-top: 5px;">
                                1
                            </div>
                        </div>
                    </div>


                    <div class="form_row">
                        <div class="col-xs-11">
                            <label class="field_name align_right" style="width: 20%">我的评分:</label>
                            <div class="field" style="margin-top: 5px;margin-left: 25%;">
                                <span style="color: green">好评</span>:1
                                <span style="color: darkslategrey;">中评</span>:0
                                <span style="color:red">差评</span>:2
                            </div>
                        </div>
                    </div>

                    <div class="form_row">
                        <div class="field">
                            <a href="javascript:;" class="btn light_blue">首页</a>
                            <a href="javascript:;" class="btn light_blue">接单中</a>
                            <a href="javascript:;" class="btn light_blue">检查中</a>
                            <a href="javascript:;" class="btn light_blue">维修中</a>
                        </div>
                    </div>

                </form>
            </div>

        </div>
    </div>

    <jsp:include page="/WEB-INF/views/common/commonJS.jsp" flush="true"/>
  </body>
</html>

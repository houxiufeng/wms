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
            <div style="margin-top: 40px; padding: 10px;">
                <form id="orderForm">
                    <div class="form_row">
                        <div class="field">
                            <span style="font-size: 14px;font-weight: 600">李先生维修公司:你好刘德华</span>
                        </div>
                    </div>
                    <div class="form_row">
                        <label class="field_name align_right"><span style="color: red">*</span>产品名称:</label>
                        <div class="field">
                            <select id="productId" name="productId" class="span10">
                                <option value="0">kent li</option>
                                <option value="1">jack chen</option>
                                <option value="2">bruce lee</option>
                            </select>
                        </div>
                    </div>
                    <div class="form_row">
                        <label class="field_name align_right"><span style="color: red">*</span>产品型号:</label>
                        <div class="field">
                            <select id="modelId" name="modelId" class="span10">
                                <option value="0">型号1</option>
                                <option value="1">型号2</option>
                                <option value="2">型号3</option>
                            </select>
                        </div>
                    </div>

                    <div class="form_row">
                        <label class="field_name align_right"><span style="color: red">*</span>问题描述:</label>
                        <div class="field">
                            <input name="name" type="text" maxlength="30" class="span10">
                        </div>
                    </div>

                    <div class="form_row">
                        <label class="field_name align_right"><span style="color: red">*</span>问题描述:</label>
                        <div class="field">
                            <textarea name="remark" style="resize:none;height: 100px;" class="span10"></textarea>
                        </div>
                    </div>

                    <div class="form_row">
                        <div class="field">
                            <a href="javascript:;" class="btn red"><i class="icon-save"></i></a>
                            <a href="javascript:;" class="btn dark_green"><i class="icon-reply"></i></a>
                        </div>
                    </div>

                    <div class="form_row" >
                        <div>
                            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor');" class="btn dark_green btn-large" style="font-size: 16px;">首页</a>
                            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=1');" class="btn btn-large" style="font-size: 16px;">检查中</a>
                            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=2');" class="btn btn-large" style="font-size: 16px;">维修中</a>
                            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=4');" class="btn btn-large" style="font-size: 16px;">已完成</a>
                        </div>
                    </div>

                </form>
            </div>

        </div>
    </div>

    <jsp:include page="/WEB-INF/views/common/commonJS.jsp" flush="true"/>
  </body>
</html>

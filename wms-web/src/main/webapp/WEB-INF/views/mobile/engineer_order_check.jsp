<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>WMS</title>
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
          <li class="dropdown"><a href="javascript:void(0);">${CURRENT_USER.name}</a>
          </li>
          <li><a id="logout" href="javascript:logout();"><i class="icon-signout"></i> <span class="hidden-768 hidden-480">Logout</span></a></li>
      </ul>
  </header>
    <div id="content">
	    <div id="main" class="row-fluid">
            <div class="span12" style="margin-top: 45px;">
                <div class="well blue">
                    <div class="well-header" style="min-height: 40px">
                        <a href="javascript:void(0)" style="color: white; display: inline-block; padding: 10px; font-size: 16px;" onclick="App.goToPage(appCtx + '/mobile/engineer/order/list', {'status':1});">Back</a>
                    </div>
                    <div class="well-content price-table" style="min-height: 80vh; font-size: 15px;">
                        <div style="min-height: 70vh;">
                            <ul>
                                <li style="line-height:40px;">Product name: ${branchProduct.product.name}</li>
                                <li style="line-height:40px;">Product model: ${branchProduct.product.model}</li>
                                <li style="line-height:40px;">Computer name: ${branchProduct.computerName}</li>
                                <li style="line-height:40px;">Roles: ${branchProduct.roles}</li>
                                <li style="line-height:40px;">Operating System: ${branchProduct.os}</li>
                                <li style="line-height:40px;">Processor: ${branchProduct.product.processor}</li>
                                <li style="line-height:40px;">Memory: ${branchProduct.product.memory}</li>
                                <li style="line-height:40px;">Hard drive: ${branchProduct.product.hardDrive}</li>
                                <li style="line-height:40px;">Position: ${branchProduct.position}</li>
                                <li style="line-height:40px;">Confirm question:
                                    <select id="type" name="type">
                                        <c:forEach items="${types}" var="item">
                                            <option value="${item.id}" <c:if test="${item.id == order.type}">selected</c:if>>${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li style="line-height:40px;">Confirm description:
                                    <textarea id="description" style="resize:none;height: 50px; width: 90%;" maxlength="200">${order.description}</textarea>
                                </li>
                                <c:if test="${order == null}">
                                    <li style="line-height:40px; font-size: 16px;font-weight: 600; color: #bf3f20">Sorry sir, this is not the product you will fix, please make it sure!</li>
                                </c:if>
                            </ul>
                        </div>
                        <c:if test="${order != null}">
                            <input type="hidden" value="${order.id}" id="orderId">
                            <div style="min-height: 10vh; padding: 18px;">
                                <a href="javascript:void(0)" class="btn btn-xlarge btn-block blue" onclick="javascript:Order.mobileChecked();">OK</a>
                            </div>
                        </c:if>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/app/order.js"></script>
    <jsp:include page="/WEB-INF/views/common/commonJS.jsp" flush="true"/>
  </body>
</html>

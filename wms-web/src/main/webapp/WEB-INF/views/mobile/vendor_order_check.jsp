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
            <li class="dropdown"><a href="javascript:void(0);">${CURRENT_USER.name}</a>
            </li>
            <li><a id="logout" href="javascript:logout();"><i class="icon-signout"></i> <span class="hidden-768 hidden-480">Logout</span></a></li>
        </ul>
    </header>
    <div id="content">
	    <div id="main" class="row-fluid">
            <div style="margin-top: 40px; padding: 10px;">
                <input type="hidden" value="${order.id}" id="orderId">
                <div class="form_row">
                    <label class="field_name align_right" style="width: 30%;">总/分店名称:</label>
                    <div class="field" style="margin-left: 31%; margin-top: 5px;">
                        ${branchProduct.branch.name}
                    </div>
                </div>
                <div class="form_row">
                    <label class="field_name align_right" style="width: 30%;">总分店地址:</label>
                    <div class="field" style="margin-left: 31%; margin-top: 5px; ">
                        ${branchProduct.branch.address}
                    </div>
                </div>
                <div class="form_row">
                    <label class="field_name align_right" style="width: 30%;">维修产品名称:</label>
                    <div class="field" style="margin-left: 31%; margin-top: 5px;">
                        ${branchProduct.product.name}
                    </div>
                </div>
                <div class="form_row">
                    <label class="field_name align_right" style="width: 30%;">维修产品型号:</label>
                    <div class="field" style="margin-left: 31%; margin-top: 5px;">
                        ${branchProduct.product.model}
                    </div>
                </div>
                <div class="form_row">
                    <label class="field_name align_right" style="width: 30%;">维修产品位置:</label>
                    <div class="field" style="margin-left: 31%; margin-top: 5px;">
                        <span>${branchProduct.position}</span>
                        <a href="javascript:Order.showPOI('${branchProduct.poi}');" class="btn dark_green btn-small" style="margin-left: 2px;">POI</a>
                    </div>
                </div>
                <div class="form_row">
                    <label class="field_name align_right" style="width: 30%;">客户问题类型:</label>
                    <div class="field" style="margin-left: 31%; margin-top: 5px;">
                        ${order.typeName}
                    </div>
                </div>
                <div class="form_row">
                    <label class="field_name align_right" style="width: 30%;">客户问题描述:</label>
                    <div class="field" style="margin-left: 31%; margin-top: 5px;">
                        ${order.description}
                    </div>
                </div>
                <c:if test="${order == null}">
                    <div class="form_row" style="text-align: center;">
                        <span style="font-size: 16px;font-weight: 600">不对起先生，这不是你需要维修的产品，请再确认</span>
                    </div>
                    <%--<div class="form_row">--%>
                        <%--<div class="field">--%>
                            <%--<a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=1');" class="btn dark_green btn-large" style="font-size: 16px;">返回</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                </c:if>
                <c:if test="${order != null}">
                    <div class="form_row">
                        <label class="field_name align_right" style="width: 30%;">确认问题:</label>
                        <div class="field" style="margin-left: 31%;">
                            <select id="type" name="type">
                                <c:forEach items="${types}" var="item">
                                    <option value="${item.id}" <c:if test="${item.id == order.type}">selected</c:if>>${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form_row">
                        <label class="field_name align_right" style="width: 30%;">确认描述:</label>
                        <div class="field" style="margin-left: 31%;">
                            <textarea id="description" style="resize:none;height: 50px; width: 90%;" maxlength="200">${order.description}</textarea>
                        </div>
                    </div>
                    <div class="form_row">
                        <div class="field">
                            <a href="javascript:Order.mobileChecked();" class="btn dark_green btn-large" style="font-size: 16px;">确认</a>
                            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/checkList');" class="btn  btn-large" style="font-size: 16px;">返回</a>
                        </div>
                    </div>

                </c:if>

            </div>
        </div>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/app/order.js"></script>
    <jsp:include page="/WEB-INF/views/common/commonJS.jsp" flush="true"/>
  </body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="margin-top: 40px; padding: 10px;">
    <div class="form_row" style="text-align: center;">
        <span style="font-size: 14px;font-weight: 600">${CURRENT_USER.organizationName}:很高兴为你服务</span>
        <input type="hidden" id="branchId" value="${branchId}">
    </div>

    <div class="dataTables_wrapper">
        <table id="orderTable" class="table table-striped table-bordered table-hover datatable"></table>
    </div>

    <div class="form_row">
        <div class="field">
            <a href="javascript:App.goToPage(appCtx + '/mobile/order/list?flag=1');" class="btn btn-large <c:if test='${flag == 1}'>dark_green</c:if>" style="font-size: 16px;">Current Orders</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/order/list?flag=2');" class="btn btn-large <c:if test='${flag == 2}'>dark_green</c:if>" style="font-size: 16px;">History Orders</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/order.js"></script>
<script>
    Order.getMobileTableData_customer(${flag});
</script>
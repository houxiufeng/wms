<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="margin-top: 40px; padding: 10px;">
    <div class="form_row" style="text-align: center;">
        <span style="font-size: 16px;font-weight: 600">你好,${vendor.name}</span>
        <input type="hidden" id="vendorId" value="${vendor.id}">
    </div>

    <div class="dataTables_wrapper">
        <table id="orderTable" class="table table-striped table-bordered table-hover datatable"></table>
    </div>

    <div class="form_row">
        <div class="field">
            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor');" class="btn btn-large" style="font-size: 16px;">首页</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=1');" class="btn btn-large <c:if test='${status == 1}'>light_blue</c:if>" style="font-size: 16px;">检查中</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=2');" class="btn btn-large <c:if test='${status == 2}'>light_blue</c:if>" style="font-size: 16px;">维修中</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=4');" class="btn btn-large <c:if test='${status == 4}'>light_blue</c:if>" style="font-size: 16px;">已完成</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/order.js"></script>
<script>
    Order.getMobileTableData(${status});
</script>
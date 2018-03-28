<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .form_row {
        margin-bottom: 10px;
    }
    .content1 {
        min-height: calc(100vh - 110px);
    }
    .footer1 {
        height: 50px;
    }
</style>
<div class="content1" style="margin-top: 40px; padding: 10px;">
    <div class="form_row" style="text-align: center;">
        <span style="font-size: 16px;font-weight: 600">你好,${vendor.name}</span>
        <input type="hidden" id="vendorId" value="${vendor.id}">
    </div>

    <div class="dataTables_wrapper">
        <table id="orderTable" class="table table-striped table-bordered table-hover datatable"></table>
    </div>
</div>
<div class="footer1">
    <div style="margin-left: 10px;padding: 10px;">
        <a href="javascript:App.goToPage(appCtx + '/mobile/vendor');" class="btn btn-large" style="font-size: 16px;"><i class="icon-home"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=1');" class="btn btn-large <c:if test='${status == 1}'>dark_green</c:if>" style="font-size: 16px;"><i class="icon-zoom-in"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=2');" class="btn btn-large <c:if test='${status == 2}'>dark_green</c:if>" style="font-size: 16px;"><i class="icon-legal"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=4');" class="btn btn-large <c:if test='${status == 4}'>dark_green</c:if>" style="font-size: 16px;"><i class="icon-check"></i></a>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/order.js"></script>
<script>
    Order.getMobileTableData(${status});
</script>
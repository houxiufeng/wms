<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .form_row {
        margin-bottom: 25px;
    }
    .content {
        min-height: 70vh;
    }
    .footer {
        height: 20vh;
    }
</style>

<div class="content" style="margin-top: 40px; padding: 10px; font-size: 20px; margin-left: 10px;text-align: center;">
    <div class="form_row" style="text-align: center;">
        <span style="font-weight: 600">${CURRENT_USER.organizationName}:happy to serve you!</span>
    </div>
    <div class="form_row" style="text-align: left">
        <input id="startTime" name="startTime" type="text" class="datetimepicker" placeholder="From" style="width:100px;"> --
        <input id="endTime" name="endTime" type="text" class="datetimepicker" placeholder="To" style="width:100px;">
        <a href="javascript:Order.refreshMobileOrderSum();" class="btn blue">Search</a>
    </div>
    <input type="hidden" id="branchId" value="${branch.id}">

    <div class="dataTables_wrapper">
        <table id="orderSumTable" class="table table-striped table-bordered table-hover datatable" style="font-size: 14px;"></table>
    </div>

</div>
<div class="footer">
    <div style="margin-left: 10px;">
        <a href="javascript:App.goToPage(appCtx + '/mobile/customer');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Home <i class="icon-home"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/customer/report');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Report <i class="icon-table"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/customer/me');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Me <i class="icon-user"></i></a>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/order.js"></script>
<script>
    jQuery(".datetimepicker").datetimepicker({
        format:"yyyy-mm-dd",
        autoclose: true,
        pickTime: false,
        minView: '2',
        pickerPosition: "bottom-left",
        todayBtn: true
    });
    Order.queryMobileCustomerOrderSum();
    jQuery("#orderSumTable_paginate").css("width", "100%");
    jQuery("#orderSumTable_next").css({"float":"right","margin-right":"30px"});
</script>
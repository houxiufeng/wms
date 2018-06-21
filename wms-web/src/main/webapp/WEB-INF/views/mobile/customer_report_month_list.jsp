<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="span12" style="margin-top: 45px;">
    <div class="well blue">
        <div class="well-header">
            <h5 onclick="App.goToPage(appCtx+'/mobile/customer/report/sum');">Back</h5>
            <h5 style="margin-left: 30%;">${fn:substring(monthBegin, 0,7)}</h5>
        </div>

        <div class="well-content">
            <input id="branchId" type="hidden" value="${branchId}">
            <input id="monthBegin" type="hidden" value="${monthBegin}">
            <input id="monthEnd" type="hidden" value="${monthEnd}">
            <div class="navbar-inner">
                <ul class="nav nav-tabs">
                    <li class="active" data-status="1"><a href="#right-tab0" data-toggle="tab">Checking order</a></li>
                    <li data-status="2"><a href="#right-tab1" data-toggle="tab">Fixing order</a></li>
                    <li data-status="4"><a href="#right-tab2" data-toggle="tab">Complete order</a></li>
                </ul>
            </div>
            <div class="tab-content">
                <div class="tab-pane active" id="right-tab0">
                    <table id="m_orderTable1" class="table table-striped table-bordered table-hover datatable"></table>
                </div>
                <div class="tab-pane" id="right-tab1">
                    <table id="m_orderTable2" class="table table-striped table-bordered table-hover datatable"></table>
                </div>
                <div class="tab-pane" id="right-tab2">
                    <table id="m_orderTable4" class="table table-striped table-bordered table-hover datatable"></table>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/order.js"></script>
<script>
    Order.getCustomerMonthOrders(1);
    jQuery("#m_orderTable1_paginate").css("width", "100%");
    jQuery("#m_orderTable1_previous").css({"font-size":"16px"});
    jQuery("#m_orderTable1_next").css({"float":"right","margin-right":"30px","font-size":"16px"});

    jQuery("ul.nav li").on("click",function(){
        var status = jQuery(this).data("status");
        Order.getCustomerMonthOrders(status);
        jQuery("#m_orderTable" + status + "_paginate").css("width", "100%");
        jQuery("#m_orderTable" + status + "_previous").css({"font-size":"16px"});
        jQuery("#m_orderTable" + status + "_next").css({"float":"right","margin-right":"30px","font-size":"16px"});
    });
</script>
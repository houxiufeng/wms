<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <div class="span12">
    <div class="well light_gray">
        <div class="top_bar">
            <ul class="breadcrumb">
                <li><a href="javascript:void(0);" style="color: #037dc5;font-size: 15px;">订单中心</a> <span class="divider">/</span></li>
                <li class="active"><a href="javascript:void(0)" style="font-weight: 600;font-size: 13px;color: #037dc5;">订单管理</a></li>
            </ul>
        </div>

        <div class="well-content">

            <!-- start -->
            <div class="table_options top_options">
                <div>
                    <span class="inline">订单号:</span>
                    <input id="orderNo" name="orderNo" type="text" style="width:130px;">
                    <span class="inline">起始日期:</span>
                    <input id="startTime" name="startTime" type="text" class="datetimepicker" style="width:130px;" readonly>
                    <span class="inline">结束日期:</span>
                    <input id="endTime" name="endTime" type="text" class="datetimepicker" style="width:130px;" readonly>
                    <a href="javascript:Order.queryList()" class="dark_green btn" style="margin-left:10px;">查询</a>
                    <a href="javascript:App.goToPage('order/add')" class="dark_green btn">下单</a>
                </div>
            </div>
            <!-- end   -->

            <div class="navbar-inner">
                <ul class="nav nav-tabs">
                    <li class="active" data-status="0"><a href="#right-tab0" data-toggle="tab">派单中</a></li>
                    <li data-status="1"><a href="#right-tab1" data-toggle="tab">检查中</a></li>
                    <li data-status="2"><a href="#right-tab2" data-toggle="tab">维修中</a></li>
                    <%--<li><a href="#right-tab3" data-toggle="tab">审核中</a></li>--%>
                    <li data-status="4"><a href="#right-tab4" data-toggle="tab">已完结</a></li>
                    <li data-status="5"><a href="#right-tab5" data-toggle="tab">已取消</a></li>
                </ul>
            </div>

            <div class="tab-content">
                <div class="tab-pane active" id="right-tab0">
                    <table id="orderTable0" class="table table-striped table-bordered table-hover datatable"></table>
                </div>
                <div class="tab-pane" id="right-tab1">
                    <table id="orderTable1" class="table table-striped table-bordered table-hover datatable"></table>
                </div>
                <div class="tab-pane" id="right-tab2">
                    <table id="orderTable2" class="table table-striped table-bordered table-hover datatable"></table>
                </div>
                <%--<div class="tab-pane" id="right-tab3">--%>
                    <%--<table id="orderTable3" class="table table-striped table-bordered table-hover datatable"></table>--%>
                <%--</div>--%>
                <div class="tab-pane" id="right-tab4">
                    <table id="orderTable4" class="table table-striped table-bordered table-hover datatable"></table>
                </div>
                <div class="tab-pane" id="right-tab5">
                    <table id="orderTable5" class="table table-striped table-bordered table-hover datatable"></table>
                </div>
            </div>

        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/order.js"></script>
<script>
    Order.getTableData(0);
    jQuery("ul.nav li").on("click",function(){
        Order.getTableData(jQuery(this).data("status"));
    });
    jQuery(".datetimepicker").datetimepicker({
        format:"yyyy-mm-dd",
        autoclose: true,
        pickTime: false,
        minView: '2',
        todayBtn: true
    });
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <div class="span12">
    <div class="well light_gray">
        <div class="top_bar">
            <ul class="breadcrumb">
                <li><a href="javascript:void(0);">供应商中心</a> <span class="divider">/</span></li>
                <li class="active"><a href="javascript:void(0)">供应商管理</a></li>
            </ul>
        </div>

        <div class="well-content">
          
          <!-- start -->
          <div class="table_options top_options">
              <div>
                  <span class="inline">供应商名称:</span>
                  <input id="name" name="name" type="text" style="width:130px;">
                  <a href="javascript:Vendor.queryList()" class="blue btn" style="margin-left:10px;">查询</a>
                  <a href="javascript:App.goToPage('vendor/add')" class="blue btn">添加</a>
              </div>
          </div>
          <!-- end   -->
          
          <div class="dataTables_wrapper">
            <table id="vendorTable" class="table table-striped table-bordered table-hover datatable"></table>
          </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/vendor.js"></script>
<script>
    Vendor.getTableData();
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <div class="span12">
    <div class="well light_gray">
        <div class="top_bar">
            <ul class="breadcrumb">
                <li><a href="javascript:void(0);" style="color: #037dc5;font-size: 15px;">产品中心</a> <span class="divider">/</span></li>
                <li class="active"><a href="javascript:void(0)" style="font-weight: 600;font-size: 13px;color: #037dc5;">产品管理</a></li>
            </ul>
        </div>

        <div class="well-content">
          
          <!-- start -->
          <div class="table_options top_options">
              <div>
                  <span class="inline">产品名称:</span>
                  <input id="name" name="name" type="text" style="width:230px;">
                  <span class="inline">产品型号:</span>
                  <input id="model" name="model" type="text" style="width:230px;">
                  <a href="javascript:Product.queryList()" class="dark_green btn" style="margin-left:10px;">查询</a>
                  <a href="javascript:App.goToPage('product/add')" class="dark_green btn">添加</a>
              </div>
          </div>
          <!-- end   -->
          
          <div class="dataTables_wrapper">
            <table id="productTable" class="table table-striped table-bordered table-hover datatable"></table>
          </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/product.js"></script>
<script>
    Product.getTableData();
</script>

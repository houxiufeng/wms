<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <div class="span12">
    <div class="well light_gray">
        <div class="top_bar">
            <ul class="breadcrumb">
                <li><a href="javascript:void(0);" style="color: #037dc5;font-size: 15px;">My customer</a> <span class="divider">/</span></li>
                <li class="active"><a href="javascript:void(0)" style="font-weight: 600;font-size: 13px;color: #037dc5;">Customer management</a></li>
            </ul>
        </div>

        <div class="well-content">
          
          <!-- start -->
          <div class="table_options top_options">
              <div>
                  <span class="inline">Name:</span>
                  <input id="name" name="name" type="text" style="width:230px;">
                  <a title="search customer" id="search" href="javascript:Customer.queryList()" class="dark_green btn" style="margin-left:10px;"><i class="icon-search"></i></a>
                  <a title="add customer" href="javascript:App.goToPage('customer/add')" class="dark_green btn"><i class="icon-plus"></i></a>
                  <%--<a href="customer/downloadTemplate?fileName=customer.xlsx" class="dark_green btn" target="_blank"><i class="icon-download-alt"></i></a>--%>
              </div>
          </div>
          <!-- end   -->
          
          <div class="dataTables_wrapper">
            <table id="customerTable" class="table table-striped table-bordered table-hover datatable"></table>
          </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/customer.js"></script>
<script>
    Customer.getTableData(jQuery.cookie("customer_iDisplayLength"));
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <div class="span12">
    <div class="well light_gray">
        <div class="top_bar">
            <ul class="breadcrumb">
                <li><a href="javascript:void(0);" style="color: #037dc5;font-size: 15px;">My customer</a> <span class="divider">/</span></li>
                <li class="active"><a href="javascript:void(0)" style="font-weight: 600;font-size: 13px;color: #037dc5;">Branch management</a></li>
            </ul>
        </div>

        <div class="well-content">
          
          <!-- start -->
          <div class="table_options top_options">
              <div>
                  <span class="inline">Branch name:</span>
                  <input id="name" name="name" type="text" style="width:230px;">
                  <a title="search branch" href="javascript:Branch.queryList()" class="dark_green btn" style="margin-left:10px;"><i class="icon-search"></i></a>
                  <a title="add branch" href="javascript:App.goToPage('branch/add')" class="dark_green btn"><i class="icon-plus"></i></a>
              </div>
          </div>
          <!-- end   -->
          
          <div class="dataTables_wrapper">
            <table id="branchTable" class="table table-striped table-bordered table-hover datatable"></table>
          </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/branch.js"></script>
<script>
    Branch.getTableData(jQuery.cookie("branch_iDisplayLength"));
</script>

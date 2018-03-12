<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <div class="span12">
    <div class="well light_gray">
        <div class="top_bar">
            <ul class="breadcrumb">
                <li><a href="javascript:void(0);">系统管理</a> <span class="divider">/</span></li>
                <li class="active"><a href="javascript:void(0)">平台客户</a></li>
            </ul>
        </div>

        <div class="well-content">
          
          <!-- start -->
          <div class="table_options top_options">
              <div>
                  <a href="javascript:App.goToPage('organization/add')" class="blue btn">添加</a>
                  <a href="javascript:App.goToPage('organization')" class="blue btn">刷新</a>
              </div>
          </div>
          <!-- end   -->
          
          <div class="dataTables_wrapper">
            <table id="organizationTable" class="table table-striped table-bordered table-hover datatable"></table>
          </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/organization.js"></script>
<script>
    Organization.getTableData();
</script>

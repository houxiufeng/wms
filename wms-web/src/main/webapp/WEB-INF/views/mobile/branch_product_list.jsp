<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="margin-top: 40px; padding: 10px;">

    <input id="branchId" name="branchId" type="hidden" value="${branch.id}">
    <div class="form_row">
        <div class="field">
            <span style="font-size: 16px;font-weight: 600">${CURRENT_USER.organizationName}:你好,${CURRENT_USER.name}</span>
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right">店名称:</label>
        <div class="field">
            <span>${branch.name}</span>
            <a href="javascript:App.goToPage(appCtx+'/mobile/branch/product/add?branchId=${branch.id}')" class="btn dark_green"><i class="icon-wrench"></i></a>
            <a href="javascript:App.goToPage(appCtx+'/mobile/branch/list')" class="btn dark_green"><i class="icon-reply"></i></a>
        </div>
    </div>
    <div class="dataTables_wrapper" style="margin-top: 5px;">
        <table id="branchProductTable" class="table table-striped table-bordered table-hover datatable"></table>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/branchProduct.js"></script>

<script>
    jQuery(function ($) {
        BranchProduct.getMobileTableData();
    })
</script>

    

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="margin-top: 40px; padding: 10px;">
    <div class="form_row" style="text-align: center;">
        <span style="font-size: 16px;font-weight: 600">你好,刘德华</span>
    </div>

    <div class="dataTables_wrapper">
        <table id="branchTable" class="table table-striped table-bordered table-hover datatable"></table>
    </div>

    <div class="form_row">
        <div class="field">
            <a href="javascript:App.goToPage(appCtx + '/mobile/branch');" class="btn btn-large" style="font-size: 20px;">首页</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/branch/list');" class="btn light_blue btn-large" style="font-size: 20px;">创建</a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/branch.js"></script>
<script>
    Branch.getMobileTableData();
</script>
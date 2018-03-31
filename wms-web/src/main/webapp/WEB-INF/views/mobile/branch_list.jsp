<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="margin-top: 40px; padding: 10px;">
    <div class="form_row" style="text-align: center;">
        <span style="font-size: 16px;font-weight: 600">${CURRENT_USER.organizationName}:Hi,${CURRENT_USER.name}</span>
    </div>

    <div class="dataTables_wrapper">
        <table id="branchTable" class="table table-striped table-bordered table-hover datatable"></table>
    </div>

</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/branch.js"></script>
<script>
    Branch.getMobileTableData();
</script>
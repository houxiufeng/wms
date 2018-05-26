<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="well light_gray">
    <div class="well-content">
        <div class="dataTables_wrapper">
            <table id="engineerTable" class="table table-striped table-bordered table-hover datatable"></table>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/engineer.js"></script>
<script>
    jQuery(function ($) {
        Engineer.getTableDataSimple();
        $("#engineerTable").on("click", ":checkbox[name='engineerId']", function () {
            $("#engineerTable").find(":checkbox[name='engineerId']").not(this).attr("checked", false);
        });
    })

</script>

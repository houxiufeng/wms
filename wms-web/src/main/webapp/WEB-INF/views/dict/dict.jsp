<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);" style="color: #037dc5;font-size: 15px;">My basic setup</a> <span class="divider">/</span></li>
            <li class="active"><a href="javascript:void(0)" style="font-weight: 600;font-size: 13px;color: #037dc5;">Parameter setting</a></li>
        </ul>
    </div>

    <div class="well-content">
        <div class="form_row">
            <div class="span5">
                <label class="field_name align_right" style="width: 24%"><span style="color: red">*</span>Parameter type:</label>
                <div class="field" style="margin-left: 25%">
                    <select id="type" name="type" class="span12">
                        <option value="">All</option>
                        <c:forEach items="${dictTypes}" var="item">
                            <option value="${item.value}">${item.desc}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="span2">
                <%--<a href="javascript:Dict.save();" class="red btn"><i class="icon-save"></i></a>--%>
                <a title="search parameter" href="javascript:Dict.queryList();" class="dark_green btn"><i class="icon-search"></i></a>
                <a title="add parameter" href="javascript:Dict.popupAdd();" class="dark_green btn"><i class="icon-plus"></i></a>
            </div>
        </div>

        <div class="dataTables_wrapper">
            <table id="dictTable" class="table table-striped table-bordered table-hover datatable"></table>
        </div>

    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/dict.js"></script>
<script>
    Dict.getTableData(jQuery.cookie("dict_iDisplayLength"));
</script>

    

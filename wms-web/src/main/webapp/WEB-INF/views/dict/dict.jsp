<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);">字典中心</a> <span class="divider">/</span></li>
            <li class="active">字典管理</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="dictForm" style="margin-bottom: 10px;">
            <div class="form_row">
                <div class="span4">
                    <label class="field_name align_right"><span style="color: red">*</span>字典类型:</label>
                    <div class="field">
                        <select name="type" class="span12">
                            <c:forEach items="${dictTypes}" var="item">
                                <option value="${item.value}">${item.desc}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="span4">
                    <label class="field_name align_right"><span style="color: red">*</span>字典名称:</label>
                    <div class="field">
                        <input name="name" class="span12" type="text" maxlength="32">
                    </div>
                </div>
                <div class="span2">
                    <a href="javascript:Dict.save();" class="blue btn">保存</a>
                </div>
            </div>
        </form>

        <div class="dataTables_wrapper">
            <table id="dictTable" class="table table-striped table-bordered table-hover datatable"></table>
        </div>

    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/dict.js"></script>
<script>
    Dict.getTableData();
</script>

    

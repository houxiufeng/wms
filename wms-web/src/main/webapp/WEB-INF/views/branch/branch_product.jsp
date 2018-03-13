<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);">客户中心</a> <span class="divider">/</span></li>
            <li class="active">创建维修产品</li>
        </ul>
    </div>

    <div class="well-content">

        <div class="table_options top_options">
            <div>
                <span style="font-weight: 600; font-size: 14px;">维修产品情况</span>
                <a href="javascript:BranchProduct.saveOrUpdate();" class="blue btn" style="float: right">保存</a>
                <a href="javascript:BranchProduct.reset();" class="blue btn" style="float: right;margin-right: 2px">重置</a>
            </div>
        </div>
        <div class="span5" style="margin-bottom: 20px;">
            <form id="branchProductForm">
                <input name="id" type="hidden" value="">
                <input id="branchId" name="branchId" type="hidden" value="${branchId}">
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right"><span style="color: red">*</span>产品名称:</label>
                        <div class="field">
                            <select id="productName" class="span12">
                                <c:forEach items="${brands}" var="item">
                                    <option value="${item.name}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                </div>
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right"><span style="color: red">*</span>产品型号:</label>
                        <div class="field">
                            <select id="productId" name="productId" class="span12"></select>
                        </div>
                    </div>
                </div>
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right">维修产品位置:</label>
                        <div class="field">
                            <input name="position" class="span12" type="text" maxlength="128">
                        </div>
                    </div>
                </div>
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right"><span style="color: red">*</span>产品序列号:</label>
                        <div class="field">
                            <input name="sn" class="span12" type="text" maxlength="32">
                        </div>
                    </div>
                </div>

            </form>
        </div>
        <div class="span6">
            <div class="form_row">
                <div class="span12">
                    <label class="field_name align_right" style="width: 9%">poi</label>
                    <div class="field" style="margin-left: 10%">
                        <div id="googleMap" style="width:450px;height:220px;"></div>
                    </div>
                </div>
            </div>
        </div>

        <div class="dataTables_wrapper" style="margin-top: 20px;">
            <table id="branchProductTable" class="table table-striped table-bordered table-hover datatable"></table>
        </div>

    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/branchProduct.js"></script>

<script>
    var gMap;
    jQuery(function ($) {
        BranchProduct.getTableData();
        $("#productName").change(function () {
            $.ajax({
                url: appCtx + "/product/findByConditions",
                type: 'get',
                data:{"name":$(this).val()},
                dataType:'json',
                async: false,
                success: function(json) {
                    if (json.code == "0") {
                        var productIdObj = $("#productId");
                        productIdObj.empty();
                        $.each(json.products, function (index, value) {
                            productIdObj.append(("<option value='"+value.id+"'>"+value.model+"</option>"));
                        });
                    } else {
                        App.alert(json.message);
                    }
                },
                error: function(xhr, textStatus, errorThrown){
                    alert(errorThrown);
                }
            });
        });
        $("#productName").trigger("change");
        if (_isNull(gMap)) {
            gMap = initializeMap("googleMap");
        }

    })
</script>

    

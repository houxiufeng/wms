<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);">订单中心</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)">订单管理</a><span class="divider">/</span></li>
            <li class="active">下单</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="orderForm">

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>客户名称:</label>
                    <div class="field">
                        <select id="customerId" name="customerId" class="span10">
                            <c:forEach items="${customers}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <a href="javascript:App.goToPage(appCtx + '/customer')" class="blue btn btn-small">跳转</a>
                    </div>
                </div>
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>总/分店名称:</label>
                    <div class="field">
                        <select id="branchId" name="branchId" class="span10"></select>
                        <a href="javascript:App.goToPage(appCtx + '/branch')" class="blue btn btn-small">跳转</a>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>问题类型:</label>
                    <div class="field">
                        <select name="type" class="span10">
                            <c:forEach items="${types}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>维修产品:</label>
                    <div class="field">
                        <select id="branchProductId" name="branchProductId" class="span10"></select>
                        <a href="javascript:void(0)" class="blue btn btn-small">跳转</a>
                    </div>
                </div>
            </div>


            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>问题描述:</label>
                    <div class="field">
                        <textarea name="description" style="resize:none;width: 83%;height: 150px;"></textarea>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6" >
                    <div class="field">
                        <a href="javascript:Order.save();" class="btn red">保存</a>
                        <a href="javascript:App.goToPage(appCtx+'/order')" class="btn light_blue">取消</a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>
<script>
    jQuery(function ($) {
        $("#customerId").change(function () {
            $.ajax({
                url: appCtx + "/branch/findByConditions",
                type: 'get',
                data:{"customerId":$(this).val()},
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        var branchObj = $("#branchId");
                        branchObj.empty();
                        $.each(json.data, function (index, value) {
                            branchObj.append(("<option value='"+value.id+"'>"+value.name+"</option>"));
                        });
                        if (branchObj.children().length > 0) {
                            branchObj.find("option:first").click();
                            $("#branchId").trigger("change");
                        }
                    } else {
                        App.alert(json.message);
                    }
                },
                error: function(xhr, textStatus, errorThrown){
                    alert(errorThrown);
                }
            });
        });
        $("#branchId").change(function () {
            $.ajax({
                url: appCtx + "/branch/product/findByConditions",
                type: 'get',
                data:{"branchId":$(this).val()},
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        var branchProductObj = $("#branchProductId");
                        branchProductObj.empty();
                        $.each(json.data, function (index, value) {
                            branchProductObj.append(("<option value='"+value.id+"'>" +value.productName+"-" + value.productModel +"</option>"));
                        });
                        $("#branchProductId").next().attr("href","javascript:App.goToPage(appCtx + '/branch/product?branchId=" + $("#branchId").val() + "')");
                    } else {
                        App.alert(json.message);
                    }
                },
                error: function(xhr, textStatus, errorThrown){
                    alert(errorThrown);
                }
            });
        });
        $("#customerId").trigger("change");
    })
</script>

    

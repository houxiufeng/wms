<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .form_row {
        margin-bottom: 5px;
    }
</style>
<div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">客户名称:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px; ">
            ${order.customerName}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">总/分店名称:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.branchName}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">订单号:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.orderNo}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">维修产品名称:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.productName}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">维修产品型号:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.productModel}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">问题类型:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.typeName}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">问题描述:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.description}
        </div>
    </div>
    <c:if test="${vendor != null}">
        <c:if test="${vendor.avator != null and vendor.avator != ''}">
            <div class="form_row">
                <div class="field">
                    <img src="${vendor.avator}" style="width: 60%;">
                </div>
            </div>
        </c:if>
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">维修员名称:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                    ${vendor.name}
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">维修员编码:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                    ${vendor.code}
            </div>
        </div>

    </c:if>
    <c:if test="${order.score != null}">
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">评分:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                <c:if test="${order.score == 1}">好评</c:if>
                <c:if test="${order.score == 2}">中评</c:if>
                <c:if test="${order.score == 3}">差评</c:if>
            </div>
        </div>
    </c:if>
    <c:if test="${order.feedback != null and order.feedback != '' }">
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">评价:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                ${order.feedback}
            </div>
        </div>
    </c:if>

</div>

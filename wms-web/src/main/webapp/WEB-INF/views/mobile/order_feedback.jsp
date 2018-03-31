<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .form_row {
        margin-bottom: 5px;
    }
</style>
<div style="margin-top: 40px; padding: 10px;">
    <input type="hidden" id="orderId" value="${order.id}">
    <input type="hidden" id="vendorId" value="${order.vendorId}">
    <div class="form_row">
        <div class="field">
            <span style="font-size: 14px;font-weight: 600">${CURRENT_USER.organizationName}:happy to serve you!</span>
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Product name:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px; ">
            ${order.customerName}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Branch name:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.branchName}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Order No:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.orderNo}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Product name:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.productName}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Product model:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.productModel}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Problem type:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.typeName}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Description:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.description}
        </div>
    </div>
    <c:if test="${vendor.avator != null and vendor.avator != ''}">
        <div class="form_row">
            <div class="field">
                <img src="${vendor.avator}" style="width: 60%;">
            </div>
        </div>
    </c:if>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Engineer name:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${vendor.name}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Engineer code:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${vendor.code}
        </div>
    </div>

    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Score:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            <select id="score" name="score">
                <option value="1">Good</option>
                <option value="2">moderate</option>
                <option value="3">Bad</option>
            </select>
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Feedback:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            <textarea id="feedback" name="feedback" style="resize:none;height: 50px; width: 90%;" maxlength="200"></textarea>
        </div>
    </div>
    <div class="form_row">
        <div class="field">
            <a href="javascript:Order.feedback();" class="btn dark_green btn-large" style="font-size: 16px;">Feedback</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/order/list?flag=1');" class="btn  btn-large" style="font-size: 16px;">Back</a>
        </div>
    </div>

</div>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .form_row {
        margin-bottom: 5px;
    }
</style>
<div>
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
    <c:if test="${engineer != null}">
        <c:if test="${engineer.avator != null and engineer.avator != ''}">
            <div class="form_row">
                <div class="field">
                    <img src="${engineer.avator}" style="width: 60%;">
                </div>
            </div>
        </c:if>
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">Engineer name:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                    ${engineer.name}
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">Engineer code:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                    ${engineer.code}
            </div>
        </div>

    </c:if>
    <c:if test="${order.score != null}">
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">Score:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                <%--<c:if test="${order.score == 1}">Good</c:if>--%>
                <%--<c:if test="${order.score == 2}">moderate</c:if>--%>
                <%--<c:if test="${order.score == 3}">Bad</c:if>--%>
                ${order.score}
            </div>
        </div>
    </c:if>
    <c:if test="${order.feedback != null and order.feedback != '' }">
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">Feedback:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                ${order.feedback}
            </div>
        </div>
    </c:if>

</div>

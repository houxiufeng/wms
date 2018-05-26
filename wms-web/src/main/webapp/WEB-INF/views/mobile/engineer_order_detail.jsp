<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Branch name:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${branchProduct.branch.name}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Branch address:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px; ">
            ${branchProduct.branch.address}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Product name:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${branchProduct.product.name}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Product model:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${branchProduct.product.model}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">Position:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            <span>${branchProduct.position}</span>
            <a href="javascript:Order.showPOI('${branchProduct.poi}');" class="btn dark_green btn-small" style="margin-left: 2px;">POI</a>
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
    <c:if test="${order.fixRemark != null and order.fixRemark != '' }">
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">Fix remarks:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                    ${order.fixRemark}
            </div>
        </div>
    </c:if>
    <c:if test="${order.remark != null and order.remark != '' }">
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">Complete remarks:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                    ${order.remark}
            </div>
        </div>
    </c:if>

</div>

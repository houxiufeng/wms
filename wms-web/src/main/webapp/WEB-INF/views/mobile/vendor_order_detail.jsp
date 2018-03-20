<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
    <%--<div class="form_row" style="text-align: center;">--%>
        <%--<span style="font-size: 16px;font-weight: 600">你好,${order.vendorName}</span>--%>
    <%--</div>--%>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">总/分店名称:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${branchProduct.branch.name}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">总分店地址:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px; ">
            ${branchProduct.branch.address}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">维修产品名称:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${branchProduct.product.name}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">维修产品型号:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${branchProduct.product.model}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">维修产品位置:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            <span>${branchProduct.position}</span>
            <a href="javascript:Order.showPOI('${branchProduct.poi}');" class="btn light_blue btn-small" style="margin-left: 2px;">POI</a>
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">客户问题类型:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.typeName}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 30%;">客户问题描述:</label>
        <div class="field" style="margin-left: 31%; margin-top: 5px;">
            ${order.description}
        </div>
    </div>
    <c:if test="${order.fixRemark != null and order.fixRemark != '' }">
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">维修备注:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                    ${order.fixRemark}
            </div>
        </div>
    </c:if>
    <c:if test="${order.remark != null and order.remark != '' }">
        <div class="form_row">
            <label class="field_name align_right" style="width: 30%;">完成备注:</label>
            <div class="field" style="margin-left: 31%; margin-top: 5px;">
                    ${order.remark}
            </div>
        </div>
    </c:if>

</div>

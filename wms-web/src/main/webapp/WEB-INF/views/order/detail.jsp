<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">

    <div class="well-content">
        <form id="orderForm">

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>订单号:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.orderNo}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>客户名称:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.customerName}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>总/分店名称:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.branchName}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>问题类型:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.typeName}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>维修产品:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.productName}-${order.productModel}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>维修产品:</label>
                    <div class="field" style="margin-top: 5px">
                        <c:if test="${order.status == 0}"><span>派单中</span></c:if>
                        <c:if test="${order.status == 1}"><span>检查中</span></c:if>
                        <c:if test="${order.status == 2}"><span>维修中</span></c:if>
                        <c:if test="${order.status == 3}"><span>审核中</span></c:if>
                        <c:if test="${order.status == 4}"><span>评价中</span></c:if>
                        <c:if test="${order.status == 5}"><span>已取消</span></c:if>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>问题描述:</label>
                    <div class="field">
                        <textarea name="remark" style="resize:none;width: 83%;height: 150px;">${order.remark}</textarea>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

    

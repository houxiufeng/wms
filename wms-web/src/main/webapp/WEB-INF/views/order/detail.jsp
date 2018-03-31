<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">

    <div class="well-content">
        <form id="orderForm">

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">Order no:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.orderNo}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">Customer name:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.customerName}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">Branch name:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.branchName}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">Problem types:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.typeName}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">Product:</label>
                    <div class="field" style="margin-top: 5px">
                        <span>${order.productName}-${order.productModel}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">Order status:</label>
                    <div class="field" style="margin-top: 5px">
                        <c:if test="${order.status == 0}"><span>Assigning</span></c:if>
                        <c:if test="${order.status == 1}"><span>Checking</span></c:if>
                        <c:if test="${order.status == 2}"><span>Fixing</span></c:if>
                        <c:if test="${order.status == 3}"><span>Auditing</span></c:if>
                        <c:if test="${order.status == 4}"><span>Complete</span></c:if>
                        <c:if test="${order.status == 5}"><span>Cancel</span></c:if>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">Description:</label>
                    <div class="field" style="margin-top: 5px">
                        <span style="display: inline-block; width: 85%">${order.description}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">Fix remarks:</label>
                    <div class="field" style="margin-top: 5px">
                        <span style="display: inline-block; width: 85%">${order.fixRemark}</span>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">Complete remarks:</label>
                    <div class="field" style="margin-top: 5px">
                        <span style="display: inline-block; width: 85%">${order.remark}</span>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

    

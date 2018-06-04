<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="span12" style="margin-top: 45px;">
    <div class="well blue">
        <div class="well-header" style="min-height: 40px">
            <a href="javascript:void(0)" style="color: white; display: inline-block; padding: 10px; font-size: 16px;" onclick="App.goToPage(appCtx + '/mobile/engineer/order/list', {'status':2});">Back</a>
        </div>
        <div class="well-content price-table" style="min-height: 80vh; font-size: 15px;">
            <div style="min-height: 70vh;">
                <ul>
                    <li style="line-height:40px;">Product name: ${orderInfo.product.name}</li>
                    <li style="line-height:40px;">Product model: ${orderInfo.product.model}</li>
                    <li style="line-height:40px;">Position: ${orderInfo.branchProduct.position}</li>
                    <li style="line-height:40px;">Fix remarks:
                        <textarea id="fixRemark" style="resize:none;height: 50px; width: 90%;" maxlength="200"></textarea>
                    </li>
                </ul>
            </div>
            <div style="min-height: 10vh; padding: 18px;">
                <a href="javascript:void(0)" class="btn btn-xlarge btn-block blue" onclick="javascript:Order.mobileFixed(${orderInfo.order.id});">OK</a>
            </div>
        </div>

    </div>
</div>
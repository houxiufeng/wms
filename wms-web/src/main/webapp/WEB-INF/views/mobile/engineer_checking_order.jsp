<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="span12" style="margin-top: 45px;">
    <div class="well blue">
        <div class="well-header" style="min-height: 40px">
            <a href="javascript:void(0)" style="color: white; display: inline-block; padding: 10px; font-size: 16px;" onclick="App.goToPage(appCtx + '/mobile/engineer/order/list', {'status':1});">Back</a>
        </div>
        <div class="well-content price-table" style="min-height: 80vh; font-size: 13px;">
            <div style="min-height: 70vh;">
                <ul>
                    <li style="line-height:25px;background: #ff8f32; color: white; font-size: 14px;">Please go for check the product and scan the QR-code for confirm the problem or you can reject the job.</li>
                    <li style="line-height:30px;font-size: 16px;font-weight: 600;">Detail</li>
                    <li style="line-height:30px;">Branch name: ${orderInfo.branch.name}</li>
                    <li style="line-height:30px;">Branch city: ${orderInfo.branch.city}</li>
                    <li style="line-height:30px;">Branch full address: ${orderInfo.branch.address}</li>
                    <li style="line-height:30px;">Contact name: ${orderInfo.branch.contactPerson}</li>
                    <li style="line-height:30px;">Contact number: ${orderInfo.branch.contactPhone}</li>
                    <li style="line-height:30px;">Product brand: ${orderInfo.product.name}</li>
                    <li style="line-height:30px;">Product model: ${orderInfo.product.model}</li>
                    <li style="line-height:30px;">Complain username: ${orderInfo.branch.userName}</li>
                    <li style="line-height:30px;">Date: <fmt:formatDate value="${orderInfo.order.checkTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
                    <li style="line-height:30px;">Problem type: ${orderInfo.order.typeName}</li>
                    <li style="line-height:30px;">Problem Description: ${orderInfo.order.description}</li>
                    <li style="line-height:40px;">
                        <a href="javascript:Order.showPOI('${orderInfo.branchProduct.poi}');" class="btn dark_green" style="margin-left: 2px;">POI</a>
                        <a href="javascript:showImgs('${orderInfo.product.imgUrl}');" class="btn dark_green" style="margin-left: 20px;">Pic</a>
                        <a href="javascript:showAttachments('${orderInfo.product.fileUrl}');" class="btn dark_green" style="margin-left: 20px;">Att</a>
                        <a href="javascript:Order.showText('Spare-part list', '${orderInfo.product.sparePartListStr}');" class="btn dark_green" style="margin-left: 20px;">S-L</a>
                        <a href="javascript:Order.showText('Check list', '${orderInfo.product.checkListStr}');" class="btn dark_green" style="margin-left: 20px;">C-L</a>
                    </li>
                </ul>
            </div>

            <div style="min-height: 10vh; padding: 18px;">
                <a href="javascript:void(0)" class="btn btn-xlarge btn-block blue" onclick="javascript:Order.reject(${orderInfo.order.id});">Reject</a>
            </div>
        </div>

    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/order.js"></script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="margin-top: 40px; padding: 10px;">
    <div class="form_row" style="text-align: center;">
        <span style="font-size: 16px;font-weight: 600">你好,${vendor.name}</span>
    </div>
    <div class="form_row">
        <div class="field">
            <img src="${vendor.avator}" style="width: 60%;">
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 50%;font-size: 18px;">检查中订单:</label>
        <div class="field" style="margin-left: 55%; margin-top: 5px;font-size: 18px;">
            ${checkAmount}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 50%;font-size: 18px;">维修中订单:</label>
        <div class="field" style="margin-left: 55%; margin-top: 5px; font-size: 18px;">
            ${fixAmount}
        </div>
    </div>
    <div class="form_row">
        <label class="field_name align_right" style="width: 50%;font-size: 18px;">已完成订单:</label>
        <div class="field" style="margin-left: 55%; margin-top: 5px;font-size: 18px;">
            ${completeAmount}
        </div>
    </div>

    <div class="form_row">
        <div class="col-xs-11">
            <label class="field_name align_right" style="width: 35%;font-size: 18px;">我的评分:</label>
            <div class="field" style="margin-top: 5px;margin-left: 36%;font-size: 18px;">
                <span style="color: green">好评</span>:${vendor.goodScore}
                <span style="color: darkslategrey;">中评</span>:${vendor.moderateScore}
                <span style="color:red">差评</span>:${vendor.badScore}
            </div>
        </div>
    </div>

    <div class="form_row">
        <div class="field">
            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor');" class="btn light_blue btn-large" style="font-size: 16px;">首页</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=1');" class="btn btn-large" style="font-size: 16px;">检查中</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=2');" class="btn btn-large" style="font-size: 16px;">维修中</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/vendor/order/list?status=4');" class="btn btn-large" style="font-size: 16px;">已完成</a>
        </div>
    </div>
</div>

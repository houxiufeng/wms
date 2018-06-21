<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .form_row {
        margin-bottom: 25px;
    }
    .content {
        min-height: 70vh;
    }
    .footer {
        height: 20vh;
    }
</style>

<div class="content" style="margin-top: 40px; padding: 10px; font-size: 20px; margin-left: 10px;text-align: center;">
    <div class="form_row" style="text-align: center;">
        <span style="font-weight: 600">${CURRENT_USER.organizationName}:happy to serve you!</span>
    </div>
    <div class="form_row" >
        Customer name: ${customer.name}
    </div>
    <div class="form_row" >
        Branch name: ${branch.name}
    </div>
    <div class="form_row" >
        <img src="${customer.logo}" style="width: 60%;">
    </div>

    <div class="form_row" >
        <div>
            <a href="javascript:App.goToPage(appCtx + '/mobile/customer/order/list', {'status':2});" class="btn blue" style="width: 40%; line-height:30px; font-size: 16px;">
                Progressing orders <br><span style="font-weight: 600; font-size: 20px;">${fixingNum}</span>
            </a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/customer/order/list', {'status':4,'feedbackFlag':1});" class="btn blue" style="width: 40%; line-height:30px; font-size: 16px;">
                Need rate <br><span style="font-weight: 600; font-size: 20px;">${needRateNum}</span>
            </a>
        </div>
    </div>

</div>
<div class="footer">
    <div style="margin-left: 10px;">
        <a href="javascript:App.goToPage(appCtx + '/mobile/customer');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Home <i class="icon-home"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/customer/report');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Report <i class="icon-table"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/customer/me');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Me <i class="icon-user"></i></a>
    </div>
</div>

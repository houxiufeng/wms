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
        <span style="font-weight: 600">${CURRENT_USER.organizationName}:Hi,${engineer.name}</span>
    </div>
    <div class="form_row" >
        <img src="${engineer.avator}" style="width: 40%;">
    </div>

    <div class="form_row" >
        <div>
            <a href="javascript:App.goToPage(appCtx + '/mobile/engineer/order/list', {'status':1});" class="btn blue" style="width: 40%; line-height:30px; font-size: 16px;">
                Checking orders <br><span style="font-weight: 600; font-size: 20px;">${checkAmount}</span>
            </a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/engineer/order/list', {'status':2});" class="btn blue" style="width: 40%; line-height:30px; font-size: 16px;">
                Fixing orders <br><span style="font-weight: 600; font-size: 20px;">${fixAmount}</span>
            </a>
        </div>
    </div>

    <div class="form_row" style="margin-top: 20px; text-align: left; padding-left: 10px;" >
        <div>
            <p><span>Your rate:</span></p>
            <p>
                <span style="">5 star</span>:${engineer.fiveStar}
                <span style="margin-left:5%;">4 star</span>:${engineer.fourStar}
                <span style="margin-left:5%;">3 star</span>:${engineer.threeStar}
                <span style="margin-left:5%;">2 star</span>:${engineer.twoStar}
                <span style="margin-left:5%;">1 star</span>:${engineer.oneStar}
            </p>

        </div>
    </div>

</div>
<div class="footer">
    <div style="margin-left: 10px;">
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Home <i class="icon-home"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer/report');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Report <i class="icon-table"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer/me');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Me <i class="icon-user"></i></a>
    </div>
</div>

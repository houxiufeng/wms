<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .form_row {
        margin-bottom: 25px;
    }
    .content {
        min-height: calc(100vh - 110px);
    }
    .footer {
        height: 50px;
    }
</style>
<div class="content" style="margin-top: 40px; padding: 10px; font-size: 20px; margin-left: 10px;">
    <div class="form_row" >
        <span style="font-weight: 600">Hi,${engineer.name}</span>
    </div>
    <div class="form_row" >
        <img src="${engineer.avator}" style="width: 50%;">
    </div>

    <div class="form_row" >
        <div>Checking orders:${checkAmount}</div>
    </div>
    <div class="form_row" >
        <div>Fixing orders:${fixAmount}</div>
    </div>
    <div class="form_row" >
        <div>Complete orders:${completeAmount}</div>
    </div>

    <div class="form_row" >
        <div>
            <span>My score:</span>
            <span style="color: green">Good</span>:${engineer.goodScore}
            <span style="color: darkslategrey;">moderate</span>:${engineer.moderateScore}
            <span style="color:red">Bad</span>:${engineer.badScore}
        </div>
    </div>

    <%--<div class="form_row" >--%>
        <%--<div>--%>
            <%--<a href="javascript:App.goToPage(appCtx + '/mobile/engineer');" class="btn dark_green btn-large" style="font-size: 16px;">首页</a>--%>
            <%--<a href="javascript:App.goToPage(appCtx + '/mobile/engineer/order/list?status=1');" class="btn btn-large" style="font-size: 16px;">检查中</a>--%>
            <%--<a href="javascript:App.goToPage(appCtx + '/mobile/engineer/order/list?status=2');" class="btn btn-large" style="font-size: 16px;">维修中</a>--%>
            <%--<a href="javascript:App.goToPage(appCtx + '/mobile/engineer/order/list?status=4');" class="btn btn-large" style="font-size: 16px;">已完成</a>--%>
        <%--</div>--%>
    <%--</div>--%>
</div>
<div class="footer">
    <div style="margin-left: 10px;padding: 10px;">
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer');" class="btn dark_green btn-large" style="font-size: 16px;">Home</a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer/order/list?status=1');" class="btn btn-large" style="font-size: 16px;">Checking</a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer/order/list?status=2');" class="btn btn-large" style="font-size: 16px;">Fixing</a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer/order/list?status=4');" class="btn btn-large" style="font-size: 16px;">Complete</a>
    </div>
</div>
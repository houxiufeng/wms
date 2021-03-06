<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="span12" style="margin-top: 45px;">
    <div class="well blue">
        <div class="well-header" style="min-height: 40px">
            <a href="javascript:void(0)" style="color: white; display: inline-block; padding: 10px; font-size: 16px;" onclick="App.goToPage(appCtx + '/mobile/customer/order/list', {'status':4,'feedbackFlag':1});">Back</a>
        </div>
        <div class="well-content price-table" style="min-height: 80vh; font-size: 13px;">
            <div style="min-height: 70vh;">
                <input type="hidden" id="orderId" value="${orderInfo.order.id}">
                <input type="hidden" id="engineerId" value="${orderInfo.order.engineerId}">
                <ul>
                    <li style="line-height:30px;font-size: 16px;font-weight: 600;">Detail</li>
                    <li style="line-height:30px;">Branch name: ${orderInfo.branch.name}</li>
                    <li style="line-height:30px;">Branch city: ${orderInfo.branch.city}</li>
                    <li style="line-height:30px;">Branch full address: ${orderInfo.branch.address}</li>
                    <li style="line-height:30px;">Contact name: ${orderInfo.branch.contactPerson}</li>
                    <li style="line-height:30px;">Contact number: ${orderInfo.branch.contactPhone}</li>
                    <li style="line-height:30px;">Product brand: ${orderInfo.product.name}</li>
                    <li style="line-height:30px;">Product model: ${orderInfo.product.model}</li>
                    <li style="line-height:30px;">Complain username: ${orderInfo.branch.userName}</li>
                    <li style="line-height:30px;">Complete Date: <fmt:formatDate value="${orderInfo.order.completeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
                    <li style="line-height:30px;">Confirm problem: ${orderInfo.order.typeName}</li>
                    <li style="line-height:30px;">Confirm Description: ${orderInfo.order.description}</li>
                    <li style="line-height:30px;">
                        <%--Rating:--%>
                        <span>Rating:</span>
                        <div id="starRating">
                            <p class="photo">
                                <span><i class="high"></i><i class="nohigh"></i></span>
                                <span><i class="high"></i><i class="nohigh"></i></span>
                                <span><i class="high"></i><i class="nohigh"></i></span>
                                <span><i class="high"></i><i class="nohigh"></i></span>
                                <span><i class="high"></i><i class="nohigh"></i></span>
                            </p>
                        </div>
                        <input id="score" type="hidden" value="">
                        <%--<select id="score" name="score">--%>
                            <%--<option value="1">Good</option>--%>
                            <%--<option value="2">moderate</option>--%>
                            <%--<option value="3">Bad</option>--%>
                        <%--</select>--%>
                    </li>
                    <li style="line-height:30px;">
                        Remark:
                        <textarea id="feedback" name="feedback" style="resize:none;height: 50px; width: 90%;" maxlength="200"></textarea>
                    </li>
                </ul>
            </div>

            <div style="min-height: 10vh; padding: 18px;">
                <a href="javascript:void(0)" class="btn btn-xlarge btn-block blue" onclick="javascript:Order.mobileFeedback();">Rate</a>
            </div>
        </div>

    </div>
</div>
<script>
    jQuery(function ($) {
        //评分
        var starRating = 0;
        $('.photo span').on('mouseenter',function () {
            // var index = $(this).index()+1;
            $(this).prevAll().find('.high').css('z-index',1);
            $(this).find('.high').css('z-index',1);
            $(this).nextAll().find('.high').css('z-index',0);
        });
        $('.photo').on('mouseleave',function () {
            $(this).find('.high').css('z-index',0);
            var count = starRating;
            if(count == 5) {
                $('.photo span').find('.high').css('z-index',1);
            } else {
                $('.photo span').eq(count).prevAll().find('.high').css('z-index',1);
            }
        });
        $('.photo span').on('click',function () {
            var index = $(this).index()+1;
            $(this).prevAll().find('.high').css('z-index',1);
            $(this).find('.high').css('z-index',1);
            starRating = index;
            // alert('评分：'+(starRating.toFixed(1)+'分'));
            $("#score").val(starRating);
        });
    })
</script>

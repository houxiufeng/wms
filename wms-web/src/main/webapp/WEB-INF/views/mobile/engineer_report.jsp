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

    <div id="report_main">
        <div class="well blue">
            <div class="well-header">
                <h5>Order</h5>
            </div>

            <div class="well-content">
                <div id="barchart" style="height: 200px; width: 100%;"></div>
            </div>
        </div>

        <div class="form_row" >
            <div>
                <a href="javascript:App.goToPage(appCtx+'/mobile/engineer/report/sum');" class="btn blue" style="width: 35%; line-height:45px; font-size: 16px;">
                    Order report
                </a>
                <a href="javascript:App.goToPage(appCtx+'/mobile/engineer/report/rate');" class="btn blue" style="width: 35%; line-height:45px; font-size: 16px;">
                    Rate report
                </a>
            </div>
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
<script src="${pageContext.request.contextPath}/flatpoint/js/library/flot/jquery.flot.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/library/flot/jquery.flot.orderBars.js"></script>

<script>
    var d1 = eval('${charData.dataStr}');
    var ds = new Array();
    ds.push({
        data:d1,
        bars: {
            show: true,
            barWidth: 0.3,
            align: "center",
            order: 1
        },
        color: '#0072c6'
    });

    var ticks = eval('${charData.ticksStr}');
    //Display graph
    jQuery.plot(jQuery("#barchart"), ds, {
        xaxis: {
            ticks: ticks
        },
        series: {
            bars: {
                show: true
            }
        },
        bars: {
            align: "center",
            barWidth: 0.5
        }
    });
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="margin-top: 40px; padding: 10px;">

    <form id="branchProductForm">
        <%--<input id="branchId" type="hidden" value="${bp.branchId}">--%>
        <input id="id" name="id" type="hidden" value="${bp.id}">
        <input type="hidden" id="point_x">
        <input type="hidden" id="point_y">
        <div class="form_row">
            <div class="field">
                <span style="font-size: 16px;font-weight: 600">${CURRENT_USER.organizationName}:Hi,${CURRENT_USER.name}</span>
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right"><span style="color: red">*</span>Product name:</label>
            <div class="field">
                <select id="productName" class="span10">
                    <c:forEach items="${brands}" var="item">
                        <option value="${item.name}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right"><span style="color: red">*</span>Product model:</label>
            <div class="field">
                <select id="productId" name="productId" class="span10"></select>
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right">Position:</label>
            <div class="field">
                <input name="position" type="text" maxlength="128" class="span10" value="${bp.position}">
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right"><span style="color: red">*</span>Serial number:</label>
            <div class="field">
                <input id="sn" name="sn" type="text" maxlength="32" class="span10" value="${bp.sn}">
            </div>
        </div>

        <div class="form_row">
            <label class="field_name align_right">Date range:</label>
            <div class="field">
                <input id="beginTime" name="beginTime" type="text" class="datetimepicker" style="width: 40%" value="<fmt:formatDate value="${bp.beginTime}" pattern="yyyy-MM-dd"/>" readonly> —
                <input id="endTime" name="endTime" type="text" class="datetimepicker" style="width: 40%" value="<fmt:formatDate value="${bp.endTime}" pattern="yyyy-MM-dd"/>" readonly>
            </div>
        </div>

        <div class="form_row">
            <label class="field_name align_right">poi</label>
            <div class="field">
                <div id="googleMap" style="width:280px;height:220px;"></div>
            </div>
        </div>
        <div class="form_row">
            <div class="field">
                <a id="btn_updateBranchProduct" href="javascript:;" class="btn red">Update</a>
                <a id="btn_qrCode" href="javascript:;" class="btn dark_green">QRCode</a>
                <a href="javascript:App.goToPage(appCtx + '/mobile/branch/product/list?branchId=${bp.branchId}')" class="btn dark_green">Back</a>
            </div>
        </div>

    </form>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-migrate-1.1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/branchProduct.js"></script>--%>

<script>
    jQuery(function ($) {
        $("#productName").change(function () {
            $.ajax({
                url: appCtx + "/product/findByConditions",
                type: 'get',
                data:{"name":$(this).val()},
                dataType:'json',
                async: false,
                success: function(json) {
                    if (json.code == "0") {
                        var productIdObj = $("#productId");
                        productIdObj.empty();
                        $.each(json.products, function (index, value) {
                            productIdObj.append(("<option value='"+value.id+"'>"+value.model+"</option>"));
                        });
                    } else {
                        App.alert(json.message);
                    }
                },
                error: function(xhr, textStatus, errorThrown){
                    alert(errorThrown);
                }
            });
        });
        $("#productName").val('${bp.product.name}');
        $("#productName").trigger("change");
        $("#productId").val(${bp.product.id});
        var map = initializeMap("googleMap");
        var poi = '${bp.poi}';
        if (!_isNull(poi)) {
            var latlng = poi.split(",");
            var myCenter=new google.maps.LatLng(latlng[1],latlng[0]);
            placeMarker(map, myCenter);
        }

        $("#btn_updateBranchProduct").click(function() {
            if (_isNull($("#productId").val())) {
                App.alert("product model can't be empty!");
                return false;
            }
            if (_isNull($("#sn").val())) {
                App.alert("serial number can't be empty!");
                return false;
            }
            var data = $("#branchProductForm").serialize();
            if (!_isNull($("#point_x").val()) && !_isNull($("#point_y").val())) {
                data += "&poi=" + $("#point_x").val() + "," + $("#point_y").val();
            }
            $.ajax({
                url: appCtx + "/branch/product/update",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!");
                        App.goToPage(appCtx + '/mobile/branch/product/list?branchId=${bp.branchId}')
                    } else {
                        App.alert(json.message);
                    }
                },
                error: function(xhr, textStatus, errorThrown){
                    alert(errorThrown);
                }
            });
        });
        $("#btn_qrCode").click(function () {
            var prefix = window.location.href.substring(0, window.location.href.indexOf("/mobile"))
            var url = prefix + "/mobile/branch/product/${bp.id}";
//            $.dialog({
//                title: '二维码',
//                content: '<div id="qrcode"></div>'
//            });
            $.confirm({
                title: 'QRCode',
                confirmButton: 'Print',
                confirmButtonClass: 'btn red',
                cancelButton: 'Close',
                content: '<div id="qrcode"></div>',
                confirm: function(){
                    $("#qrcode").jqprint();
                }
            });
            $("#qrcode").qrcode({
                render: "canvas", //canvas方式
                width: 200, //宽度
                height:200, //高度
                text: url //任意内容
            });
            var canvas = $('#qrcode canvas');
            var img = canvas[0].toDataURL("image/png");
            $('#qrcode').html("<img src='" + img + "'>");

        });

        $(".datetimepicker").datetimepicker({
            format:"yyyy-mm-dd",
            autoclose: true,
            pickTime: false,
            minView: '2',
            todayBtn: true
        });

    })
</script>

    

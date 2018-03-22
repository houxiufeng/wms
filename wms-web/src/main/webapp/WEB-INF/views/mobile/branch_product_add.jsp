<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style="margin-top: 40px; padding: 10px;">

    <form id="branchProductForm">
        <input id="branchId" name="branchId" type="hidden" value="${branchId}">
        <div class="form_row">
            <div class="field">
                <span style="font-size: 16px;font-weight: 600">${CURRENT_USER.organizationName}:你好,${CURRENT_USER.name}</span>
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right"><span style="color: red">*</span>产品名称:</label>
            <div class="field">
                <select id="productName" class="span10">
                    <c:forEach items="${brands}" var="item">
                        <option value="${item.name}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right"><span style="color: red">*</span>产品型号:</label>
            <div class="field">
                <select id="productId" name="productId" class="span10"></select>
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right">产品位置:</label>
            <div class="field">
                <input name="position" type="text" maxlength="128" class="span10">
            </div>
        </div>
        <div class="form_row">
            <label class="field_name align_right"><span style="color: red">*</span>序列号:</label>
            <div class="field">
                <input id="sn" name="sn" type="text" maxlength="32" class="span10">
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
                <a id="btn_saveBranchProduct" href="javascript:;" class="btn red">保存</a>
                <a id="btn_qrCode" href="javascript:;" class="btn red" style="display: none">二维码</a>
                <a href="javascript:App.goToPage(appCtx + '/mobile/branch/list');" class="btn light_blue">返回</a>
            </div>
        </div>
        <input type="hidden" id="point_x" name="point_x">
        <input type="hidden" id="point_y" name="point_y">
        <input type="hidden" id="branchProductId">
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
        $("#productName").trigger("change");
        initializeMap("googleMap");

        $("#btn_saveBranchProduct").click(function() {
            if (_isNull($("#productId").val())) {
                App.alert("产品型号必填！");
                return false;
            }
            if (_isNull($("#sn").val())) {
                App.alert("序列号必填！");
                return false;
            }
            var data = $("#branchProductForm").serialize();
            if (!_isNull($("#point_x").val()) && !_isNull($("#point_y").val())) {
                data += "&poi=" + $("#point_x").val() + "," + $("#point_y").val();
            }
            $.ajax({
                url: appCtx + "/branch/product/create",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("创建成功!", function(){
                            $("#btn_qrCode").show();
                            $("#btn_saveBranchProduct").hide();
                            $("#branchProductId").val(json.data);
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
        $("#btn_qrCode").click(function () {
            var prefix = window.location.href.substring(0, window.location.href.indexOf("/mobile"))
            var url = prefix + "/mobile/branch/product/"+$("#branchProductId").val();
//            $.dialog({
//                title: '二维码',
//                content: '<div id="qrcode"></div>'
//            });
            $.confirm({
                title: '二维码',
                confirmButton: '打印',
                confirmButtonClass: 'btn red',
                cancelButton: '关闭',
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

        })

    })
</script>

    

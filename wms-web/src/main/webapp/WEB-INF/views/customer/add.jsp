<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">客户中心</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">客户管理</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">创建客户</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="customerForm">

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">基本信息</label>
                </div>

                <div class="form_row">
                    <div class="span4">
                        <label class="field_name align_right"><span style="color: red">*</span>客户名称:</label>
                        <div class="field">
                            <input name="name" class="span12" type="text" maxlength="100">
                        </div>
                    </div>
                    <div class="span4">
                        <label class="field_name align_right"><span style="color: red">*</span>客户Code:</label>
                        <div class="field">
                            <input name="code" class="span12" type="text" maxlength="3">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span4">
                        <label class="field_name align_right">客户网址:</label>
                        <div class="field">
                            <input name="website" class="span12" type="text" maxlength="100">
                        </div>
                    </div>
                    <div class="span4">
                        <label class="field_name align_right">客户电话:</label>
                        <div class="field">
                            <input name="phone" class="span12" type="text" maxlength="20">
                        </div>
                    </div>
                </div>
            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">法人信息</label>
                </div>
                <div class="form_row">
                    <div class="span4">
                        <label class="field_name align_right"><span style="color: red">*</span>法人名称:</label>
                        <div class="field">
                            <input name="legalPerson" class="span12" type="text" maxlength="64">
                        </div>
                    </div>
                    <div class="span4">
                        <label class="field_name align_right">法人电话:</label>
                        <div class="field">
                            <input name="legalPhone" class="span12" type="text" maxlength="20">
                        </div>
                    </div>
                </div>
            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">联系人信息</label>
                </div>
                <div class="form_row">
                    <div class="span4">
                        <label class="field_name align_right" style="width: 68px;"><span style="color: red">*</span>联系人名称:</label>
                        <div class="field">
                            <input name="contactPerson" class="span12" type="text" maxlength="64">
                        </div>
                    </div>
                    <div class="span4">
                        <label class="field_name align_right">联系人电话:</label>
                        <div class="field">
                            <input name="contactPhone" class="span12" type="text" maxlength="20">
                        </div>
                    </div>
                </div>
            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">其他信息</label>
                </div>
                <div class="form_row">
                    <div class="span4">
                        <label class="field_name align_right">客户级别:</label>
                        <div class="field">
                            <select name="type">
                                <c:forEach items="${types}" var="item">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="span4">
                        <label class="field_name align_right">客户信用:</label>
                        <div class="field">
                            <select name="creditStatus">
                                <c:forEach items="${creditStatus}" var="item">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">合同信息</label>
                </div>
                <div class="form_row">
                    <div class="span4">
                        <label class="field_name align_right" style="width: 68px;"><span style="color: red">*</span>合同名称:</label>
                        <div class="field">
                            <input name="contractName" class="span12" type="text" maxlength="64">
                        </div>
                    </div>
                    <div class="span4">
                        <label class="field_name align_right">合同有效期:</label>
                        <div class="field">
                            <input name="contractFrom" type="text" class="datetimepicker" style="width:135px;" readonly> —
                            <input name="contractTo" type="text" class="datetimepicker" style="width:135px;" readonly>
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span4">
                        <label class="field_name align_right" style="width: 68px;">合同位置:</label>
                        <div class="field">
                            <input name="contractPosition" class="span12" type="text" maxlength="100">
                        </div>
                    </div>
                    <div class="span4">
                        <label class="field_name align_right">合同金额:</label>
                        <div class="field">
                            <input name="contractAmount" class="span12" type="text" maxlength="13">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span8" >
                        <label class="field_name align_right" style="width: 67px;">合同条款:</label>
                        <div class="field" style="margin-left: 75px;">
                            <textarea name="contractInfo" style="resize:none;width: 100%;height: 80px;"></textarea>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6" style="margin-left: 200px;">
                    <div class="field">
                        <a href="javascript:Customer.save();" class="btn red btn-large" style="width: 60px;">提交</a>
                        <a href="javascript:App.goToPage(appCtx+'/customer')" class="btn dark_green btn-large" style="width: 60px;">取消</a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>
<script>
    jQuery(".datetimepicker").datetimepicker({
        format:"yyyy-mm-dd",
        autoclose: true,
        pickTime: false,
        minView: '2',
        todayBtn: true
    });
</script>

    

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="span12">
    
    <div class="well light_gray">
	    <div class="top_bar">
		    <ul class="breadcrumb">
			    <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">客户中心</a> <span class="divider">/</span></li>
			    <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">客户管理</a><span class="divider">/</span></li>
			    <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">编辑客户</li>
		    </ul>
	    </div>

        <div class="well-content">
        <form id="customerForm">
            
            <input type="hidden" name="id" value="${customer.id}" />
	        <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
		        <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
			        <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">基本信息</label>
		        </div>
		        <div class="form_row">
			        <div class="span4">
				        <label class="field_name align_right"><span style="color: red">*</span>客户名称:</label>
				        <div class="field">
					        <input name="name" class="span12" type="text" maxlength="100" value="${customer.name}">
				        </div>
			        </div>
			        <div class="span4">
				        <label class="field_name align_right"><span style="color: red">*</span>客户Code:</label>
				        <div class="field">
					        <input name="code" class="span12" type="text" maxlength="20" value="${customer.code}">
				        </div>
			        </div>
		        </div>

		        <div class="form_row">
			        <div class="span4">
				        <label class="field_name align_right">客户网址:</label>
				        <div class="field">
					        <input name="website" class="span12" type="text" maxlength="100" value="${customer.website}">
				        </div>
			        </div>
			        <div class="span4">
				        <label class="field_name align_right">客户电话:</label>
				        <div class="field">
					        <%--<input name="phone" class="span12" type="text" maxlength="20" value="${customer.phone}">--%>
					        <input id="phone_pre" class="span3" type="text" maxlength="5"> -
					        <input id="phone" class="span8" type="text" maxlength="14">
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
					        <input name="legalPerson" class="span12" type="text" maxlength="64" value="${customer.legalPerson}">
				        </div>
			        </div>
			        <div class="span4">
				        <label class="field_name align_right">法人电话:</label>
				        <div class="field">
					        <%--<input name="legalPhone" class="span12" type="text" maxlength="20" value="${customer.legalPhone}">--%>
					        <input id="legalPhone_pre" class="span3" type="text" maxlength="5"> -
					        <input id="legalPhone" class="span8" type="text" maxlength="14">
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
					        <input name="contactPerson" class="span12" type="text" maxlength="64" value="${customer.contactPerson}">
				        </div>
			        </div>
			        <div class="span4">
				        <label class="field_name align_right">联系人电话:</label>
				        <div class="field">
					        <%--<input name="contactPhone" class="span12" type="text" maxlength="20" value="${customer.contactPhone}">--%>
					        <input id="contactPhone_pre" class="span3" type="text" maxlength="5"> -
					        <input id="contactPhone" class="span8" type="text" maxlength="14">
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
							        <option value="${item.id}" <c:if test="${customer.type == item.id}">selected</c:if>>${item.name}</option>
						        </c:forEach>
					        </select>
				        </div>
			        </div>

			        <div class="span4">
				        <label class="field_name align_right">客户信用:</label>
				        <div class="field">
					        <select name="creditStatus">
						        <c:forEach items="${creditStatus}" var="item">
							        <option value="${item.id}" <c:if test="${customer.creditStatus == item.id}">selected</c:if>>${item.name}</option>
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
					        <input name="contractName" class="span12" type="text" maxlength="64" value="${customer.contractName}">
				        </div>
			        </div>
			        <div class="span4">
				        <label class="field_name align_right">合同有效期:</label>
				        <div class="field">
					        <input name="contractFrom" type="text" class="datetimepicker" style="width:135px;" value='<fmt:formatDate value="${customer.contractFrom}" pattern="yyyy-MM-dd"/>' readonly> —
					        <input name="contractTo" type="text" class="datetimepicker" style="width:135px;" value='<fmt:formatDate value="${customer.contractTo}" pattern="yyyy-MM-dd"/>' readonly>
				        </div>
			        </div>
		        </div>

		        <div class="form_row">
			        <div class="span4">
				        <label class="field_name align_right" style="width: 68px;">合同位置:</label>
				        <div class="field">
					        <input name="contractPosition" class="span12" type="text" maxlength="100" value="${customer.contractPosition}">
				        </div>
			        </div>
			        <div class="span4">
				        <label class="field_name align_right">合同金额:</label>
				        <div class="field">
					        <input name="contractAmount" class="span12" type="text" maxlength="13" value="${customer.contractAmount}">
				        </div>
			        </div>
		        </div>

		        <div class="form_row">
			        <div class="span8" >
				        <label class="field_name align_right" style="width: 67px;">合同条款:</label>
				        <div class="field" style="margin-left: 75px;">
					        <textarea name="contractInfo" style="resize:none;width: 100%;height: 150px;">${customer.contractInfo}</textarea>
				        </div>
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6" style="margin-left: 200px;">
			        <div class="field">
				        <a href="javascript:Customer.update();" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
				        <a href="javascript:App.goToPage(appCtx+'/customer')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
			        </div>
		        </div>
	        </div>
            
        </form>
        </div>
    </div>
</div>
<script>
    jQuery(function ($) {
	    var phone = '${customer.phone}';
        var legalPhone = '${customer.legalPhone}';
        var contactPhone = '${customer.contactPhone}';
        var p1 = splitPhoneStr(phone);
        var p2 = splitPhoneStr(legalPhone);
        var p3 = splitPhoneStr(contactPhone);
        $("#phone_pre").val(p1[0]);
        $("#phone").val(p1[1]);
        $("#legalPhone_pre").val(p2[0]);
        $("#legalPhone").val(p2[1]);
        $("#contactPhone_pre").val(p3[0]);
        $("#contactPhone").val(p3[1]);
    });
</script>
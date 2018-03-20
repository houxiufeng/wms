<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="well light_gray">
	<div class="top_bar">
		<ul class="breadcrumb">
			<li><a href="javascript:void(0);">供应商中心</a> <span class="divider">/</span></li>
			<li><a href="javascript:void(0)">供应商管理</a><span class="divider">/</span></li>
			<li class="active">添加供应商</li>
		</ul>
	</div>

	<div class="well-content">
		<form id="vendorForm">

			<input type="hidden" name="id" value="${vendor.id}">
			<div class="form_row">
				<div class="span6">
					<label class="field_name align_left" style="font-weight: bold;font-size: 14px;">基本信息</label>
				</div>
			</div>

			<div class="form_row">
				<div class="span6">
					<label class="field_name align_right"><span style="color: red">*</span>供应商名称:</label>
					<div class="field">
						<input name="name" class="span12" type="text" maxlength="64" value="${vendor.name}">
					</div>
				</div>
				<div class="span6">
					<label class="field_name align_right"><span style="color: red">*</span>供应商编码:</label>
					<div class="field">
						<input name="code" class="span12" type="text" maxlength="3" value="${vendor.code}">
					</div>
				</div>
			</div>

			<div class="form_row">
				<div class="span6">
					<label class="field_name align_right">供应商备注:</label>
					<div class="field">
						<input name="remark" class="span12" type="text" maxlength="100" value="${vendor.remark}">
					</div>
				</div>
				<div class="span6">
					<label class="field_name align_right">供应商电话:</label>
					<div class="field">
						<input name="phone" class="span12" type="text" maxlength="20" value="${vendor.phone}">
					</div>
				</div>
			</div>

			<div class="form_row">
				<div class="span6">
					<label class="field_name align_right">供应商头像:</label>
					<div class="field">
						<input type="file" id="upAvator" style="margin-top: 5px;"/>
						<input type="button" value="上传" onclick="javascript:uploadVendorAvator()"/>
					</div>
				</div>
				<div class="span6">
					<label class="field_name align_right">&nbsp;</label>
					<div class="field">
						<input type="hidden" name="avator" value="${vendor.avator}">
						<c:if test="${vendor.avator != null and vendor.avator !=''}">
							<img src="${vendor.avator}" style="width: 100px;height: 100px;"/>
						</c:if>
						<c:if test="${vendor.avator == null or vendor.avator ==''}">
							<img src="" style="width: 100px;height: 100px; display: none"/>
						</c:if>
					</div>
				</div>
			</div>

			<div class="form_row">
				<div class="span6">
					<label class="field_name align_left" style="font-weight: bold;font-size: 14px;">其他情况</label>
				</div>
			</div>

			<div class="form_row">
				<div class="span6">
					<label class="field_name align_right">供应商级别:</label>
					<div class="field">
						<select name="level" class="span12">
							<c:forEach items="${vendorLevels}" var="item">
								<option value="${item.id}" <c:if test="${vendor.level == item.id}">selected</c:if>>${item.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="span6">
					<label class="field_name align_right">关联用户:</label>
					<div class="field">
						<select name="userId" class="span12">
							<c:forEach items="${users}" var="item">
							<option value="${item.id}" <c:if test="${item.id == vendor.userId}">selected</c:if>>${item.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>

			<div class="form_row">
				<div class="span6">
					<label class="field_name align_right">维修能力:</label>
					<div class="field" style="margin-top: 5px;">
						<c:forEach items="${maintainSkills}" var="item">
							<input type="checkbox" value="${item.id}" name="skill">${item.name}
						</c:forEach>
					</div>
				</div>
			</div>

			<div class="form_row">
				<div class="span6">
					<label class="field_name align_left" style="font-weight: bold;font-size: 14px;">上岗情况</label>
				</div>
			</div>

			<div class="form_row">
				<div class="span6">
					<label class="field_name align_right">岗位:</label>
					<div class="field">
						<select name="status" class="span12">
							<option value="1" <c:if test="${vendor.status == 1}">selected</c:if>>启用</option>
							<option value="0" <c:if test="${vendor.status == 0}">selected</c:if>>闲置</option>
						</select>
					</div>
				</div>
				<div class="span6">
					<label class="field_name align_right">累计评分:</label>
					<div class="field" style="margin-top: 5px;">
						<span style="color: green">好评</span>:${vendor.goodScore}
						<span style="color: darkslategrey;">中评</span>:${vendor.moderateScore}
						<span style="color:red">差评</span>:${vendor.badScore}
					</div>
				</div>
			</div>

			<div class="form_row">
				<div class="span6" >
					<div class="field">
						<a href="javascript:Vendor.update();" class="btn red">提交</a>
						<a href="javascript:App.goToPage(appCtx+'/vendor')" class="btn light_blue">取消</a>
					</div>
				</div>
			</div>

		</form>
	</div>
</div>
<script>
	var skill = "${vendor.skill}";
	jQuery.each(skill.split(","),function (index, value) {
        jQuery("#vendorForm").find(":checkbox[name='skill'][value='" + value + "']").attr("checked","checked");
    });
</script>
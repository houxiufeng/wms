<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="well light_gray">
	<div class="top_bar">
		<ul class="breadcrumb">
			<li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">My engineer team</a> <span class="divider">/</span></li>
			<li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">Engineer management</a><span class="divider">/</span></li>
			<li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">Add engineer</li>
		</ul>
	</div>

	<div class="well-content">
		<form id="engineerForm">

			<input type="hidden" name="id" value="${engineer.id}">
			<div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
				<div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
					<label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Basic info</label>
				</div>
				<div class="form_row">
					<div class="span6">
						<label class="field_name align_right"><span style="color: red">*</span>Name:</label>
						<div class="field">
							<input name="name" class="span12" type="text" maxlength="64" value="${engineer.name}">
						</div>
					</div>
					<div class="span6">
						<label class="field_name align_right"><span style="color: red">*</span>Code:</label>
						<div class="field">
							<input name="code" class="span12" type="text" maxlength="3" value="${engineer.code}">
						</div>
					</div>
				</div>

				<div class="form_row">
					<div class="span6">
						<label class="field_name align_right">Remarks:</label>
						<div class="field">
							<input name="remark" class="span12" type="text" maxlength="100" value="${engineer.remark}">
						</div>
					</div>
					<div class="span6">
						<label class="field_name align_right">Phone:</label>
						<div class="field">
							<%--<input name="phone" class="span12" type="text" maxlength="20" value="${engineer.phone}">--%>
							<input id="phone_pre" class="span3" type="text" maxlength="5"> -
							<input id="phone" class="span8" type="text" maxlength="14">
						</div>
					</div>
				</div>

				<div class="form_row">
					<div class="span6">
						<label class="field_name align_right">Avator:</label>
						<div class="field">
							<input type="file" id="upAvator" style="margin-top: 5px;"/>
							<input type="button" value="Upload" onclick="javascript:uploadEngineerAvator()"/>
						</div>
					</div>
					<div class="span6">
						<label class="field_name align_right">&nbsp;</label>
						<div class="field">
							<input type="hidden" name="avator" value="${engineer.avator}">
							<c:if test="${engineer.avator != null and engineer.avator !=''}">
								<img src="${engineer.avator}" style="width: 100px;height: 100px;"/>
							</c:if>
							<c:if test="${engineer.avator == null or engineer.avator ==''}">
								<img src="" style="width: 100px;height: 100px; display: none"/>
							</c:if>
						</div>
					</div>
				</div>
			</div>

			<div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
				<div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
					<label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Others</label>
				</div>
				<div class="form_row">
					<div class="span6">
						<label class="field_name align_right">Engineer degree:</label>
						<div class="field">
							<select name="level" class="span12">
								<c:forEach items="${engineerLevels}" var="item">
									<option value="${item.id}" <c:if test="${engineer.level == item.id}">selected</c:if>>${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="span6">
						<label class="field_name align_right">Bind user:</label>
						<div class="field">
							<select name="userId" class="span12">
								<c:forEach items="${users}" var="item">
									<option value="${item.id}" <c:if test="${item.id == engineer.userId}">selected</c:if>>${item.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>

				<div class="form_row">
					<div class="span6">
						<label class="field_name align_right">Capability:</label>
						<div class="field" style="margin-top: 5px;">
							<c:forEach items="${maintainSkills}" var="item">
								<input type="checkbox" value="${item.id}" name="skill">${item.name}
							</c:forEach>
						</div>
					</div>
				</div>
			</div>

			<div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
				<div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
					<label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Work info</label>
				</div>
				<div class="form_row">
					<div class="span6">
						<label class="field_name align_right">Status:</label>
						<div class="field">
							<select name="status" class="span12">
								<option value="1" <c:if test="${engineer.status == 1}">selected</c:if>>On</option>
								<option value="0" <c:if test="${engineer.status == 0}">selected</c:if>>Off</option>
							</select>
						</div>
					</div>
					<div class="span6">
						<label class="field_name align_right">Cumulative score:</label>
						<div class="field" style="margin-top: 5px;">
							<span style="color: green">Good</span>:${engineer.goodScore}
							<span style="color: darkslategrey;">Moderate</span>:${engineer.moderateScore}
							<span style="color:red">Bad</span>:${engineer.badScore}
						</div>
					</div>
				</div>
			</div>

			<div class="form_row">
				<div class="span6" >
					<div class="field">
						<a href="javascript:Engineer.update();" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
						<a href="javascript:App.goToPage(appCtx+'/engineer')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
					</div>
				</div>
			</div>

		</form>
	</div>
</div>
<script>
	var skill = "${engineer.skill}";
	jQuery.each(skill.split(","),function (index, value) {
        jQuery("#engineerForm").find(":checkbox[name='skill'][value='" + value + "']").attr("checked","checked");
    });
    var phone = '${engineer.phone}';
    var p = splitPhoneStr(phone);
    jQuery("#phone_pre").val(p[0]);
    jQuery("#phone").val(p[1]);
</script>
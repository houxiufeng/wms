<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="span12">
    
    <div class="well light_gray">
	    <div class="top_bar">
		    <ul class="breadcrumb">
			    <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">系统管理</a> <span class="divider">/</span></li>
			    <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">平台客户</a><span class="divider">/</span></li>
			    <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">编辑平台客户</li>
		    </ul>
	    </div>

        <div class="well-content">
        <form id="organizationForm">
            
            <input type="hidden" name="id" value="${organization.id}" />
	        <div class="form_row">
		        <div class="span6">
			        <label class="field_name align_right">名称:</label>
			        <div class="field">
				        <input name="name" class="span12" type="text" maxlength="30" value="${organization.name}">
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6">
			        <label class="field_name align_right">角色:</label>
			        <div class="field">
				        <c:forEach items="${roles}" var="item">
					        <label class="checkbox">
						        <input type="checkbox" class="uniform" name="roleIds" value="${item.id}"> ${item.name}
					        </label>
				        </c:forEach>
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6" >
			        <label class="field_name align_right">状态：</label>
			        <div class="field">
				        <label class="radio">
					        <input type="radio" class="uniform" name="status" value="1" <c:if test="${organization.status == 1}">checked="checked"</c:if>> 启用
				        </label>
				        <label class="radio">
					        <input type="radio" class="uniform" name="status" value="0" <c:if test="${organization.status == 0}">checked="checked"</c:if>> 禁用
				        </label>
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6" >
			        <label class="field_name align_right">备注：</label>
			        <div class="field">
				        <textarea name="remark" cols="80" rows="10" style="resize:none" maxlength="60">${organization.remark}</textarea>
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6" >
			        <div class="field">
				        <a href="javascript:Organization.update();" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
				        <a href="javascript:App.goToPage(appCtx+'/organization')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
			        </div>
		        </div>
	        </div>
            
        </form>
        </div>
    </div>
</div>
<script>
	var myRoleIds = [];
    if (!_isNull("${organization.roleIds}")) {
        myRoleIds = "${organization.roleIds}".split(",");
        for (var i =0; i < myRoleIds.length; i++) {
            jQuery(":checkbox[name='roleIds'][value='" + myRoleIds[i] +"']").attr("checked",true);
        }
    }
</script>
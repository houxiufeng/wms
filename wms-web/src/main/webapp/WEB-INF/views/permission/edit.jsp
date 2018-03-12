<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="span12">
    
    <div class="well light_gray">
	    <div class="top_bar">
		    <ul class="breadcrumb">
			    <li><a href="javascript:void(0);">系统管理</a> <span class="divider">/</span></li>
			    <li><a href="javascript:void(0)">权限信息</a><span class="divider">/</span></li>
			    <li class="active">编辑权限</li>
		    </ul>
	    </div>

        <div class="well-content">
        <form id="permissionForm">
            
            <input type="hidden" name="id" value="${permission.id}" />
            <div class="form_row">
                <div class="span4">
                    <label class="field_name align_right">权限名称:</label>
	                <div class="field">
	                    <input name="name" class="span12" type="text" value="${permission.name }">
	                </div>
                </div>
            </div>

	        <div class="form_row">
		        <div class="span4">
			        <label class="field_name align_right">URL:</label>
			        <div class="field">
				        <input name="url" class="span12" type="text" value="${permission.url}">
			        </div>
		        </div>
	        </div>


	        <div class="form_row">
		        <div class="span4">
			        <label class="field_name align_right">父节点:</label>
			        <div class="field">
				        <select name="pid" class="span12">
					        <option value="0">无</option>
					        <c:forEach items="${permissions}" var="item">
						        <option value="${item.id}" <c:if test="${permission.pid == item.id}">selected</c:if>>${item.name}</option>
					        </c:forEach>
				        </select>
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span4">
			        <label class="field_name align_right">是否菜单:</label>
			        <div class="field">
				        <label class="radio">
					        <input type="radio" class="uniform" name="menuFlag" value="1" <c:if test="${permission.menuFlag == 1}">checked="checked"</c:if>> 是&nbsp;&nbsp;&nbsp;&nbsp;
				        </label>
				        <label class="radio">
					        <input type="radio" class="uniform" name="menuFlag" value="0" <c:if test="${permission.menuFlag == 0}">checked="checked"</c:if>> 否
				        </label>
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span4" >
			        <label class="field_name align_right">状态:</label>
			        <div class="field">
				        <label class="radio">
					        <input type="radio" class="uniform" name="status" value="1" <c:if test="${permission.status == 1}">checked="checked"</c:if>> 启用
				        </label>
				        <label class="radio">
					        <input type="radio" class="uniform" name="status" value="0" <c:if test="${permission.status == 0}">checked="checked"</c:if>> 禁用
				        </label>
			        </div>
		        </div>
	        </div>
            
            <div class="form_row">
                <div class="span4" >
	                <div class="field">
	                    <a id="updateUser" href="javascript:Permission.update();" class="btn red">提交</a>
	                    <a href="javascript:App.goToPage(appCtx+'/permission')" class="btn light_blue">取消</a>
	                </div>
                </div>
            </div>
            
            </form>
        </div>
    </div>
</div>
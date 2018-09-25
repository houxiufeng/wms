<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="span12">
    
    <div class="well light_gray">
	    <div class="top_bar">
		    <ul class="breadcrumb">
			    <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">System setting</a> <span class="divider">/</span></li>
			    <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">User management</a><span class="divider">/</span></li>
			    <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">Edit User</li>
		    </ul>
	    </div>

        <div class="well-content">
        <form id="userForm">
            
            <input type="hidden" name="id" value="${user.id}" />

	        <div class="form_row">
		        <div class="span4">
			        <label class="field_name align_right">Role:</label>
			        <div class="field">
				        <select name="roleId" class="span12">
					        <c:forEach items="${roles}" var="item">
						        <option value="${item.id}" <c:if test="${user.roleId == item.id}">selected</c:if>>${item.name}</option>
					        </c:forEach>
				        </select>
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span4">
			        <label class="field_name align_right">Email:</label>
			        <div class="field">
				        <input name="email" class="span12" type="text" value="${user.email }">
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
                <div class="span4">
                    <label class="field_name align_right">Name:</label>
	                <div class="field">
	                    <input name="name" class="span12" type="text" value="${user.name }">
	                </div>
                </div>
            </div>

	        <c:if test="${CURRENT_USER.roleCode == 'admin'}">
		        <div class="form_row">
			        <div class="span4">
				        <label class="field_name align_right">Organization:</label>
				        <div class="field">
					        <select name="organizationId" class="span12">
						        <c:forEach items="${organizations}" var="item">
							        <option value="${item.id}" <c:if test="${user.organizationId == item.id}">selected</c:if>>${item.name}</option>
						        </c:forEach>
					        </select>
				        </div>
			        </div>
		        </div>
	        </c:if>

	        <div class="form_row">
                <div class="span4">
                    <label class="field_name align_right">Address:</label>
	                 <div class="field">
	                     <input name="address" class="span12" type="text" value="${user.address }">
	                 </div>
                </div>
            </div>
            
            <div class="form_row">
                <div class="span4" >
                    <label class="field_name align_right">Remarks：</label>
	                 <div class="field">
	                     <textarea id="description" name="description" cols="80" rows="10" style="resize:none" >${user.description }</textarea>
	                 </div>
                </div>
            </div>
            
            <div class="form_row">
                <div class="span4" >
	                <div class="field">
	                    <a id="updateUser" title="save" href="javascript:void(0);" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
	                    <a title="back" href="javascript:App.goToPage('user')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
	                </div>
                </div>
            </div>
            
            </form>
        </div>
    </div>
</div>

<script>
jQuery(function($){
	$("#updateUser").click(function(){
		User.validateUser(function(){
			$.ajax({
				url: "user/update/",
				type: 'post',
				data: $("#userForm").serialize(),
				dataType: "json",
				success: function(json) {
					if (json.code == "0") {
						App.alert("success!", function(){
							App.goToPage("user");
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
	});
})
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray span12">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">System setting</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">Role management</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">Permission setting</li>
        </ul>
    </div>

    <div class="well-content">
        <%--<p style="font-weight: 500; font-size: 14px;">${roleName}</p>--%>
        <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
            <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">${roleName}</label>
        </div>
        <input id="roleId" type="hidden" value="${roleId}">
        <ul style="list-style-type:none;">
            <c:forEach items="${permissionTrees}" var="permissionTree">
                <li>
                    <input type="checkbox" value="${permissionTree.treeNode.id}" class="level1" style="display: inline">
                    ${permissionTree.treeNode.name}
                    <c:if test="${permissionTree.subNodes != null and permissionTree.subNodes.size() > 0}">
                        <ul style="list-style-type:none;">
                            <c:forEach items="${permissionTree.subNodes}" var="subNode">
                                <li><input type="checkbox" value="${subNode.id}" class="level2" style="display: inline">${subNode.name}</li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </li>

            </c:forEach>
        </ul>
        <div class="form_row" style="margin-top: 10px;">
            <div class="span10" >
                <div class="field">
                    <a id="saveRolePermission" href="javascript:void(0);" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
                    <a href="javascript:App.goToPage(appCtx+'/role');" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
                </div>
            </div>
        </div>
    </div>


</div>

<script>
jQuery(function($){
    $(".level1").click(function () {
        if ($(this).is(':checked')) {
            $(this).closest("li").find(".level2").prop("checked","checked");
        } else {
            $(this).closest("li").find(".level2").removeAttr("checked");
        }
    });
    $(".level2").click(function () {
        if ($(this).closest("ul").find(".level2:checked").length == 0) {
            $(this).closest("ul").siblings('.level1').removeAttr("checked");
        } else if ($(this).closest("ul").find(".level2:checked").length != 0) {
            $(this).closest("ul").siblings('.level1').prop("checked","checked");
        }
    });

    $("#saveRolePermission").click(function () {
        var permissionIds = [];
        $("input:checked").each(function () {
            permissionIds.push($(this).val());
        });
        if (permissionIds.length == 0) {
            App.alert("please select one!");
            return;
        }
        var data = {};
        data.permissionIds = permissionIds;
        data.roleId = $("#roleId").val();
        $.ajax({
            url: appCtx + "/role/editRolePermissions",
            type: 'post',
            data: data,
            dataType:'json',
            success: function(json) {
                if (json.code == "0") {
                    App.alert("success!", function(){
                        App.goToPage(appCtx+"/role");
                    });
                } else {
                    App.alert(json.message);
                }
            },
            error: function(xhr, textStatus, errorThrown){
                App.alert(errorThrown);
            }
        });
    });

    //设置以选中的权限
    var rolePermissionsStr = '${rolePermissions}';
    if (rolePermissionsStr && rolePermissionsStr.length > 0) {
        var arr = JSON.parse(rolePermissionsStr);
        for (var i = 0; i < arr.length; i++) {
            var rolePermission = arr[i];
            $(':checkbox[value="' + rolePermission['permissionId'] + '"]').prop("checked","checked");
        }
    }
});
</script>
    
    

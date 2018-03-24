<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">系统管理</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">权限信息</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">添加权限</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="permissionForm">

            <div class="form_row">
                <div class="span4">
                    <label class="field_name align_right">权限名称:</label>
                    <div class="field">
                        <input name="name" class="span12" type="text">
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span4">
                    <label class="field_name align_right">URL:</label>
                    <div class="field">
                        <input name="url" class="span12" type="text">
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
                                <option value="${item.id}">${item.name}</option>
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
                            <input type="radio" class="uniform" name="menuFlag" value="1" checked="checked"> 是
                        </label>
                        <label class="radio">
                            <input type="radio" class="uniform" name="menuFlag" value="0"> 否
                        </label>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span4" >
                    <label class="field_name align_right">状态：</label>
                    <div class="field">
                        <label class="radio">
                            <input type="radio" class="uniform" name="status" value="1" checked="checked"> 启用
                        </label>
                        <label class="radio">
                            <input type="radio" class="uniform" name="status" value="0"> 禁用
                        </label>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span4" >
                    <div class="field">
                        <a href="javascript:Permission.save();" class="btn red btn-large" style="width: 60px;">提交</a>
                        <a href="javascript:App.goToPage(appCtx+'/permission')" class="btn dark_green btn-large" style="width: 60px;">取消</a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

    

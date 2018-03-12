<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);">系统管理</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)">平台客户</a><span class="divider">/</span></li>
            <li class="active">添加平台客户</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="organizationForm">

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right">名称:</label>
                    <div class="field">
                        <input name="name" class="span12" type="text" maxlength="30">
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
                            <input type="radio" class="uniform" name="status" value="1" checked="checked"> 启用
                        </label>
                        <label class="radio">
                            <input type="radio" class="uniform" name="status" value="0"> 禁用
                        </label>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6" >
                    <label class="field_name align_right">备注：</label>
                    <div class="field">
                        <textarea name="remark" cols="80" rows="10" style="resize:none" maxlength="60"></textarea>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6" >
                    <div class="field">
                        <a href="javascript:Organization.save();" class="btn red">提交</a>
                        <a href="javascript:App.goToPage(appCtx+'/organization')" class="btn light_blue">取消</a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

    

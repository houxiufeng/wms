<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">客户中心</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">分支管理</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">添加分支</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="branchForm">

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>客户名称:</label>
                    <div class="field">
                        <select name="customerId" class="span12">
                            <c:forEach items="${customers}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>总/分店名称:</label>
                    <div class="field">
                        <input name="name" class="span12" type="text" maxlength="100">
                    </div>
                </div>
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>总/分店编码:</label>
                    <div class="field">
                        <input name="code" class="span12" type="text" maxlength="3">
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>联系人名称:</label>
                    <div class="field">
                        <input name="contactPerson" class="span12" type="text" maxlength="64">
                    </div>
                </div>
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>联系人电话:</label>
                    <div class="field">
                        <input name="contactPhone" class="span12" type="text" maxlength="32">
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right"><span style="color: red">*</span>总/分店地址:</label>
                    <div class="field">
                        <input name="address" class="span12" type="text" maxlength="128">
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6" >
                    <div class="field">
                        <a href="javascript:Branch.save();" class="btn red btn-large" style="width: 60px;">提交</a>
                        <a href="javascript:App.goToPage(appCtx+'/branch')" class="btn dark_green btn-large" style="width: 60px;">取消</a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

    

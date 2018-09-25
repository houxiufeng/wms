<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">System setting</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">Permission management</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">Add permission</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="permissionForm">

            <div class="form_row">
                <div class="span4">
                    <label class="field_name align_right">Name:</label>
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
                    <label class="field_name align_right">Parent:</label>
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
                    <label class="field_name align_right">IsMenu:</label>
                    <div class="field">
                        <label class="radio">
                            <input type="radio" class="uniform" name="menuFlag" value="1" checked="checked"> Yes
                        </label>
                        <label class="radio">
                            <input type="radio" class="uniform" name="menuFlag" value="0"> No
                        </label>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span4" >
                    <label class="field_name align_right">Status：</label>
                    <div class="field">
                        <label class="radio">
                            <input type="radio" class="uniform" name="status" value="1" checked="checked"> On
                        </label>
                        <label class="radio">
                            <input type="radio" class="uniform" name="status" value="0"> Off
                        </label>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span4" >
                    <div class="field">
                        <a title="save" href="javascript:Permission.save();" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
                        <a title="back" href="javascript:App.goToPage(appCtx+'/permission')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

    

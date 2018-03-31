<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">System setting</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">Role management</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">Add role</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="roleForm">

            <div class="form_row">
                <div class="span4">
                    <label class="field_name align_right">Role:</label>
                    <div class="field">
                        <input name="name" class="span12" type="text" maxlength="30">
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span4">
                    <label class="field_name align_right">Code:</label>
                    <div class="field">
                        <input name="code" class="span12" type="text" maxlength="30">
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
                    <label class="field_name align_right">Remarks：</label>
                    <div class="field">
                        <textarea name="remark" cols="80" rows="10" style="resize:none" maxlength="60"></textarea>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span4" >
                    <div class="field">
                        <a href="javascript:Role.save();" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
                        <a href="javascript:App.goToPage(appCtx+'/role')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

    

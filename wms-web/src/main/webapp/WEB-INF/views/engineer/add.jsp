<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">My engineer team</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">Engineer management</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">add engineer</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="engineerForm">

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Basic info</label>
                </div>
                <div class="form_row">
                    <div class="span6">
                        <label class="field_name align_right"><span style="color: red">*</span>Name:</label>
                        <div class="field">
                            <input name="name" class="span12" type="text" maxlength="64">
                        </div>
                    </div>
                    <div class="span6">
                        <label class="field_name align_right"><span style="color: red">*</span>Code:</label>
                        <div class="field">
                            <input name="code" class="span12" type="text" maxlength="3">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span6">
                        <label class="field_name align_right">Remarks:</label>
                        <div class="field">
                            <input name="remark" class="span12" type="text" maxlength="100">
                        </div>
                    </div>
                    <div class="span6">
                        <label class="field_name align_right">Phone:</label>
                        <div class="field">
                            <%--<input name="phone" class="span12" type="text" maxlength="20">--%>
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
                            <input type="hidden" name="avator">
                            <img src="" style="width: 100px;height: 100px; display: none"/>
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
                        <label class="field_name align_right">Engineer qualification:</label>
                        <div class="field">
                            <select name="level" class="span12">
                                <c:forEach items="${engineerLevels}" var="item">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="span6">
                        <label class="field_name align_right">Bind user:</label>
                        <div class="field">
                            <select name="userId" class="span12">
                                <c:forEach items="${users}" var="item">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

                <%--<div class="form_row">--%>
                    <%--<div class="span6">--%>
                        <%--<label class="field_name align_right">Capability:</label>--%>
                        <%--<div class="field" style="margin-top: 5px;">--%>
                            <%--<c:forEach items="${maintainSkills}" var="item">--%>
                                <%--<input type="checkbox" value="${item.id}" name="skill">${item.name}--%>
                            <%--</c:forEach>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
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
                                <option value="1">On</option>
                                <option value="0">Off</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6" >
                    <div class="field">
                        <a href="javascript:Engineer.save();" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
                        <a href="javascript:App.goToPage(appCtx+'/engineer')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

    

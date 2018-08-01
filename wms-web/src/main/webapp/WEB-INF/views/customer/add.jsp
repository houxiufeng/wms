<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">My customer</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">Customer management</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">Add customer</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="customerForm">

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Basic Info</label>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Name:</label>
                        <div class="field">
                            <input name="name" class="span12" type="text" maxlength="100">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Code:</label>
                        <div class="field">
                            <input name="code" class="span12" type="text" maxlength="3">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right">Website:</label>
                        <div class="field">
                            <input name="website" class="span12" type="text" maxlength="100">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">Phone number:</label>
                        <div class="field">
                            <input id="phone_pre" class="span3" type="text" maxlength="5"> -
                            <input id="phone" class="span8" type="text" maxlength="14">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right">Registration No:</label>
                        <div class="field">
                            <input name="registNo" class="span12" type="text" maxlength="60">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">Group:</label>
                        <div class="field">
                            <input name="groups" class="span12" type="text" maxlength="60">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right">Remarks:</label>
                        <div class="field">
                            <textarea name="remark" cols="80" rows="5" style="resize:none" maxlength="255"></textarea>
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">Logo:</label>
                        <div class="field">
                            <input type="file" id="upImg" style="margin-top: 5px;"/>
                            <input type="button" value="Upload" onclick="javascript:Customer.uploadLogo()"/>
                            <img id="logo" src="" style="width: 60px;height: 60px; display: none">
                        </div>
                    </div>
                </div>

            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Legal person info</label>
                </div>
                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Legal person name:</label>
                        <div class="field">
                            <input name="legalPerson" class="span12" type="text" maxlength="64">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">Legal person phone:</label>
                        <div class="field">
                            <%--<input name="legalPhone" class="span12" type="text" maxlength="20">--%>
                            <input id="legalPhone_pre" class="span3" type="text" maxlength="5"> -
                            <input id="legalPhone" class="span8" type="text" maxlength="14">
                        </div>
                    </div>
                </div>
            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Contact info</label>
                </div>
                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Contact name:</label>
                        <div class="field">
                            <input name="contactPerson" class="span12" type="text" maxlength="64">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">Contact phone:</label>
                        <div class="field">
                            <%--<input name="contactPhone" class="span12" type="text" maxlength="20">--%>
                            <input id="contactPhone_pre" class="span3" type="text" maxlength="5"> -
                            <input id="contactPhone" class="span8" type="text" maxlength="14">
                        </div>
                    </div>
                </div>
            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Others</label>
                </div>
                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right">Customer reputation:</label>
                        <div class="field">
                            <select name="type">
                                <c:forEach items="${types}" var="item">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="span5">
                        <label class="field_name align_right">Credit:</label>
                        <div class="field">
                            <select name="creditStatus">
                                <c:forEach items="${creditStatus}" var="item">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Contract info</label>
                </div>
                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right" style="width: 68px;"><span style="color: red">*</span>Contract name:</label>
                        <div class="field">
                            <input name="contractName" class="span12" type="text" maxlength="64">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">Contract valid date:</label>
                        <div class="field">
                            <input name="contractFrom" type="text" class="datetimepicker" style="width:135px;" readonly> —
                            <input name="contractTo" type="text" class="datetimepicker" style="width:135px;" readonly>
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right" style="width: 68px;">Contract position:</label>
                        <div class="field">
                            <input name="contractPosition" class="span12" type="text" maxlength="100">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">Contract amount:</label>
                        <div class="field">
                            <input name="contractAmount" class="span12" type="text" maxlength="13">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span8" >
                        <label class="field_name align_right" style="width: 67px;">Contract terms:</label>
                        <div class="field" style="margin-left: 95px;">
                            <textarea name="contractInfo" style="resize:none;width: 100%;height: 80px;"></textarea>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span6" style="margin-left: 200px;">
                    <div class="field">
                        <a href="javascript:Customer.save();" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
                        <a href="javascript:App.goToPage(appCtx+'/customer')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>
<script>
    jQuery(".datetimepicker").datetimepicker({
        format:"yyyy-mm-dd",
        autoclose: true,
        pickTime: false,
        minView: '2',
        todayBtn: true
    });
</script>

    

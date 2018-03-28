<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">产品中心</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">产品管理</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">添加产品</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="productForm">

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">基本信息</label>
                </div>
                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>产品名称:</label>
                        <div class="field">
                            <select id="productName" name="name" class="span10">
                                <c:forEach items="${brands}" var="item">
                                    <option value="${item.name}">${item.name}</option>
                                </c:forEach>
                            </select>
                            <a href="javascript:Product.addBrand();" class="btn dark_green">+</a>
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>产品编码:</label>
                        <div class="field">
                            <input name="code" class="span10" type="text" maxlength="3">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>产品型号:</label>
                        <div class="field">
                            <input name="model" class="span10" type="text" maxlength="32">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">产品类型:</label>
                        <div class="field">
                            <select name="type" class="span10">
                                <option value="1">空调</option>
                                <option value="2">升降机</option>
                                <option value="3">风扇</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5" >
                        <label class="field_name align_right">产品备注:</label>
                        <div class="field">
                            <textarea name="remark" class="span10" style="resize:none;height: 80px;"></textarea>
                        </div>
                    </div>
                </div>
            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">维保人员信息</label>
                </div>
                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>维保人名称:</label>
                        <div class="field">
                            <input name="maintenancePerson" class="span10" type="text" maxlength="64">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>维保人电话:</label>
                        <div class="field">
                            <input name="maintenancePhone" class="span10" type="text" maxlength="32">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right">产品图片:</label>
                        <div class="field">
                            <input type="file" id="upImg" style="margin-top: 5px;"/>
                            <input type="button" value="上传" onclick="javascript:uploadProductImg()"/>
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">产品附件:</label>
                        <div class="field">
                            <input type="file" id="upFile" style="margin-top: 5px;"/>
                            <input type="button" value="上传" onclick="javascript:uploadProductFile()"/>
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right">&nbsp;</label>
                        <div class="field">
                            <table id="productImgTable" border="1" style="display: none">
                                <tr>
                                    <th>图片名称</th>
                                    <th>操作</th>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">&nbsp;</label>
                        <div class="field">
                            <table id="productFileTable" border="1" style="display: none">
                                <tr>
                                    <th>附件名称</th>
                                    <th>操作</th>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form_row">
                <div class="span5" >
                    <div class="field">
                        <a href="javascript:Product.save();" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
                        <a href="javascript:App.goToPage(appCtx+'/product')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
                    </div>
                </div>
            </div>

        </form>
    </div>
</div>

    

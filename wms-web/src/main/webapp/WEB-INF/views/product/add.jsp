<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">My product</a> <span class="divider">/</span></li>
            <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">Product management</a><span class="divider">/</span></li>
            <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">Add product</li>
        </ul>
    </div>

    <div class="well-content">
        <form id="productForm">

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Basic info</label>
                </div>
                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Product brand:</label>
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
                        <label class="field_name align_right"><span style="color: red">*</span>Product code:</label>
                        <div class="field">
                            <input name="code" class="span10" type="text" maxlength="3">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Product model:</label>
                        <div class="field">
                            <input name="model" class="span10" type="text" maxlength="32">
                        </div>
                    </div>
                    <%--<div class="span5">--%>
                        <%--<label class="field_name align_right">Product type:</label>--%>
                        <%--<div class="field">--%>
                            <%--<select name="type" class="span10">--%>
                                <%--<option value="1">Air conditioning</option>--%>
                                <%--<option value="2">Lifter</option>--%>
                                <%--<option value="3">Fan</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="span5">
                        <label class="field_name align_right">Processor:</label>
                        <div class="field">
                            <input name="processor" class="span10" type="text" maxlength="64">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right">Memory:</label>
                        <div class="field">
                            <input name="memory" class="span10" type="text" maxlength="32">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">Hard Drive:</label>
                        <div class="field">
                            <input name="hardDrive" class="span10" type="text" maxlength="32">
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5" >
                        <label class="field_name align_right">Remarks:</label>
                        <div class="field">
                            <textarea name="remark" class="span10" style="resize:none;height: 80px;"></textarea>
                        </div>
                    </div>
                </div>
            </div>

            <div style="border: solid 1px lightgrey;padding-bottom:10px;margin-bottom:10px;">
                <div class="well-header" style="min-height: 35px;margin-bottom: 15px; background-color: #f7f7f7">
                    <label class="field_name align_left" style="font-weight: bold;font-size: 14px;margin:5px;color: #39a77e">Maintenance info</label>
                </div>
                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Maintenance person:</label>
                        <div class="field">
                            <input name="maintenancePerson" class="span10" type="text" maxlength="64">
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Maintenance phone:</label>
                        <div class="field">
                            <%--<input name="maintenancePhone" class="span10" type="text" maxlength="32">--%>
                            <input id="maintenancePhone_pre" class="span3" type="text" maxlength="5"> -
                            <input id="maintenancePhone" class="span7" type="text" maxlength="14">
                        </div>
                    </div>
                </div>
                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Spare-part list:</label>
                        <div class="field" style="margin-top: 5px;">
                            <c:forEach items="${sparePartList}" var="item">
                                <input type="checkbox" value="${item.id}" name="sparePartList">${item.name}
                            </c:forEach>
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right"><span style="color: red">*</span>Check list:</label>
                        <div class="field" style="margin-top: 5px;">
                            <c:forEach items="${checkList}" var="item">
                                <input type="checkbox" value="${item.id}" name="checkList">${item.name}
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right">Product image:</label>
                        <div class="field">
                            <input type="file" id="upImg" style="margin-top: 5px;"/>
                            <input type="button" value="Upload" onclick="javascript:uploadProductImg()"/>
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">Attachment:</label>
                        <div class="field">
                            <input type="file" id="upFile" style="margin-top: 5px;"/>
                            <input type="button" value="Upload" onclick="javascript:uploadProductFile()"/>
                        </div>
                    </div>
                </div>

                <div class="form_row">
                    <div class="span5">
                        <label class="field_name align_right">&nbsp;</label>
                        <div class="field">
                            <table id="productImgTable" border="1" style="display: none">
                                <tr>
                                    <th>Image</th>
                                    <th>Operation</th>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="span5">
                        <label class="field_name align_right">&nbsp;</label>
                        <div class="field">
                            <table id="productFileTable" border="1" style="display: none">
                                <tr>
                                    <th>Attachment</th>
                                    <th>Operation</th>
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

    

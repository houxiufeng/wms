<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);">客户中心</a> <span class="divider">/</span></li>
            <li class="active">维修产品详情</li>
        </ul>
    </div>

    <div class="well-content">

        <div class="table_options top_options">
            <div>
                <span style="font-weight: 600; font-size: 14px;">维修产品情况</span>
            </div>
        </div>
        <div class="span5" style="margin-bottom: 20px;">
            <form id="branchProductForm">
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right">产品名称:</label>
                        <div class="field">
                            <span>${branchProduct.productId}</span>
                        </div>
                    </div>

                </div>
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right">分支机构:</label>
                        <div class="field">
                            <span>${branchProduct.branchId}</span>
                        </div>
                    </div>
                </div>
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right">维修产品位置:</label>
                        <div class="field">
                            <span>${branchProduct.position}</span>
                        </div>
                    </div>
                </div>
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right"><span style="color: red">*</span>产品序列号:</label>
                        <div class="field">
                            <span>${branchProduct.sn}</span>
                        </div>
                    </div>
                </div>

            </form>
        </div>
        <div class="span6">
            <div class="form_row">
                <div class="span12">
                    <label class="field_name align_right" style="width: 9%">poi</label>
                    <div class="field" style="margin-left: 10%">
                        <span>${branchProduct.poi}</span>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>


    

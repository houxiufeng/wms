<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);">My customer</a> <span class="divider">/</span></li>
            <li class="active">Product info</li>
        </ul>
    </div>

    <div class="well-content">

        <div class="table_options top_options">
            <div>
                <span style="font-weight: 600; font-size: 14px;">Product info</span>
            </div>
        </div>
        <div class="span5" style="margin-bottom: 20px;">
            <form id="branchProductForm">
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right">Product name:</label>
                        <div class="field">
                            <span>${branchProduct.productId}</span>
                        </div>
                    </div>

                </div>
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right">Branch name:</label>
                        <div class="field">
                            <span>${branchProduct.branchId}</span>
                        </div>
                    </div>
                </div>
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right">Position:</label>
                        <div class="field">
                            <span>${branchProduct.position}</span>
                        </div>
                    </div>
                </div>
                <div class="form_row">
                    <div class="span12">
                        <label class="field_name align_right"><span style="color: red">*</span>Serial number:</label>
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


    

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="well light_gray">
    <div class="well-content">
        <div class="form_row">
            <div class="span5">
                <label class="field_name align_right"><span style="color: red">*</span>Confirm question:</label>
                <div class="field" style="margin-top: 5px;">
                    <span>${order.typeName}</span>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="span5">
                <label class="field_name align_right"><span style="color: red">*</span>Confirm description:</label>
                <div class="field" style="margin-top: 5px;">
                    <span style="display: inline-block; width: 100%">${order.description}</span>
                </div>
            </div>
        </div>
    </div>
</div>

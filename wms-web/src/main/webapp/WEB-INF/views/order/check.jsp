<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="well light_gray">
    <div class="well-content">
        <div class="form_row">
            <div class="span5">
                <label class="field_name align_right"><span style="color: red">*</span>确认问题:</label>
                <div class="field">
                    <select id="type" name="type" class="span4">
                        <c:forEach items="${types}" var="item">
                            <option value="${item.id}" <c:if test="${item.id == order.type}">selected</c:if>>${item.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="form_row">
            <div class="span5">
                <label class="field_name align_right"><span style="color: red">*</span>确认描述:</label>
                <div class="field">
                    <textarea id="description" style="resize:none;height: 100px;" class="span5">${order.description}</textarea>
                </div>
            </div>
        </div>
    </div>
</div>

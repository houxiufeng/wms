<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="well light_gray">
    <div class="well-content">
        <form id="dictForm" style="margin-bottom: 10px;">
            <div class="form_row">
                <label class="field_name align_right" style="width: 30%"><span style="color: red">*</span>Parameter type:</label>
                <div class="field" style="margin-left: 31%">
                    <select id="type" name="type">
                        <c:forEach items="${dictTypes}" var="item">
                            <option value="${item.value}">${item.desc}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form_row" style="margin-top: 20px;">
                <label class="field_name align_right" style="width: 30%"><span style="color: red">*</span>Parameter name:</label>
                <div class="field" style="margin-left: 31%">
                    <input name="name" type="text" maxlength="32" style="width: 90%">
                </div>
            </div>
        </form>
    </div>
</div>
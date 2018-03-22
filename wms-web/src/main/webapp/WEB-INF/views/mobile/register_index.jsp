<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="margin-top: 40px; padding: 10px;">
    <div class="form_row" style="text-align: center;">
        <span style="font-size: 16px;font-weight: 600">${CURRENT_USER.organizationName}:你好,${CURRENT_USER.name}</span>
    </div>
    <div class="form_row">
        <div class="field">
            <img src="${ctx}/flatpoint/demo/avatar_06.png" style="width: 60%;">
        </div>
    </div>

    <div class="form_row">
        <div class="field">
            <a href="javascript:App.goToPage(appCtx + '/mobile/branch');" class="btn light_blue btn-large" style="font-size: 20px;">首页</a>
            <a href="javascript:App.goToPage(appCtx + '/mobile/branch/list');" class="btn btn-large" style="font-size: 20px;">创建</a>
        </div>
    </div>
</div>

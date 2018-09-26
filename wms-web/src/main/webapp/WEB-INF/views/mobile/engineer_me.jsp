<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
    .form_row {
        margin-bottom: 25px;
    }
    .content {
        min-height: 70vh;
    }
    .footer {
        height: 20vh;
    }
    .content ul li {
        color: white;
        background-color: #0072c6;
    }

</style>

<div class="content" style="margin-top: 40px; padding: 10px; font-size: 20px; margin-left: 10px;">
    <div class="form_row" style="text-align: center;">
        <span style="font-weight: 600">${CURRENT_USER.organizationName}:Hi,${engineer.name}</span>
    </div>

    <div class="well blue">
        <div class="well-content price-table" style="font-size: 13px;">
            <div>
                <ul>
                    <li style="line-height:30px;"><span>User name: ${user.name}</span><a id="resetPwd" href="javascript:void(0)" class="btn dark_green" style="float: right">Change password</a></li>
                    <li style="line-height:30px;">Name: ${engineer.name}</li>
                    <li style="line-height:30px;">Engineer code: ${engineer.code}</li>
                </ul>

            </div>
        </div>
        <div class="well-content price-table" style="font-size: 13px;margin-top: 40px;">
            <div>
                <ul>
                    <li style="line-height:30px;">Engineer degree: ${engineer.levelName}</li>
                    <li style="line-height:30px;">Capability:</li>
                    <li style="line-height:30px;">
                        <c:forEach items="${engineer.skillList}" var="item" varStatus="status">
                            ${status.index + 1}.${item}<br/>
                        </c:forEach>
                    </li>
                </ul>
            </div>
        </div>

    </div>

</div>
<div class="footer">
    <div style="margin-left: 10px;">
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Home <i class="icon-home"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer/report');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Report <i class="icon-table"></i></a>
        <a href="javascript:App.goToPage(appCtx + '/mobile/engineer/me');" class="btn blue" style="font-size: 22px; line-height: 80px; width: 26%">Me <i class="icon-user"></i></a>
    </div>
</div>
<script>
    jQuery("#resetPwd").click(function () {
        jQuery.confirm({
            keyboardEnabled: true,
            title: 'Reset password!',
            confirmButton: 'OK',
            confirmButtonClass: 'btn red',
            cancelButton: 'CLOSE',
            content: '<div>New Password：<input type="text" id="newPwd" maxlength="20" value=""></div>',
            confirm: function(){
                var newPwd = jQuery.trim(jQuery("#newPwd").val());
                if (!newPwd) {
                    alert("password can't empty!");
                    return false;
                }
                if (!/^[a-zA-Z0-9_\.]+$/.test(newPwd)) {
                    alert("invalid password, should a-zA-Z0-9_.");
                    return false;
                }
                var pwdMd5 = CryptoJS.MD5(newPwd).toString();
                jQuery.ajax({
                    url: appCtx + "/user/resetPwd",
                    type: 'post',
                    dataType:'json',
                    data:{"id":${user.id}, "password":pwdMd5},
                    success: function(json) {
                        if (json.code == "0") {
                            alert("success！");
                        } else {
                            alert(json.message);
                        }
                    },
                    error: function(xhr, textStatus, errorThrown){
                        alert(errorThrown);
                    }
                });
            }
        });
        setTimeout(function () {//因为用vh所以alert组件高度出问题了，这里是用暂时的方式修改高度。
            jQuery("div.content-pane").css("height","100px");
            jQuery("div.content").css("clip","rect(0px, 383px, 100px, -100px)");
        },200);
    });
</script>

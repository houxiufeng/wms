<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>wms</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <c:set var="flatpointPath" value="${pageContext.request.contextPath}/flatpoint"/>
    <link href="${flatpointPath}/css/bootstrap.css" rel="stylesheet">
    <link href="${flatpointPath}/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="${flatpointPath}/css/stylesheet.css" rel="stylesheet">
    <link href="${flatpointPath}/icon/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/bootstrap.extend.css" rel="stylesheet">
    <link href="${ctx}/css/jquery-confirm.min.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="${flatpointPath}/js/html5shiv.js"></script>
    <![endif]-->
  </head>

  <body style="background-image: url('${flatpointPath}/demo/slide_04.jpg');background-repeat:no-repeat; background-size:100% 100%;-moz-background-size:100% 100%;">

    <div class="login-container">
        <div class="login-header blue">
            <h4>WMS</h4>
        </div>
        <form id="loginForm">
            <div class="login-field">
                <label for="email">Email</label>
                <input type="text" name="email" id="email" placeholder="Email">
                <i class="icon-user"></i>
            </div>
            <div class="login-field">
                <label for="password">Password</label>
                <input type="password" name="password" id="password" placeholder="Password">
                <i class="icon-lock"></i>
            </div>
            <div class="login-button">
                <a id="loginBtn" class="btn btn-large btn-block blue">Login <i class="icon-arrow-right"></i></a>
            </div>
        </form>
    </div>



    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${flatpointPath}/js/jquery-1.10.2.js"></script>
    <script src="${flatpointPath}/js/library/jquery.validate.min.js"></script>
    <script src="${ctx}/js/jquery-confirm.min.js"></script>
    <script src="${ctx}/js/crypto-js.js"></script>
    <script src="${ctx}/js/app.js"></script>
    <script>
	    $(function(){
	    	$("#loginBtn").click(function(){
		   		var params = {};
		   		params.rules = {
		   				email: {
                            required: true,
                            email:true
		   				},
		   				password: {
		   					required: true
		   				}
		   		}
		   		params.messages={
		   				email: {
		   					required: "Email不能为空！",
                            email:"email格式不正确"
		   				},
		   				password: {
		   					required: "密码不能为空！"
		   				}
		   		}
		   		params.form = $("#loginForm");
                var pwd = $.trim($("#password").val());
                var pwdMd5 = CryptoJS.MD5(pwd).toString();
                params.form.validate({
                    rules:params.rules,
                    messages: params.messages
                });
                var b = isPC();
                var data = {"email":$.trim($("#email").val()), "password":pwdMd5};
                if (!b) {
                    data = {"email":$.trim($("#email").val()), "password":pwdMd5, "mobile":1};
                }
                if (params.form.valid()) {
                    $.ajax({
                        url:  "${ctx}/login",
                        type: 'post',
                        sync: false,
                        data:data,
                        dataType:'json',
                        success: function(json) {
                            if (json.code == 0) {
                                window.location.href = "${ctx}/main";
                            } else {
                                App.alert(json.msg);
                            }
                        },
                        error: function(xhr, textStatus, errorThrown){
                            App.alert(errorThrown);
                        }
                    });
                }
		    });
            $("body").keydown(function() {
                if (event.keyCode == "13") {//keyCode=13是回车键
                    if ($(".jconfirm-box-container").length == 0) {
                        $('#loginBtn').click();
                    }
                }
            });
	    })
    </script>
  </body>
</html>

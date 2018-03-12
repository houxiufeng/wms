<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>wms zoo</title>
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

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="${flatpointPath}/js/html5shiv.js"></script>
    <![endif]-->
  </head>

  <body class="light_blue">

    <div class="login-container">
        x:<input type="text" value="" id="x"/><br/>
        y:<input type="text" value="" id="y"/><br/>
        excel:<input type="file" id="upfile"/>&nbsp;<input type="button" value="上传" onclick="javascript:uploadAttachment()"/><br/>
        <a href="javascript:void(0)" id="calc">计算</a>&nbsp;&nbsp;result:<span id="xyResult"></span>
    </div>



    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${flatpointPath}/js/jquery-1.10.2.js"></script>
    <script src="${flatpointPath}/js/library/jquery.validate.min.js"></script>
    <script src="${ctx}/js/app.js"></script>
    <script>
        function uploadAttachment(){
            if ($("#upfile").get(0).files.length == 0) {
                alert("请选择文件后在上传！");
                return false;
            }
            var fd = new FormData();
            var file = $("#upfile").get(0).files[0];
            fd.append("file", file);
            $.ajax({
                url: "uploadExcelFile",
                type: "POST",
                processData: false,
                contentType: false,
                data: fd,
                dataType: 'json',
                success: function(d) {
                    if (d.code == 0) {
                        alert("上传成功！");
                    } else {
                        alert("上传失败！");
                    }
                },
                error : function(err) {
                    console.log(err);
                },
                complete : function () {
                    $("#upfile").val("");
                }
            });
        }

	    jQuery(function($){
	        //code here
            $("#calc").click(function () {
                var x = $("#x").val();
                $.ajax({
                    url: "calc",
                    type: 'post',
                    data: {"x":$("#x").val(),"y":$("#y").val()},
                    dataType:'json',
                    success: function(json) {
                        $("#xyResult").text(json.data);
                    },
                    error: function(xhr, textStatus, errorThrown){
                        alert(errorThrown);
                    },
                    complete: function () {
                    }
                });
            });
	    })
    </script>
  </body>
</html>

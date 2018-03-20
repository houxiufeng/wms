var appCtx;
Date.prototype.Format = function(fmt) { //author: meizz
	var o = {
		"M+" : this.getMonth() + 1, //月份 
		"d+" : this.getDate(), //日 
		"h+" : this.getHours(), //小时 
		"m+" : this.getMinutes(), //分 
		"s+" : this.getSeconds(), //秒 
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
		"S" : this.getMilliseconds()
	//毫秒 
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

jQuery.validator.addMethod("eng", function (value, element) {
    return this.optional(element) || /^[a-zA-Z0-9_\.]+$/.test(value);
}, "只能包括英文字母,数字和下划线");

/*判断是否为空*/
function _isNull(value){
    return value == undefined || value == null || jQuery.trim(value) == "" || jQuery.trim(value) == 'undefined' || jQuery.trim(value) == 'null';
}

//公用方法goToPage
var App = {
	    goToPage: function (url, data) {
            jQuery.ajax({
                url: url,
                type: 'get',
                data: data,
                success: function(html) {
                    jQuery("#main").html(html);
                },
                error: function(xhr, textStatus, errorThrown){
                    alert(errorThrown);
                },
				complete: function () {
					console.log(url + "-" + data);
                }
            });
        },
		alert: function(content, cbk){
			jQuery.alert({
				icon: 'icon-ok-sign',
				confirmButtonClass: 'btn yellow',
				confirmButton: '确定',
			    title: "Alert!",
			    content: content,
			    confirm: function(){
			        if(cbk) cbk();
			    }
			});
		},
		confirm: function(content,cbk){
			jQuery.confirm({
				icon: 'icon-warning-sign',
			    title: 'Confirm!',
			    confirmButton: '确定',
			    confirmButtonClass: 'btn red',
			    cancelButton: '关闭',
			    content: content,
			    confirm: function(){
			    	if(cbk) cbk();
			    }
			});
		},
		validate: function(params){
			params.form.validate({
				rules:params.rules,
				messages: params.messages
			});
			
			if (params.form.valid() && params.cbk) {
				params.cbk();
			}
		}
		
	};
function logout() {
    jQuery.ajax({
        url: appCtx +"/logout",
        type: 'post',
        dataType: 'json',
        success: function (json) {
            if (json.code == 0) {
                window.location.href=appCtx+"/login";
                jQuery.removeCookie('wms_token', { path: '/' });
            } else {
                alert(json.msg);
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

function uploadProductImg(){
    if (jQuery("#upImg").get(0).files.length == 0) {
        App.alert("请选择图片后再上传！");
        return false;
    }
    var fd = new FormData();
    var file = jQuery("#upImg").get(0).files[0];
    if (file.size/1024 > 1024) {//大于1M
        App.alert('图片不能大于1M');
        return false;
	}
	var fileName = file.name;
	if (fileName.lastIndexOf(".") == -1 || !checkImgExt(fileName.substring(fileName.lastIndexOf(".")).toLowerCase())) {
        App.alert("图片格式不正确");
        return false;
	}
	if (fileName.indexOf(",") != -1 || fileName.indexOf("&") != -1 || fileName.indexOf("?") != -1) {
	    App.alert("图片名不能包括,&?");
	    return false;
    }
    fd.append("productImg", file);
    jQuery.ajax({
        url: "fileupload/uploadProductImg",
        type: "POST",
        processData: false,
        contentType: false,
        data: fd,
        dataType: 'json',
        success: function(d) {
            if (d.code == 0) {
                var productImgTable = jQuery("#productImgTable");
                productImgTable.show();
                var imgRow = ['<tr class="imgTr">',
                    '<td>'+ d.imgName +'</td>',
                    '<td>',
                    '<a href="javascript:void(0);" onclick="showImg(this)" data-imgpath="' + d.imgPath + '" class="btn yellow btn-small imgPath">查看</a>',
                    '<a href="javascript:void(0);" onclick="deleteImgRow(this)" class="btn grey btn-small">删除</a>',
                    '</td>',
                    '</tr>'].join('\n');
                productImgTable.append(imgRow);

            } else {
                App.alert("上传失败！");
            }
        },
        error : function(err) {
            console.log(err);
            App.alert("上传失败！");
        },
        complete : function () {
            jQuery("#upImg").val("");
        }
    });

}

function deleteImgRow(obj) {
    jQuery(obj).closest("tr.imgTr").remove();
    var productImgTable = jQuery("#productImgTable");
    if (productImgTable.find("tr.imgTr").length == 0) {
        productImgTable.hide();
    }
};

function deleteFileRow(obj) {
    jQuery(obj).closest("tr.fileTr").remove();
    var productFileTable = jQuery("#productFileTable");
    if (productFileTable.find("tr.fileTr").length == 0) {
        productFileTable.hide();
    }
};
function showImg(obj) {
    var imgPath = jQuery(obj).data("imgpath");
    jQuery.dialog({
        title: '图片展示',
        content: '<img src="' + imgPath + '" style="width: 450px;">'
    });
};

function checkImgExt(ext) {
    if (!ext.match(/.jpg|.gif|.png|.bmp/i)) {
        return false;
    }
    return true;
}

function uploadProductFile(){
    if (jQuery("#upFile").get(0).files.length == 0) {
        App.alert("请选择文件后再上传！");
        return false;
    }
    var fd = new FormData();
    var file = jQuery("#upFile").get(0).files[0];
    if (file.size/1024 > 3027) {//大于3M
        App.alert('附件不能大于3M');
        return false;
    }
    var fileName = file.name;
    if (fileName.indexOf(",") != -1 || fileName.indexOf("&") != -1 || fileName.indexOf("?") != -1) {
        App.alert("文件名不能包括,&?");
        return false;
    }

    fd.append("productFile", file);
    jQuery.ajax({
        url: "fileupload/uploadProductFile",
        type: "POST",
        processData: false,
        contentType: false,
        data: fd,
        dataType: 'json',
        success: function(d) {
            if (d.code == 0) {
                var productFileTable = jQuery("#productFileTable");
                productFileTable.show();
                var fileRow = ['<tr class="fileTr">',
                    '<td>'+ d.fileName +'</td>',
                    '<td>',
                    '<a href="javascript:void(0);" onclick="downloadFile(this)" data-filepath="' + d.filePath + '" class="btn yellow btn-small filePath">下载</a>',
                    '<a href="javascript:void(0);" onclick="deleteFileRow(this)" class="btn grey btn-small">删除</a>',
                    '</td>',
                    '</tr>'].join('\n');
                productFileTable.append(fileRow);

            } else {
                App.alert("上传失败！");
            }
        },
        error : function(err) {
            console.log(err);
            App.alert("上传失败！");
        },
        complete : function () {
            jQuery("#upFile").val("");
        }
    });

}

function downloadFile(obj) {
    var filepath = jQuery(obj).data("filepath");
    window.open(filepath);
}

function uploadVendorAvator() {
    if (jQuery("#upAvator").get(0).files.length == 0) {
        App.alert("请选择图片后再上传！");
        return false;
    }
    var fd = new FormData();
    var file = jQuery("#upAvator").get(0).files[0];
    if (file.size/1024 > 1024) {//大于1M
        App.alert('图片不能大于1M');
        return false;
    }
    var fileName = file.name;
    if (fileName.lastIndexOf(".") == -1 || !checkImgExt(fileName.substring(fileName.lastIndexOf(".")).toLowerCase())) {
        App.alert("图片格式不正确");
        return false;
    }
    if (fileName.indexOf(",") != -1 || fileName.indexOf("&") != -1 || fileName.indexOf("?") != -1) {
        App.alert("图片名不能包括,&?");
        return false;
    }
    fd.append("vendorAvator", file);
    jQuery.ajax({
        url: "fileupload/uploadVendorAvator",
        type: "POST",
        processData: false,
        contentType: false,
        data: fd,
        dataType: 'json',
        success: function(d) {
            if (d.code == 0) {
                var avatorObj = jQuery("input[name='avator']");
                avatorObj.val(d.avatorPath);
                avatorObj.next().attr('src',d.avatorPath);
                avatorObj.next().show();
            } else {
                App.alert("上传失败！");
            }
        },
        error : function(err) {
            console.log(err);
            App.alert("上传失败！");
        },
        complete : function () {
            jQuery("#upAvator").val("");
        }
    });
}

function isPC() {//true为PC端，false为手机端
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}
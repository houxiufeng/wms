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
}, "only a-zA-Z0-9_ permited");

/*判断是否为空*/
function _isNull(value){
    return value == undefined || value == null || jQuery.trim(value) == "" || jQuery.trim(value) == 'undefined' || jQuery.trim(value) == 'null';
}

function _isEmail(value) {
    return /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/.test(value);
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
				confirmButton: 'OK',
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
			    confirmButton: 'OK',
			    confirmButtonClass: 'btn red',
			    cancelButton: 'CLOSE',
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
                alert(json.message);
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

function uploadProductImg(){
    if (jQuery("#upImg").get(0).files.length == 0) {
        App.alert("please chose image!");
        return false;
    }
    var fd = new FormData();
    var file = jQuery("#upImg").get(0).files[0];
    if (file.size/1024 > 1024) {//大于1M
        App.alert('image size can not mare than 1M');
        return false;
	}
	var fileName = file.name;
	if (fileName.lastIndexOf(".") == -1 || !checkImgExt(fileName.substring(fileName.lastIndexOf(".")).toLowerCase())) {
        App.alert("invalid suffix");
        return false;
	}
	if (fileName.indexOf(",") != -1 || fileName.indexOf("&") != -1 || fileName.indexOf("?") != -1) {
	    App.alert("can't include,&? in image name");
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
                    '<a href="javascript:void(0);" onclick="showImg(this)" data-imgpath="' + d.imgPath + '" class="btn yellow btn-small imgPath"><i class="icon-eye-open"></i></a>',
                    '<a href="javascript:void(0);" onclick="deleteImgRow(this)" class="btn grey btn-small"><i class="icon-trash"></i></a>',
                    '</td>',
                    '</tr>'].join('\n');
                productImgTable.append(imgRow);

            } else {
                App.alert("upload failed!");
            }
        },
        error : function(err) {
            console.log(err);
            App.alert("upload failed!");
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
        title: 'Show Image',
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
        App.alert("please chose file!");
        return false;
    }
    var fd = new FormData();
    var file = jQuery("#upFile").get(0).files[0];
    if (file.size/1024 > 3027) {//大于3M
        App.alert('file size can not more than 3M');
        return false;
    }
    var fileName = file.name;
    if (fileName.indexOf(",") != -1 || fileName.indexOf("&") != -1 || fileName.indexOf("?") != -1) {
        App.alert("can't include,&? in file name");
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
                    '<a href="javascript:void(0);" onclick="downloadFile(this)" data-filepath="' + d.filePath + '" class="btn yellow btn-small filePath"><i class="icon-download-alt"></i></a>',
                    '<a href="javascript:void(0);" onclick="deleteFileRow(this)" class="btn grey btn-small"><i class="icon-trash"></i></a>',
                    '</td>',
                    '</tr>'].join('\n');
                productFileTable.append(fileRow);

            } else {
                App.alert("upload failed!");
            }
        },
        error : function(err) {
            console.log(err);
            App.alert("upload failed!");
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

function uploadEngineerAvator() {
    if (jQuery("#upAvator").get(0).files.length == 0) {
        App.alert("please select avator!");
        return false;
    }
    var fd = new FormData();
    var file = jQuery("#upAvator").get(0).files[0];
    if (file.size/1024 > 1024) {//大于1M
        App.alert('image size can not mare than 1M');
        return false;
    }
    var fileName = file.name;
    if (fileName.lastIndexOf(".") == -1 || !checkImgExt(fileName.substring(fileName.lastIndexOf(".")).toLowerCase())) {
        App.alert("invalid suffix");
        return false;
    }
    if (fileName.indexOf(",") != -1 || fileName.indexOf("&") != -1 || fileName.indexOf("?") != -1) {
        App.alert("can't include ,&? in image name");
        return false;
    }
    fd.append("engineerAvator", file);
    jQuery.ajax({
        url: "fileupload/uploadEngineerAvator",
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
                App.alert("upload failed!");
            }
        },
        error : function(err) {
            console.log(err);
            App.alert("upload failed!");
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

function buildPhoneStr(prefix, phone) {
    var s = "";
    if (!_isNull(phone)) {
       s = jQuery.trim(phone);
    } else {
        return s;
    }
    if (!_isNull(prefix)) {
        s = jQuery.trim(prefix) + "-" + s;
    }
    return s;
}

function splitPhoneStr(phoneStr) {
    var array = ["",""];
    if (!_isNull(phoneStr)) {
        var ss = phoneStr.split("-");
        if (ss.length == 2) {
            array = ss;
        } else {
            array[1] = phoneStr;
        }
    }
    return array;
}

function onlyInt(obj) {
    if(obj.value.length==1){
        obj.value=obj.value.replace(/[^0-9]/g,'')
    } else {
        obj.value=obj.value.replace(/(\D)/g,'')
    }
}
var User = {
		getTableData: function(iDisplayLength){
            iDisplayLength = iDisplayLength || 10;
			jQuery('.datatable').dataTable({
		        sAjaxSource: appCtx + "/user/loadData",
		        // oLanguage: {
		        //     sUrl: appCtx + '/flatpoint/js/zh_CN.json',
		        // },
		        bSort: false,                        // 是否排序功能
		        bFilter: false,                       // 过滤功能
		        bPaginate: true,                     // 翻页功能
		        bInfo: true,                         // 页脚信息
		        bProcessing: true,                   //显示正在加载中
		        bServerSide: true,                   //开启服务器模式
		        sPaginationType: "full_numbers",    //分页策略
		        bAutoWidth: false,                  // 是否非自动宽度
		        sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
                iDisplayLength: iDisplayLength,
		        //sPaginationType: "bootstrap",
		        sDom: '<"top">rt<"tableFooter"lip<"clear">',
		        fnServerParams : function (aoData) {
		            aoData.push({"name": "name", "value":jQuery("#name").val()});
		            aoData.push({"name": "organizationId", "value":jQuery("#organizationId").val()});
		            jQuery.cookie("user_iDisplayLength",aoData[4]["value"]);
		        },
		        aoColumns:[{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "Name"
		        },{
		            mData : "roleName",
		            sDefaultContent : "",
		            sTitle : "Role"
		            
		        },{
                    mData : "organizationName",
                    sDefaultContent : "",
                    sTitle : "Organization"

                },{
		            mData : "email",
		            sDefaultContent : "",
		            sTitle : "Email"
		            
		        },{
		            mData : "address",
		            sDefaultContent : "",
		            sTitle : "Address"
		            
		        },{
		            mData : "id",
		            sDefaultContent : "",
		            sTitle : "Operation",
		            mRender: function(value, type ,data){
		            	return '<a class="btn edit blue" title="edit user" href="javascript:User.editUser('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a class="btn edit blue" title="reset password" href="javascript:User.resetPwd('+ value + ')"><i class="icon-keyboard"></i></a>\
		            	        <a class="btn edit blue" title="delete user" href="javascript:User.deleteUser('+ value + ')"><i class="icon-trash"></i></a>';
		            }
		            
		        }]
		    })
		},
			
    // 查询按钮
    queryList : function(){
    	jQuery(".datatable").dataTable().fnDraw();
    },
    
    validateUser: function(cbk) {
    	var params = {};
    	params.rules = {
            name: {
                required: true
            },
            password: {
            	required: true
            },
            email: {
                required: true,
            	email: true
            }
        }
    	params.messages={
            name: {
                required: "Name can't empty！"
            },
            password: {
            	required: "Password can't empty！"
            },
            email: {
                required: "Email can't empty",
            	email: "Email wrong！"
            }
    	}
    	params.form = jQuery("#userForm");
    	params.cbk = cbk;
        App.validate(params);
    },
    
    editUser : function(id){
        App.goToPage(appCtx + "/user/edit/" + id);
    },
    
    deleteUser: function(id) {
        App.confirm("Are you sure？", function(){
    		jQuery.ajax({
				url: appCtx + "/user/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
						User.queryList();
					} else {
                        App.alert(json.message);
					}
				},
				error: function(xhr, textStatus, errorThrown){
					alert(errorThrown);
				}
			});
    	});
    },

    resetPwd : function(id) {
        jQuery.confirm({
            title: 'Reset password!',
            confirmButton: 'OK',
            confirmButtonClass: 'btn red',
            cancelButton: 'CLOSE',
            content: '<div>New Password：<input type="text" id="newPwd" maxlength="20" value=""></div>',
            confirm: function(){
                var newPwd = jQuery.trim(jQuery("#newPwd").val());
                if (!newPwd) {
                    App.alert("password can't empty!");
                    return false;
                }
                if (!/^[a-zA-Z0-9_\.]+$/.test(newPwd)) {
                    App.alert("invalid password, should a-zA-Z0-9_.");
                    return false;
                }
                var pwdMd5 = CryptoJS.MD5(newPwd).toString();
                jQuery.ajax({
                    url: appCtx + "/user/resetPwd",
                    type: 'post',
                    dataType:'json',
                    data:{"id":id, "password":pwdMd5},
                    success: function(json) {
                        if (json.code == "0") {
                            App.alert("success！");
                            User.queryList();
                        } else {
                            App.alert(json.message);
                        }
                    },
                    error: function(xhr, textStatus, errorThrown){
                        App.alert(errorThrown);
                    }
                });
            }
        });
    }
}


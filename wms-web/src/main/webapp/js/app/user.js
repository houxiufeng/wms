var User = {
		getTableData: function(){
			jQuery('.datatable').dataTable({
		        sAjaxSource: appCtx + "/user/loadData",
		        oLanguage: {
		            sUrl: appCtx + '/flatpoint/js/zh_CN.json',
		        },
		        bSort: false,                        // 是否排序功能
		        bFilter: false,                       // 过滤功能
		        bPaginate: true,                     // 翻页功能
		        bInfo: true,                         // 页脚信息
		        bProcessing: true,                   //显示正在加载中
		        bServerSide: true,                   //开启服务器模式
		        sPaginationType: "full_numbers",    //分页策略
		        bAutoWidth: false,                  // 是否非自动宽度
		        sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
		        //sPaginationType: "bootstrap",
		        sDom: '<"top">rt<"tableFooter"lip<"clear">',
		        fnServerParams : function (aoData) {
		            aoData.push({"name": "name", "value":jQuery("#name").val()});
		            aoData.push({"name": "organizationId", "value":jQuery("#organizationId").val()});
		        },
		        aoColumns:[{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "姓名"
		        },{
		            mData : "roleName",
		            sDefaultContent : "",
		            sTitle : "角色"
		            
		        },{
                    mData : "organizationName",
                    sDefaultContent : "",
                    sTitle : "机构"

                },{
		            mData : "email",
		            sDefaultContent : "",
		            sTitle : "Email"
		            
		        },{
		            mData : "address",
		            sDefaultContent : "",
		            sTitle : "地址"
		            
		        },{
		            mData : "id",
		            sDefaultContent : "",
		            sTitle : "操作",
		            mRender: function(value, type ,data){
		            	return '<a class="btn edit blue" href="javascript:User.editUser('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a class="btn edit blue" href="javascript:User.resetPwd('+ value + ')"><i class="icon-keyboard"></i></a>\
		            	        <a class="btn edit blue" href="javascript:User.deleteUser('+ value + ')"><i class="icon-trash"></i></a>';
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
                required: "用户名不能为空！"
            },
            password: {
            	required: "密码不能为空！"
            },
            email: {
                required: "Email不能为空!",
            	email: "Email格式不正确！"
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
        App.confirm("确定要删除？", function(){
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
            title: '重置密码!',
            confirmButton: '确定',
            confirmButtonClass: 'btn red',
            cancelButton: '关闭',
            content: '<div>新密码：<input type="text" id="newPwd" maxlength="20" value="abc123"></div>',
            confirm: function(){
                var newPwd = jQuery.trim(jQuery("#newPwd").val());
                if (!newPwd) {
                    App.alert("密码不能为空！");
                    return false;
                }
                if (!/^[a-zA-Z0-9_\.]+$/.test(newPwd)) {
                    App.alert("密码必须是数字、字母或下划线");
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
                            App.alert("重置密码成功！");
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


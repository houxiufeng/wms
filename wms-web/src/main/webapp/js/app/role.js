var Role = {
		getTableData: function(){
			jQuery('#roleTable').dataTable({
		        sAjaxSource: appCtx + "/role/loadData",
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
		        aoColumns:[{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "角色名称"
		        },{
		            mData : "code",
		            sDefaultContent : "",
		            sTitle : "编码"
		            
		        },{
		            mData : "remark",
		            sDefaultContent : "",
		            sTitle : "备注"
		        },{
                    mData : "status",
                    sDefaultContent : "",
                    sTitle : "状态",
                    mRender: function(value, type ,data){
                        if (value == 1) {
                            return "启用";
                        } else {
                            return "禁用";
                        }
                    }

                },{
		            mData : "id",
		            sDefaultContent : "",
		            sTitle : "操作",
		            mRender: function(value, type ,data){
		            	return '<a class="btn edit blue" href="javascript:Role.edit('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a class="btn edit blue" href="javascript:Role.delete('+ value + ')"><i class="icon-trash"></i></a>\
		            	        <a class="btn edit blue" href="javascript:Role.editPermission('+ value + ')"><i class="icon-wrench"></i></a>';
		            }
		            
		        }]
		    })
		},
			
    // 查询按钮
    queryList : function(){
    	jQuery("#roleTable").dataTable().fnDraw();
    },
    
    save: function() {
    	var params = {};
    	params.rules = {
            name: {
                required: true
            },
            code: {
                required: true
            }
        };
    	params.messages={
            name: {
                required:"角色名称不能为空！"
            },
            code: {
                required:"角色编码不能为空！"
            }
    	};
    	params.form = jQuery("#roleForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/role/create",
                type: 'post',
                data:jQuery("#roleForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("角色创建成功!", function(){
                            App.goToPage(appCtx+"/role");
                        });
                    } else {
                        App.alert(json.message);
                    }
                },
                error: function(xhr, textStatus, errorThrown){
                    alert(errorThrown);
                }
            });
		}
    },
    
    edit : function(id){
        App.goToPage(appCtx + "/role/edit/" + id);
    },

    update: function() {
        var params = {};
        params.rules = {
            name: {
                required: true
            },
            code: {
                required: true
            }
        };
        params.messages={
            name: {
                required:"角色名称不能为空！"
            },
            code: {
                required:"角色编码不能为空！"
            }
        };
        params.form = jQuery("#roleForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/role/update/",
                type: 'post',
                data:jQuery("#roleForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("角色修改成功!", function(){
                            App.goToPage(appCtx+"/role");
                        });
                    } else {
                        App.alert(json.message);
                    }
                },
                error: function(xhr, textStatus, errorThrown){
                    alert(errorThrown);
                }
            });
        }
    },
    editPermission : function(id){
        App.goToPage(appCtx + "/role/editPermission/" + id);
    },
    
    delete: function(id) {
        App.confirm("确定要删除？", function(){
    		jQuery.ajax({
				url: appCtx + "/role/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
						Role.queryList();
					} else {
                        App.alert(json.message);
					}
				},
				error: function(xhr, textStatus, errorThrown){
					alert(errorThrown);
				}
			});
    	});
    }
}


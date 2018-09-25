var Role = {
		getTableData: function(iDisplayLength){
            iDisplayLength = iDisplayLength || 10;
			jQuery('#roleTable').dataTable({
		        sAjaxSource: appCtx + "/role/loadData",
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
                    jQuery.cookie("role_iDisplayLength",aoData[4]["value"]);
                },
		        aoColumns:[{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "Role"
		        },{
		            mData : "code",
		            sDefaultContent : "",
		            sTitle : "Code"
		            
		        },{
		            mData : "remark",
		            sDefaultContent : "",
		            sTitle : "Remarks"
		        },{
                    mData : "status",
                    sDefaultContent : "",
                    sTitle : "Status",
                    mRender: function(value, type ,data){
                        if (value == 1) {
                            return "On";
                        } else {
                            return "Off";
                        }
                    }

                },{
		            mData : "id",
		            sDefaultContent : "",
		            sTitle : "Operation",
		            mRender: function(value, type ,data){
		            	return '<a title="edit role" class="btn edit blue" href="javascript:Role.edit('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a title="delete role" class="btn edit blue" href="javascript:Role.delete('+ value + ')"><i class="icon-trash"></i></a>\
		            	        <a title="authorize" class="btn edit blue" href="javascript:Role.editPermission('+ value + ')"><i class="icon-sitemap"></i></a>';
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
                required:"Role can't be empty！"
            },
            code: {
                required:"Code can't be empty！"
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
                        App.alert("success!", function(){
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
                required:"Role can't be empty！"
            },
            code: {
                required:"Code can't be empty！"
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
                        App.alert("success!", function(){
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
        App.confirm("Are you sure？", function(){
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


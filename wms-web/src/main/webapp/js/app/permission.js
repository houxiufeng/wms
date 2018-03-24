var Permission = {
		getTableData: function(){
			jQuery('#permissionTable').dataTable({
		        sAjaxSource: appCtx + "/permission/loadData",
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
		            aoData.push({"name": "pname", "value":jQuery("#pname").val()});
		        },
		        aoColumns:[{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "权限名称"
		        },{
		            mData : "url",
		            sDefaultContent : "",
		            sTitle : "url"
		            
		        },{
		            mData : "pname",
		            sDefaultContent : "",
		            sTitle : "父级"
		        },{
		            mData : "menuFlag",
		            sDefaultContent : "",
		            sTitle : "是否菜单",
		            mRender: function(value, type ,data){
		            	if (value == 1) {
			            	return "是";
		            	} else {
		            		return "否"
						}
		            }
		            
		        },{
		            mData : "createdTime",
		            sDefaultContent : "",
		            sTitle : "创建时间",
                    mRender: function(value, type ,data){
                        return moment(value).format("YYYY-MM-DD HH:mm:ss");
                    }
		            
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
		            	return '<a class="btn edit blue" href="javascript:Permission.edit('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a class="btn edit blue" href="javascript:Permission.delete('+ value + ')"><i class="icon-trash"></i></a>';
		            }
		            
		        }]
		    })
		},
			
    // 查询按钮
    queryList : function(){
    	jQuery("#permissionTable").dataTable().fnDraw();
    },
    
    save: function() {
    	var params = {};
    	params.rules = {
            name: {
                required: true
            }
        }
    	params.messages={
            name: {
                required: "权限名称不能为空！"
            }
    	}
    	params.form = jQuery("#permissionForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/permission/create",
                type: 'post',
                data:jQuery("#permissionForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("权限创建成功!", function(){
                            App.goToPage(appCtx+"/permission");
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
        App.goToPage(appCtx + "/permission/edit/" + id);
    },

    update: function() {
        var params = {};
        params.rules = {
            name: {
                required: true
            }
        }
        params.messages={
            name: {
                required: "权限名称不能为空！"
            }
        }
        params.form = jQuery("#permissionForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/permission/update/",
                type: 'post',
                data:jQuery("#permissionForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("权限修改成功!", function(){
                            App.goToPage(appCtx+"/permission");
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
    
    delete: function(id) {
        App.confirm("确定要删除？", function(){
    		jQuery.ajax({
				url: appCtx + "/permission/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
						Permission.queryList();
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


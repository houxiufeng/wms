var Branch = {
		getTableData: function(){
			jQuery('#branchTable').dataTable({
		        sAjaxSource: appCtx + "/branch/loadData",
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
		        },
		        aoColumns:[{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "店名称"
		        },{
		            mData : "address",
		            sDefaultContent : "",
		            sTitle : "地址"
		            
		        },{
		            mData : "contactPerson",
		            sDefaultContent : "",
		            sTitle : "联系人"
		        },{
		            mData : "contactPhone",
		            sDefaultContent : "",
		            sTitle : "联系人电话"
		        },{
		            mData : "id",
		            sDefaultContent : "",
		            sTitle : "操作",
		            mRender: function(value, type ,data){
		            	return '<a class="btn edit" href="javascript:Branch.addMaintainProduct('+ value + ')">维修产品添加</a>\
		            	        <a class="btn edit" href="javascript:Branch.edit('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a class="btn edit" href="javascript:Branch.delete('+ value + ')"><i class="icon-trash"></i></a>';
		            }
		            
		        }]
		    })
		},
			
    // 查询按钮
    queryList : function(){
    	jQuery("#branchTable").dataTable().fnDraw();
    },
    
    save: function() {
        var params = Branch.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/branch/create",
                type: 'post',
                data:jQuery("#branchForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("创建成功!", function(){
                            App.goToPage(appCtx+"/branch");
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
        App.goToPage(appCtx + "/branch/edit/" + id);
    },

    update: function() {
        var params = Branch.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/branch/update/",
                type: 'post',
                data:jQuery("#branchForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("修改成功!", function(){
                            App.goToPage(appCtx+"/branch");
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
				url: appCtx + "/branch/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
						Branch.queryList();
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

    buildValidate: function () {
        var params = {};
        params.rules = {
            name: {
                required: true
            },
            code: {
                required: true,
                eng:true
            },
            address: {
                required:true
            },
            contactPerson: {
                required:true
            },
            contactPhone: {
                required:true
            }
        }
        params.messages={
            name: {
                required: "名称必填！"
            },
            code: {
                required: "code必填！",
                eng:"必须是字母数字！"
            },
            address: {
                required:"地址必填！"
            },
            contactPerson: {
                required:"联系人必填！"
            },
            contactPhone: {
                required:"联系人电话必填！"
            }
        }
        params.form = jQuery("#branchForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        return params;
    },

    addMaintainProduct: function (id) {
        App.goToPage(appCtx + "/branch/product?branchId=" + id);
    }

}


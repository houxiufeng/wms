var Organization = {
		getTableData: function(iDisplayLength){
            iDisplayLength = iDisplayLength || 10;
			jQuery('#organizationTable').dataTable({
		        sAjaxSource: appCtx + "/organization/loadData",
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
                    jQuery.cookie("organization_iDisplayLength",aoData[4]["value"]);
                },
		        aoColumns:[{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "Name"
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
		            	return '<a title="edit platform user" class="btn edit blue" href="javascript:Organization.edit('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a title="delete platform user" class="btn edit blue" href="javascript:Organization.delete('+ value + ')"><i class="icon-trash"></i></a>';
		            }
		            
		        }]
		    })
		},
			
    // 查询按钮
    queryList : function(){
    	jQuery("#organizationTable").dataTable().fnDraw();
    },
    
    save: function() {
    	var params = {};
    	params.rules = {
            name: {
                required: true
            }
        };
    	params.messages={
            name: {
                required:"Name can't be empty！"
            }
    	};
    	params.form = jQuery("#organizationForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        if (params.form.valid()) {
            if (jQuery(":checkbox[name='roleIds']:checked").length == 0) {
                App.alert("please select at least one！")
                return false;
            }
            var data = jQuery("#organizationForm").serialize();
            var logo = jQuery("#logo").attr("src");
            if (!_isNull(logo)) {
                data += "&logo=" + logo;
            }
            jQuery.ajax({
                url: appCtx + "/organization/create",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!", function(){
                            App.goToPage(appCtx+"/organization");
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
        App.goToPage(appCtx + "/organization/edit/" + id);
    },

    update: function() {
        var params = {};
        params.rules = {
            name: {
                required: true
            }
        };
        params.messages={
            name: {
                required:"Name can't be empty！"
            }
        };
        params.form = jQuery("#organizationForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        if (params.form.valid()) {
            if (jQuery(":checkbox[name='roleIds']:checked").length == 0) {
                App.alert("please select at least one role！")
                return false;
            }
            var data = jQuery("#organizationForm").serialize();
            var logo = jQuery("#logo").attr("src");
            if (!_isNull(logo)) {
                data += "&logo=" + logo;
            }
            jQuery.ajax({
                url: appCtx + "/organization/update/",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!", function(){
                            App.goToPage(appCtx+"/organization");
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
        App.confirm("Are you sure？", function(){
    		jQuery.ajax({
				url: appCtx + "/organization/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
                        Organization.queryList();
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

    uploadLogo: function () {
        uploadImg("upImg", function (data) {
            var $logo = jQuery("#logo");
            $logo.attr("src", data.imgPath);
            $logo.show();
        });
    }
}


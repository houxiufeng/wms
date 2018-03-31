var Dict = {
		getTableData: function(){
			jQuery('#dictTable').dataTable({
		        sAjaxSource: appCtx + "/dict/loadData",
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
		        sDom: '<"top">rt<"tableFooter"lip<"clear">',
		        fnServerParams : function (aoData) {
		        },
		        aoColumns:[{
		            mData : "typeName",
		            sDefaultContent : "",
		            sTitle : "Parameter type"

		        },{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "Parameter name"
		            
		        },{
		            mData : "id",
		            sDefaultContent : "",
		            sTitle : "Operation",
		            mRender: function(value, type ,data){
		            	return '<a class="btn edit blue" href="javascript:Dict.delete('+ value + ')"><i class="icon-trash"></i></a>';
		            }
		            
		        }]
		    })
		},
			
    // 查询按钮
    queryList : function(){
    	jQuery("#dictTable").dataTable().fnDraw();
    },
    
    save: function() {
        var params = Dict.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/dict/create",
                type: 'post',
                data:jQuery("#dictForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!", function(){
                            Dict.queryList();
                            Dict.reset();
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
				url: appCtx + "/dict/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
                        Dict.queryList();
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

    reset : function(){
        jQuery("#dictForm")[0].reset();
    },

    buildValidate: function () {
        var params = {};
        params.rules = {
            name: {
                required: true
            }
        }
        params.messages={
            name: {
                required: "parameter name can't be empty!"
            }
        }
        params.form = jQuery("#dictForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        return params;
    },

}


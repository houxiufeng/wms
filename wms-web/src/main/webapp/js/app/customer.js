var Customer = {
		getTableData: function(){
			jQuery('#customerTable').dataTable({
		        sAjaxSource: appCtx + "/customer/loadData",
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
		            sTitle : "客户名称"
		        },{
                    mData : "code",
                    sDefaultContent : "",
                    sTitle : "客户ID"
                },{
                    mData : "phone",
                    sDefaultContent : "",
                    sTitle : "客户电话"
                },{
                    mData : "type",
                    sDefaultContent : "",
                    sTitle : "客户级别",
                    mRender: function(value, type ,data){
                        var result = '';
                        if (value == 1) {
                            result = '长期合作';
                        } else if (value == 2) {
                            result = '短期合作';
                        } else if (value == 3) {
                            result = '临时合作';
                        }
                        return result;
                    }
                },{
                    mData : "creditStatus",
                    sDefaultContent : "",
                    sTitle : "客户信用",
                    mRender: function(value, type ,data){
                        var result = '';
                        if (value == 1) {
                            result = '良好';
                        } else if (value == 2) {
                            result = '中等';
                        } else if (value == 3) {
                            result = '一般';
                        }
                        return result;
                    }
                },{
                    mData : "contractName",
                    sDefaultContent : "",
                    sTitle : "客户合同名称"
                },{
		            mData : "id",
		            sDefaultContent : "",
		            sTitle : "操作",
		            mRender: function(value, type ,data){
		            	return '<a class="btn edit" href="javascript:Customer.edit('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a class="btn edit" href="javascript:Customer.delete('+ value + ')"><i class="icon-trash"></i></a>';
		            }
		            
		        }]
		    })
		},
			
    // 查询按钮
    queryList : function(){
    	jQuery("#customerTable").dataTable().fnDraw();
    },
    
    save: function() {
        var params = Customer.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/customer/create",
                type: 'post',
                data:jQuery("#customerForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("客户创建成功!", function(){
                            App.goToPage(appCtx+"/customer");
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
        App.goToPage(appCtx + "/customer/edit/" + id);
    },

    update: function() {
        var params = Customer.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/customer/update/",
                type: 'post',
                data:jQuery("#customerForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("客户修改成功!", function(){
                            App.goToPage(appCtx+"/customer");
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
				url: appCtx + "/customer/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
                        Customer.queryList();
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
            code:{
                required: true,
                eng: true
            },
            legalPerson: {
                required: true
            },
            contactPerson: {
                required: true
            },
            phone: {
                digits:true
            },
            contactPhone: {
                digits:true
            },
            legalPhone: {
                digits:true
            },
            contractName: {
                required:true
            },
            contractAmount: {
                number:true
            }
        };
        params.messages={
            name: {
                required:"客户名称不能为空！"
            },
            code:{
                required: "客户编码不能为空！",
                eng: "只能是字母，数字和下划线！"
            },
            legalPerson: {
                required: "法人不能为空！"
            },
            contactPerson: {
                required: "联系人不能为空！"
            },
            phone: {
                digits:"电话格式不正确！"
            },
            contactPhone: {
                digits:"电话格式不正确！"
            },
            legalPhone: {
                digits:"电话格式不正确！"
            },
            contractName: {
                required:"合同名称不能为空！"
            },
            contractAmount: {
                number:"金额格式不正确！"
            }
        };
        params.form = jQuery("#customerForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        return params;
    }
}


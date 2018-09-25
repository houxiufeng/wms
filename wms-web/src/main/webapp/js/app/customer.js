var Customer = {
		getTableData: function(){
			jQuery('#customerTable').dataTable({
		        sAjaxSource: appCtx + "/customer/loadData",
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
		        //sPaginationType: "bootstrap",
		        sDom: '<"top">rt<"tableFooter"lip<"clear">',
                fnServerParams : function (aoData) {
                    aoData.push({"name": "name", "value":jQuery("#name").val()});
                },
		        aoColumns:[{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "Customer name"
		        },{
                    mData : "code",
                    sDefaultContent : "",
                    sTitle : "Customer code"
                },{
                    mData : "phone",
                    sDefaultContent : "",
                    sTitle : "Phone number"
                },{
                    mData : "typeName",
                    sDefaultContent : "",
                    sTitle : "Customer degree"
                },{
                    mData : "creditStatusName",
                    sDefaultContent : "",
                    sTitle : "Credit"
                },{
                    mData : "contractName",
                    sDefaultContent : "",
                    sTitle : "Contract name"
                },{
		            mData : "id",
		            sDefaultContent : "",
		            sTitle : "Operation",
		            mRender: function(value, type ,data){
		            	return '<a title="edit customer" class="btn edit blue" href="javascript:Customer.edit('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a title="delete customer" class="btn edit blue" href="javascript:Customer.delete('+ value + ')"><i class="icon-trash"></i></a>';
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
        var data = jQuery("#customerForm").serialize();
        var phoneStr = buildPhoneStr(jQuery('#phone_pre').val(), jQuery('#phone').val());
        data += "&phone="+phoneStr;
        var legalPhoneStr = buildPhoneStr(jQuery('#legalPhone_pre').val(), jQuery('#legalPhone').val());
        data += "&legalPhone="+legalPhoneStr;
        var contactPhoneStr = buildPhoneStr(jQuery('#contactPhone_pre').val(), jQuery('#contactPhone').val());
        data += "&contactPhone="+contactPhoneStr;
        var logo = jQuery("#logo").attr("src");
        if (!_isNull(logo)) {
            data += "&logo=" + logo;
        }
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/customer/create",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!", function(){
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
        var data = jQuery("#customerForm").serialize();
        var phoneStr = buildPhoneStr(jQuery('#phone_pre').val(), jQuery('#phone').val());
        data += "&phone="+phoneStr;
        var legalPhoneStr = buildPhoneStr(jQuery('#legalPhone_pre').val(), jQuery('#legalPhone').val());
        data += "&legalPhone="+legalPhoneStr;
        var contactPhoneStr = buildPhoneStr(jQuery('#contactPhone_pre').val(), jQuery('#contactPhone').val());
        data += "&contactPhone="+contactPhoneStr;
        var logo = jQuery("#logo").attr("src");
        if (!_isNull(logo)) {
            data += "&logo=" + logo;
        }
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/customer/update/",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!", function(){
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
        App.confirm("Are you sure？", function(){
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
                required:"customer name can't be empty"
            },
            code:{
                required: "customer code can't be empty",
                eng: "invalid input, eg:a-z,0-9,_"
            },
            legalPerson: {
                required: "legal person can't be empty"
            },
            contactPerson: {
                required: "contact person can't be empty"
            },
            phone: {
                digits:"invalid phone input!"
            },
            contactPhone: {
                digits:"invalid contact phone input!"
            },
            legalPhone: {
                digits:"invalid legal phone input!"
            },
            contractName: {
                required:"contract name can't be empty!"
            },
            contractAmount: {
                number:"invalid amount"
            }
        };
        params.form = jQuery("#customerForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        return params;
    },
    uploadLogo: function () {
        uploadImg("upImg", function (data) {
            var $logo = jQuery("#logo");
            $logo.attr("src", data.imgPath);
            $logo.show();
        });
    }
}


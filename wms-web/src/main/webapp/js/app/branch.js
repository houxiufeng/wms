var Branch = {
    getTableData: function(iDisplayLength){
        iDisplayLength = iDisplayLength || 10;
        jQuery('#branchTable').dataTable({
            sAjaxSource: appCtx + "/branch/loadData",
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
                jQuery.cookie("branch_iDisplayLength",aoData[4]["value"]);
            },
            aoColumns:[{
                mData : "customerName",
                sDefaultContent : "",
                sTitle : "Customer name"
            },{
                mData : "name",
                sDefaultContent : "",
                sTitle : "Branch name"
            },{
                mData : "address",
                sDefaultContent : "",
                sTitle : "Address"

            },{
                mData : "contactPerson",
                sDefaultContent : "",
                sTitle : "Contact name"
            },{
                mData : "contactPhone",
                sDefaultContent : "",
                sTitle : "contact phone"
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "Operation",
                mRender: function(value, type ,data){
                    return '<a class="btn edit blue" href="javascript:Branch.addMaintainProduct('+ value + ')"><i class="icon-wrench"></i></a>\
                            <a class="btn edit blue" href="javascript:Branch.edit('+ value + ')"><i class="icon-edit"></i></a>\
                            <a class="btn edit blue" href="javascript:Branch.delete('+ value + ')"><i class="icon-trash"></i></a>';
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
        var data = jQuery("#branchForm").serialize();
        debugger;
        var contactPhoneStr = buildPhoneStr(jQuery('#contactPhone_pre').val(), jQuery('#contactPhone').val());
        data += "&contactPhone="+contactPhoneStr;
        if (!_isNull(jQuery("#point_x").val()) && !_isNull(jQuery("#point_y").val())) {
            data += "&poi=" + jQuery("#point_x").val() + "," + jQuery("#point_y").val();
        }
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/branch/create",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!", function(){
                            cleanPointxy();
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
        var data = jQuery("#branchForm").serialize();
        var contactPhoneStr = buildPhoneStr(jQuery('#contactPhone_pre').val(), jQuery('#contactPhone').val());
        data += "&contactPhone="+contactPhoneStr;
        if (!_isNull(jQuery("#point_x").val()) && !_isNull(jQuery("#point_y").val())) {
            data += "&poi=" + jQuery("#point_x").val() + "," + jQuery("#point_y").val();
        }
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/branch/update/",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!", function(){
                            cleanPointxy();
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
        App.confirm("Are you sure？", function(){
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
            },
            userId: {
                required:true
            }
        }
        params.messages={
            name: {
                required: "name can't be empty!"
            },
            code: {
                required: "code can't be empty!",
                eng:"invalid input!"
            },
            address: {
                required:"address can't be empty!！"
            },
            contactPerson: {
                required:"contact name can't be empty!"
            },
            contactPhone: {
                required:"contact phone can't be empty!"
            },
            userId: {
                required:"user can't be empty!"
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
    },

    getMobileTableData: function(){
        jQuery('#branchTable').dataTable({
            sAjaxSource: appCtx + "/branch/loadData",
            oLanguage: {
                "sProcessing": "Processing...",
                "sLengthMenu": "_MENU_ 记录/页",
                "sZeroRecords": "No records",
                "sInfo": "total _TOTAL_ ",
                "sInfoEmpty": "0 items",
                "sInfoFiltered": "(由 _MAX_ 项记录过滤)",
                "sInfoPostFix": "",
                "sSearch": "过滤:",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": "<<",
                    "sPrevious": "<️",
                    "sNext": ">",
                    "sLast": ">>"
                }
            },
            bSort: false,                        // 是否排序功能
            bFilter: false,                       // 过滤功能
            bPaginate: true,                     // 翻页功能
            bInfo: true,                         // 页脚信息
            bProcessing: true,                   //显示正在加载中
            bServerSide: true,                   //开启服务器模式
            // sPaginationType: "full_numbers",    //分页策略
            bAutoWidth: false,                  // 是否非自动宽度
            sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                // aoData.push({"name": "name", "value":jQuery("#name").val()});
            },
            aoColumns:[{
                mData : "name",
                sDefaultContent : "",
                sTitle : "Branch name"
            },{
                mData : "address",
                sDefaultContent : "",
                sTitle : "Address"

            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "Operation",
                mRender: function(value, type ,data){
                    return '<a class="btn edit" href="javascript:App.goToPage(appCtx + \'/mobile/branch/product/add?branchId='+value+'\')">Add Product</a>\
                            <a class="btn edit" href="javascript:App.goToPage(appCtx + \'/mobile/branch/product/list?branchId='+value+'\')">View</a>';
                }

            }]
        })
    },

}


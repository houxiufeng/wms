var Order = {
    getTableData: function(index){
        jQuery('#orderTable'+index).dataTable({
            sAjaxSource: appCtx + "/order/loadData",
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
            // bRetrieve:true,
            bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"lip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "orderNo", "value":jQuery("#orderNo").val()});
                aoData.push({"name": "startTime", "value":jQuery("#startTime").val()});
                aoData.push({"name": "endTime", "value":jQuery("#endTime").val()});
                aoData.push({"name": "status", "value":index});
            },
            aoColumns:[{
                mData : "orderNo",
                sDefaultContent : "",
                sTitle : "订单号"
            },{
                mData : "customerName",
                sDefaultContent : "",
                sTitle : "客户名"
            },{
                mData : "branchName",
                sDefaultContent : "",
                sTitle : "分店名"

            },{
                mData : "productName",
                sDefaultContent : "",
                sTitle : "维修产品名",
                mRender: function(value, type ,data) {
                    return value + "-" + data.productModel;
                }
            },{
                mData : "createdTime",
                sDefaultContent : "",
                sTitle : "订单时间",
                mRender: function(value, type ,data) {
                    return moment(value).format("YYYY-MM-DD HH:mm:ss");
                }
            },{
                mData : "typeName",
                sDefaultContent : "",
                sTitle : "问题类型"
            },{
                mData : "description",
                sDefaultContent : "",
                sTitle : "问题描述"
            },{
                mData : "vendorName",
                sDefaultContent : "",
                sTitle : "维修人员"
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "操作",
                mRender: function(value, type ,data){
                    var opts = ['<a class="btn edit" href="javascript:Order.detail('+ value + ')">查看</a>'];
                    if (index == 0) {
                        opts.push('<a class="btn edit" href="javascript:Order.assign('+ value + ')">委派</a>');
                        opts.push('<a class="btn edit" href="javascript:Order.cancel('+ value + ')">取消</a>');
                    } else if (index == 1) {
                        opts.push('<a class="btn edit" href="javascript:Order.checked('+ value + ')">确认</a>');
                        opts.push('<a class="btn edit" href="javascript:Order.cancel('+ value + ')">取消</a>');
                    } else if (index == 2) {
                        opts.push('<a class="btn edit" href="javascript:Order.fixed('+ value + ')">确认</a>');
                        opts.push('<a class="btn edit" href="javascript:Order.cancel('+ value + ')">取消</a>');
                    } else if (index == 3) {
                        opts.push('<a class="btn edit" href="javascript:Order.audited('+ value + ')">通过</a>');
                        opts.push('<a class="btn edit" href="javascript:Order.cancel('+ value + ')">取消</a>');
                    }
                    return opts.join(" ");
                }

            }]
        })
    },

    // 查询按钮
    queryList : function(){
        var index = jQuery("ul.nav li.active").index();
    	jQuery("#orderTable"+index).dataTable().fnDraw();
    },
    
    save: function() {
        var params = Order.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/order/create",
                type: 'post',
                data:jQuery("#orderForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("创建成功!", function(){
                            App.goToPage(appCtx+"/order");
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

    cancel: function(id) {
        App.confirm("确定要取消订单？", function(){
    		jQuery.ajax({
				url: appCtx + "/order/cancel/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
						Order.queryList();
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
            customerId: {
                required: true
            },
            branchId: {
                required: true
            },
            type: {
                required: true
            },
            branchProductId: {
                required: true
            },
            remark: {
                required: true
            }
        }
        params.messages={
            customerId: {
                required: "客户必选"
            },
            branchId: {
                required: "分支必选"
            },
            type: {
                required: "问题类型必选"
            },
            branchProductId: {
                required: "维修产品必选"
            },
            remark: {
                required: "描述信息必填！"
            }
        }
        params.form = jQuery("#orderForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        return params;
    },
    detail: function (id) {
        jQuery.dialog({
            title: '订单详情',
            columnClass: 'col-md-6 col-md-offset-3',
            content: 'url:'+appCtx+"/order/detail/"+id
        });
    },
    assign: function (id) {
        jQuery.confirm({
            title: '派单中',
            columnClass: 'col-md-8 col-md-offset-2',
            content: 'url:'+appCtx+"/order/assign/"+id,
            confirmButton: '确定',
            cancelButton: '关闭',
            confirm: function(){
                var checkedObj = jQuery("#vendorTable").find(":checkbox:checked[name='vendorId']");
                if (checkedObj.length == 0) {
                    App.alert("请选择一个供应商");
                    return false;
                }
                var vendorId = checkedObj.val();
                jQuery.ajax({
                    url: appCtx + "/order/assign/vendor",
                    type: 'post',
                    data: {"orderId":id, "vendorId":vendorId},
                    dataType:'json',
                    success: function(json) {
                        if (json.code == "0") {
                            Order.queryList();
                            App.alert("委派成功");
                        } else {
                            App.alert(json.message);
                        }
                    },
                    error: function(xhr, textStatus, errorThrown){
                        alert(errorThrown);
                    }
                });

            }
        });
    },
    
    checked: function (id) {
        jQuery.confirm({
            title: '检查中',
            columnClass: 'col-md-6 col-md-offset-3',
            content: 'url:'+appCtx+"/order/check/"+id,
            confirmButton: '确定',
            cancelButton: '关闭',
            confirm: function(){
                var description = jQuery("#description").val();
                var type = jQuery("#type").val();
                if (_isNull(description)) {
                    App.alert("描述信息必填");
                    return false;
                }
                jQuery.ajax({
                    url: appCtx + "/order/checked",
                    type: 'post',
                    data: {"orderId":id, "type":type, "description":description},
                    dataType:'json',
                    success: function(json) {
                        if (json.code == "0") {
                            Order.queryList();
                            App.alert("确认完成");
                        } else {
                            App.alert(json.message);
                        }
                    },
                    error: function(xhr, textStatus, errorThrown){
                        alert(errorThrown);
                    }
                });

            }
        });
    },

    fixed: function (id) {
        jQuery.confirm({
            title: '维修中',
            columnClass: 'col-md-6 col-md-offset-3',
            content: 'url:'+appCtx+"/order/fix/"+id,
            confirmButton: '确定',
            cancelButton: '关闭',
            confirm: function(){
                jQuery.ajax({
                    url: appCtx + "/order/fixed",
                    type: 'post',
                    data: {"orderId":id},
                    dataType:'json',
                    success: function(json) {
                        if (json.code == "0") {
                            Order.queryList();
                            App.alert("维修完成");
                        } else {
                            App.alert(json.message);
                        }
                    },
                    error: function(xhr, textStatus, errorThrown){
                        alert(errorThrown);
                    }
                });

            }
        });
    },
    audited: function (id) {
        jQuery.confirm({
            title: '审核中',
            columnClass: 'col-md-6 col-md-offset-3',
            content: '完成备注:<textarea id="remark" style="resize:none;width: 100%;height: 150px;"></textarea>',
            confirmButton: '通过',
            cancelButton: '关闭',
            confirm: function(){
                jQuery.ajax({
                    url: appCtx + "/order/audited",
                    type: 'post',
                    data: {"orderId":id, "remark":jQuery.trim(jQuery("#remark").val())},
                    dataType:'json',
                    success: function(json) {
                        if (json.code == "0") {
                            Order.queryList();
                            App.alert("审核完成");
                        } else {
                            App.alert(json.message);
                        }
                    },
                    error: function(xhr, textStatus, errorThrown){
                        alert(errorThrown);
                    }
                });

            }
        });
    }

}


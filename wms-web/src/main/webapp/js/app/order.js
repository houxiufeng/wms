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
            fnRowCallback : function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                console.log(nRow);
                console.log(aData);
                console.log(iDisplayIndex);
                console.log(iDisplayIndexFull);
                if (aData.overed) {
                    jQuery(nRow).find("td").not("td:last").css({"background-color":"#ac193d","color":"#fff"});
                } else if (aData.warned) {
                    jQuery(nRow).find("td").not("td:last").css({"background-color":"#ff8f32","color":"#fff"});
                }
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
                    var opts = ['<a class="btn edit blue" href="javascript:Order.detail('+ value + ')"><i class="icon-eye-open"></i></a>'];
                    if (index == 0) {
                        opts.push('<a class="btn edit blue" href="javascript:Order.assign('+ value + ')"><i class="icon-group"></i></a>');
                        opts.push('<a class="btn edit blue" href="javascript:Order.cancel('+ value + ')"><i class="icon-remove"></i></a>');
                    } else if (index == 1) {
                        opts.push('<a class="btn edit blue" href="javascript:Order.checked('+ value + ')"><i class="icon-ok"></i></a>');
                        opts.push('<a class="btn edit blue" href="javascript:Order.cancel('+ value + ')"><i class="icon-remove"></i></a>');
                    } else if (index == 2) {
                        opts.push('<a class="btn edit blue" href="javascript:Order.fixed('+ value + ')"><i class="icon-ok"></i></a>');
                        opts.push('<a class="btn edit blue" href="javascript:Order.cancel('+ value + ')"><i class="icon-remove"></i></a>');
                    }
                    // else if (index == 3) {
                    //     opts.push('<a class="btn edit blue" href="javascript:Order.audited('+ value + ')">通过</a>');
                    //     opts.push('<a class="btn edit blue" href="javascript:Order.cancel('+ value + ')">取消</a>');
                    // }
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
    
    save: function(wap) {
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
                            if (!_isNull(wap)) {//手机端
                                App.goToPage(appCtx+"/mobile/order/list?flag=1");
                            } else {
                                App.goToPage(appCtx+"/order");
                            }
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
            },
            description: {
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
                required: "完成备注必填！"
            },
            description: {
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
                var privateOrder;
                App.confirm("自制单号:<input id='privateOrder' type='text' maxlength='32'>", function () {
                    if (_isNull(jQuery("#privateOrder").val())) {
                        App.alert("请填写自制单号");
                        return false;
                    } else {
                        privateOrder = jQuery("#privateOrder").val();
                        jQuery.ajax({
                            url: appCtx + "/order/assign/vendor",
                            type: 'post',
                            data: {"orderId":id, "vendorId":vendorId, "privateOrder":privateOrder},
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
    },

    getMobileTableData: function (status) {
        jQuery('#orderTable').dataTable({
            sAjaxSource: appCtx + "/order/loadData",
            // oLanguage: {
            //     sUrl: appCtx + '/flatpoint/js/zh_CN.json',
            // },
            oLanguage: {
                "sProcessing": "处理中...",
                "sLengthMenu": "_MENU_ 记录/页",
                "sZeroRecords": "没有匹配的记录",
                "sInfo": "共 _TOTAL_ 条",
                "sInfoEmpty": "共 0 条",
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
            iDisplayLength: 5,
            // bRetrieve:true,
            // bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "status", "value":status});
                aoData.push({"name": "vendorId", "value":jQuery("#vendorId").val()});
            },
            aoColumns:[{
                mData : "orderNo",
                sDefaultContent : "",
                sTitle : "订单号"
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
                mData : "id",
                sDefaultContent : "",
                sTitle : "操作",
                mRender: function(value, type ,data){
                    var opts = [];
                    if (status == 1) {//检查中
                        opts.push('<a style="margin: 1px;" class="btn edit" href="javascript:Order.reject('+ value + ')"><i class="icon-remove-circle"></i></a><br>');
                        opts.push('<a class="btn edit" href="javascript:Order.showMobileOrderDetail('+ value + ')"><i class="icon-eye-open"></i></a>');
                    } else if (status == 2) {//维修中
                        opts.push('<a class="btn edit" href="javascript:Order.mobileFixed('+ value + ')"><i class="icon-check"></i></a>');
                    } else if (status == 4) {
                        opts.push('<a class="btn edit" href="javascript:Order.showMobileOrderDetail('+ value + ')"><i class="icon-eye-open"></i></a>');
                    }
                    return opts.join(" ");
                }

            }]
        })
    },

    reject: function(id) {
        App.confirm("确定要拒单？", function(){
            jQuery.ajax({
                url: appCtx + "/order/reject/" + id,
                type: 'post',
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        jQuery("#orderTable").dataTable().fnDraw();
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

    showMobileOrderDetail: function (id) {
        jQuery.dialog({
            title: '订单详情',
            content: 'url:'+appCtx+"/mobile/vendor/order/detail/"+id
        });
    },
    showPOI: function (poi) {
        jQuery.dialog({
            title: '地图',
            content: '<div id="googleMap" style="width:280px;height:220px;"></div>'
        });
        var map = initializeMap("googleMap");
        google.maps.event.clearListeners(map,'click');
        if (!_isNull(poi)) {
            var latlng = poi.split(",");
            var myCenter=new google.maps.LatLng(latlng[1],latlng[0]);
            placeMarker(map, myCenter);
        }
    },
    mobileChecked: function () {
        var orderId = jQuery("#orderId").val();
        var description = jQuery("#description").val();
        var type = jQuery("#type").val();
        if (_isNull(description)) {
            App.alert("描述信息必填");
            return false;
        }
        jQuery.ajax({
            url: appCtx + "/order/checked",
            type: 'post',
            data: {"orderId":orderId, "type":type, "description":description},
            dataType:'json',
            success: function(json) {
                if (json.code == "0") {
                    App.goToPage(appCtx + '/mobile/vendor/order/list?status=2');
                    App.alert("确认完成");
                } else {
                    App.alert(json.message);
                }
            },
            error: function(xhr, textStatus, errorThrown){
                alert(errorThrown);
            }
        });

    },
    mobileFixed: function (id) {
        jQuery.confirm({
            title: '维修中',
            // columnClass: 'col-md-8 col-md-offset-2',
            content: 'url:'+appCtx+"/mobile/vendor/order/fixed/"+id,
            confirmButton: '确定',
            cancelButton: '返回',
            confirm: function(){
                var fixRemark = jQuery("#fixRemark").val();
                if (_isNull(fixRemark)) {
                    App.alert("维修描述必填");
                    return false;
                }
                // var orderId = jQuery("#orderId").val();
                jQuery.ajax({
                    url: appCtx + "/order/fixed",
                    type: 'post',
                    data: {"orderId":id,"fixRemark":fixRemark},
                    dataType:'json',
                    success: function(json) {
                        if (json.code == "0") {
                            App.goToPage(appCtx + '/mobile/vendor/order/list?status=2');
                            App.alert("确认维修成功");
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
    getMobileTableData_customer: function (flag) {
        jQuery('#orderTable').dataTable({
            sAjaxSource: appCtx + "/order/loadData",
            // oLanguage: {
            //     sUrl: appCtx + '/flatpoint/js/zh_CN.json',
            // },
            oLanguage: {
                "sProcessing": "处理中...",
                "sLengthMenu": "_MENU_ 记录/页",
                "sZeroRecords": "没有匹配的记录",
                "sInfo": "共 _TOTAL_ 条",
                "sInfoEmpty": "共 0 条",
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
            // bRetrieve:true,
            // bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "flag", "value":flag});
                aoData.push({"name": "branchId", "value":jQuery('#branchId').val()});
            },
            aoColumns:[{
                mData : "orderNo",
                sDefaultContent : "",
                sTitle : "订单号"
            },{
                mData : "createdTime",
                sDefaultContent : "",
                sTitle : "订单时间",
                mRender: function(value, type ,data) {
                    return moment(value).format("YYYY-MM-DD HH:mm:ss");
                }
            },{
                mData : "status",
                sDefaultContent : "",
                sTitle : "订单状态",
                mRender: function(value, type ,data){
                    var html = '';
                    if (value == 0) {
                        html = '派单中';
                    } else if (value == 1) {
                        html = '检查中';
                    } else if (value == 2) {
                        html = '维修中';
                    } else if (value == 3) {
                        html = '审核中';
                    } else if (value == 4) {
                        html = '已完成';
                    }
                    return html;
                }
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "操作",
                mRender: function(value, type ,data){
                    var opts = [];
                    if (data.status == 4 && _isNull(data.score)) {//未评价
                        opts.push('<a style="margin: 1px;" class="btn edit" href="javascript:App.goToPage(appCtx + \'/mobile/order/feedback/'+value+'\')">评价</a><br>');
                    }
                    opts.push('<a class="btn edit" href="javascript:Order.showMobileOrderDetail_customer('+ value + ')"><i class="icon-eye-open"></i></a>');
                    return opts.join(" ");
                }

            }]
        })
    },
    showMobileOrderDetail_customer: function (id) {
        jQuery.dialog({
            title: '订单详情',
            content: 'url:'+appCtx+"/mobile/order/detail/"+id
        });
    },
    feedback: function () {
        var orderId = jQuery("#orderId").val();
        var vendorId = jQuery("#vendorId").val();
        var feedback = jQuery.trim(jQuery("#feedback").val());
        var score = jQuery("#score").val();
        if (_isNull(feedback)) {
            App.alert("评价信息必填");
            return false;
        }
        jQuery.ajax({
            url: appCtx + "/order/feedback",
            type: 'post',
            data: {"orderId":orderId, "vendorId":vendorId,"score":score, "feedback":feedback},
            dataType:'json',
            success: function(json) {
                if (json.code == "0") {
                    App.goToPage(appCtx + '/mobile/order/list?flag=1');
                    App.alert("感谢评价！");
                } else {
                    App.alert(json.message);
                }
            },
            error: function(xhr, textStatus, errorThrown){
                alert(errorThrown);
            }
        });

    },

}


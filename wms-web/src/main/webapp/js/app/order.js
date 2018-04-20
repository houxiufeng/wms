var Order = {
    getTableData: function(index){
        jQuery('#orderTable'+index).dataTable({
            sAjaxSource: appCtx + "/order/loadData",
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
            // bRetrieve:true,
            bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"lip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "orderNo", "value":jQuery("#orderNo").val()});
                aoData.push({"name": "startTime", "value":jQuery("#startTime").val()});
                aoData.push({"name": "endTime", "value":jQuery("#endTime").val()});
                aoData.push({"name": "feedbackFlag", "value":jQuery("input[name='feedbackFlag']:checked").val()});
                aoData.push({"name": "status", "value":index});
            },
            fnRowCallback : function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                if (aData.overed) {
                    jQuery(nRow).find("td").not("td:last").css({"background-color":"#ac193d","color":"#fff"});
                } else if (aData.warned) {
                    jQuery(nRow).find("td").not("td:last").css({"background-color":"#ff8f32","color":"#fff"});
                }
            },
            aoColumns:Order.getColumns(index)
        })
    },

    getColumns: function (index) {
        var columns = [{
            mData : "orderNo",
            sDefaultContent : "",
            sTitle : "Order no"
        },{
            mData : "customerName",
            sDefaultContent : "",
            sTitle : "Customer name"
        },{
            mData : "branchName",
            sDefaultContent : "",
            sTitle : "Branch name"

        },{
            mData : "productName",
            sDefaultContent : "",
            sTitle : "Product name",
            mRender: function(value, type ,data) {
                return value + "-" + data.productModel;
            }
        },{
            mData : "createdTime",
            sDefaultContent : "",
            sTitle : "Create time",
            mRender: function(value, type ,data) {
                return moment(value).format("YYYY-MM-DD HH:mm:ss");
            }
        },{
            mData : "typeName",
            sDefaultContent : "",
            sTitle : "Problem type"
        },{
            mData : "description",
            sDefaultContent : "",
            sTitle : "Description"
        },{
            mData : "vendorName",
            sDefaultContent : "",
            sTitle : "Engineer name"
        }];
        var scoreColumn = {
            mData : "score",
            sDefaultContent : "",
            sTitle : "Rate",
            mRender: function(value, type ,data){
                if (_isNull(value)) {
                    return "<span style='color: #bf3f20'>wait for customer rating</span>"
                } else {
                    if (value == 1) {
                        return "positive"
                    } else if (value == 2) {
                        return "normal"
                    } else {
                        return "negative"
                    }
                }
            }
        };
        var opts = {
            mData : "id",
            sDefaultContent : "",
            sTitle : "Operation",
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

        };
        if (index == 4) {
            columns.push(scoreColumn);
        }
        columns.push(opts);
        return columns;
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
                        App.alert("success!", function(){
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
        App.confirm("Cancel order？", function(){
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
                required: "customer can't be empty!"
            },
            branchId: {
                required: "branch can't be empty!"
            },
            type: {
                required: "problem type can't be empty!"
            },
            branchProductId: {
                required: "product can't be empty!"
            },
            remark: {
                required: "remarks can't be empty!！"
            },
            description: {
                required: "description can't be empty!！"
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
            title: 'Order detail',
            columnClass: 'col-md-6 col-md-offset-3',
            content: 'url:'+appCtx+"/order/detail/"+id
        });
    },
    assign: function (id) {
        jQuery.confirm({
            title: 'Assigning',
            columnClass: 'col-md-8 col-md-offset-2',
            content: 'url:'+appCtx+"/order/assign/"+id,
            confirmButton: 'OK',
            cancelButton: 'CLOSE',
            confirm: function(){
                var checkedObj = jQuery("#vendorTable").find(":checkbox:checked[name='vendorId']");
                if (checkedObj.length == 0) {
                    App.alert("please select one");
                    return false;
                }
                var vendorId = checkedObj.val();
                var privateOrder;
                App.confirm("Private OrderNo:<input id='privateOrder' type='text' maxlength='32'>", function () {
                    if (_isNull(jQuery("#privateOrder").val())) {
                        App.alert("private orderNo can't be empty!");
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
                                    App.alert("success");
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
            title: 'Checking',
            columnClass: 'col-md-6 col-md-offset-3',
            content: 'url:'+appCtx+"/order/check/"+id,
            confirmButton: 'OK',
            cancelButton: 'CLOSE',
            confirm: function(){
                var description = jQuery("#description").val();
                var type = jQuery("#type").val();
                if (_isNull(description)) {
                    App.alert("Description can't be empty!");
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
                            App.alert("Check done");
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
            title: 'Fixing',
            columnClass: 'col-md-6 col-md-offset-3',
            content: 'url:'+appCtx+"/order/fix/"+id,
            confirmButton: 'OK',
            cancelButton: 'CLOSE',
            confirm: function(){
                jQuery.ajax({
                    url: appCtx + "/order/fixed",
                    type: 'post',
                    data: {"orderId":id},
                    dataType:'json',
                    success: function(json) {
                        if (json.code == "0") {
                            Order.queryList();
                            App.alert("Fixed!");
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
            title: 'Auditing',
            columnClass: 'col-md-6 col-md-offset-3',
            content: 'Remarks:<textarea id="remark" style="resize:none;width: 100%;height: 150px;"></textarea>',
            confirmButton: 'OK',
            cancelButton: 'CLOSE',
            confirm: function(){
                jQuery.ajax({
                    url: appCtx + "/order/audited",
                    type: 'post',
                    data: {"orderId":id, "remark":jQuery.trim(jQuery("#remark").val())},
                    dataType:'json',
                    success: function(json) {
                        if (json.code == "0") {
                            Order.queryList();
                            App.alert("Audit done");
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
                "sProcessing": "Processing...",
                "sLengthMenu": "_MENU_ 记录/页",
                "sZeroRecords": "No records",
                "sInfo": "total _TOTAL_ ",
                "sInfoEmpty": "total 0 ",
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
                sTitle : "Order No"
            },{
                mData : "createdTime",
                sDefaultContent : "",
                sTitle : "Created time",
                mRender: function(value, type ,data) {
                    return moment(value).format("YYYY-MM-DD HH:mm:ss");
                }
            },{
                mData : "typeName",
                sDefaultContent : "",
                sTitle : "Problem type"
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "Operation",
                mRender: function(value, type ,data){
                    var opts = [];
                    if (status == 1) {//检查中
                        opts.push('<a style="margin: 1px;" class="btn edit" href="javascript:Order.reject('+ value + ')">Reject</a><br>');
                        opts.push('<a class="btn edit" href="javascript:Order.showMobileOrderDetail('+ value + ')">View</a>');
                    } else if (status == 2) {//维修中
                        opts.push('<a class="btn edit" href="javascript:Order.mobileFixed('+ value + ')">Fixed</a>');
                    } else if (status == 4) {
                        opts.push('<a class="btn edit" href="javascript:Order.showMobileOrderDetail('+ value + ')">View</a>');
                    }
                    return opts.join(" ");
                }

            }]
        })
    },

    reject: function(id) {
        App.confirm("Are you sure？", function(){
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
            title: 'Order detail',
            content: 'url:'+appCtx+"/mobile/vendor/order/detail/"+id
        });
    },
    showPOI: function (poi) {
        jQuery.dialog({
            title: 'Map',
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
            App.alert("Description can't be empty");
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
                    App.alert("checked");
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
            title: 'Fixing',
            // columnClass: 'col-md-8 col-md-offset-2',
            content: 'url:'+appCtx+"/mobile/vendor/order/fixed/"+id,
            confirmButton: 'OK',
            cancelButton: 'Back',
            confirm: function(){
                var fixRemark = jQuery("#fixRemark").val();
                if (_isNull(fixRemark)) {
                    App.alert("Fix remark can't be empty!");
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
                            App.alert("success");
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
                "sProcessing": "Processing...",
                "sLengthMenu": "_MENU_ 记录/页",
                "sZeroRecords": "No records",
                "sInfo": "total _TOTAL_ ",
                "sInfoEmpty": "total 0",
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
                sTitle : "Order No"
            },{
                mData : "createdTime",
                sDefaultContent : "",
                sTitle : "Created time",
                mRender: function(value, type ,data) {
                    return moment(value).format("YYYY-MM-DD HH:mm:ss");
                }
            },{
                mData : "status",
                sDefaultContent : "",
                sTitle : "Status",
                mRender: function(value, type ,data){
                    var html = '';
                    if (value == 0) {
                        html = 'Assigning';
                    } else if (value == 1) {
                        html = 'Checking';
                    } else if (value == 2) {
                        html = 'Fixing';
                    } else if (value == 3) {
                        html = 'Auditing';
                    } else if (value == 4) {
                        html = 'Complete';
                    }
                    return html;
                }
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "Operation",
                mRender: function(value, type ,data){
                    var opts = [];
                    if (data.status == 4 && _isNull(data.score)) {//未评价
                        opts.push('<a style="margin: 1px;" class="btn edit" href="javascript:App.goToPage(appCtx + \'/mobile/order/feedback/'+value+'\')">Feedback</a><br>');
                    }
                    opts.push('<a class="btn edit" href="javascript:Order.showMobileOrderDetail_customer('+ value + ')">View</a>');
                    return opts.join(" ");
                }

            }]
        })
    },
    showMobileOrderDetail_customer: function (id) {
        jQuery.dialog({
            title: 'Order detail',
            content: 'url:'+appCtx+"/mobile/order/detail/"+id
        });
    },
    feedback: function () {
        var orderId = jQuery("#orderId").val();
        var vendorId = jQuery("#vendorId").val();
        var feedback = jQuery.trim(jQuery("#feedback").val());
        var score = jQuery("#score").val();
        if (_isNull(feedback)) {
            App.alert("Feedback can't be empty!");
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
                    App.alert("Thanks for your feedback！");
                } else {
                    App.alert(json.message);
                }
            },
            error: function(xhr, textStatus, errorThrown){
                alert(errorThrown);
            }
        });

    }

}


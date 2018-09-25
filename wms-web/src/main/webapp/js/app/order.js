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
            mData : "engineerName",
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
                var opts = ['<a title="order detail" class="btn edit blue" href="javascript:Order.detail('+ value + ')"><i class="icon-eye-open"></i></a>'];
                if (index == 0) {
                    opts.push('<a title="assign order" class="btn edit blue" href="javascript:Order.assign('+ value + ')"><i class="icon-group"></i></a>');
                    opts.push('<a title="cancel order" class="btn edit blue" href="javascript:Order.cancel('+ value + ')"><i class="icon-remove"></i></a>');
                } else if (index == 1) {
                    opts.push('<a title="checked order" class="btn edit blue" href="javascript:Order.checked('+ value + ')"><i class="icon-ok"></i></a>');
                    opts.push('<a title="cancel order" class="btn edit blue" href="javascript:Order.cancel('+ value + ')"><i class="icon-remove"></i></a>');
                } else if (index == 2) {
                    opts.push('<a title="fixed order" class="btn edit blue" href="javascript:Order.fixed('+ value + ')"><i class="icon-ok"></i></a>');
                    opts.push('<a title="cancel order" class="btn edit blue" href="javascript:Order.cancel('+ value + ')"><i class="icon-remove"></i></a>');
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
                var checkedObj = jQuery("#engineerTable").find(":checkbox:checked[name='engineerId']");
                if (checkedObj.length == 0) {
                    App.alert("please select one");
                    return false;
                }
                var engineerId = checkedObj.val();
                var privateOrder;
                App.confirm("Private OrderNo:<input id='privateOrder' type='text' maxlength='32'>", function () {
                    if (_isNull(jQuery("#privateOrder").val())) {
                        App.alert("private orderNo can't be empty!");
                        return false;
                    } else {
                        privateOrder = jQuery("#privateOrder").val();
                        jQuery.ajax({
                            url: appCtx + "/order/assign/engineer",
                            type: 'post',
                            data: {"orderId":id, "engineerId":engineerId, "privateOrder":privateOrder},
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
                    "sPrevious": "Back",
                    "sNext": "Next",
                    "sLast": ">>"
                }
            },
            bSort: false,                        // 是否排序功能
            bFilter: false,                       // 过滤功能
            bPaginate: true,                     // 翻页功能
            bInfo: false,                         // 页脚信息
            bProcessing: true,                   //显示正在加载中
            bServerSide: true,                   //开启服务器模式
            // sPaginationType: "full_numbers",    //分页策略
            bAutoWidth: false,                  // 是否非自动宽度
            sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
            iDisplayLength: 4,
            // bRetrieve:true,
            // bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "status", "value":status});
                aoData.push({"name": "engineerId", "value":jQuery("#engineerId").val()});
            },
            aoColumns:[{
                mData : "orderNo",
                sDefaultContent : "",
                sTitle : "",
                sClass : "",
                mRender: function(value, type ,data){
                    var html = "<div onclick='Order.toOrderPage(" + data.id + "," + status + ");' style='text-align: left; line-height: 15px;background: #0072c6;color: white;margin: -5px;padding: 5px;'><p><span>OrderId:"
                        + data.orderNo + "</span><span style='float: right; margin-right: 10px;'>Date:"
                        + moment.unix(data.createdTime/1000).format("YYYY-MM-DD")
                        +  "</span></p>";
                    html += "<p>Problem Type:" + data.typeName + "</p>";
                    html += "<p>City:" + data.branchCity + "</p></div>";
                    return html;
                }
            }
            ]
        })
    },

    getEngineerMonthOrders: function (status) {
        jQuery('#m_orderTable'+status).dataTable({
            sAjaxSource: appCtx + "/mobile/engineer/report/monthOrders",
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
                    "sPrevious": "Back",
                    "sNext": "Next",
                    "sLast": ">>"
                }
            },
            bSort: false,                        // 是否排序功能
            bFilter: false,                       // 过滤功能
            bPaginate: true,                     // 翻页功能
            bInfo: false,                         // 页脚信息
            bProcessing: true,                   //显示正在加载中
            bServerSide: true,                   //开启服务器模式
            // sPaginationType: "full_numbers",    //分页策略
            bAutoWidth: false,                  // 是否非自动宽度
            sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
            iDisplayLength: 4,
            // bRetrieve:true,
            bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "status", "value":status});
                aoData.push({"name": "engineerId", "value":jQuery("#engineerId").val()});
                aoData.push({"name": "monthBegin", "value":jQuery("#monthBegin").val()});
                aoData.push({"name": "monthEnd", "value":jQuery("#monthEnd").val()});
            },
            aoColumns:[{
                mData : "orderNo",
                sDefaultContent : "",
                sTitle : "",
                sClass : "",
                mRender: function(value, type ,data){
                    var html = "<div onclick='Order.toReportOrderDetail(" + data.id + ");' style='text-align: left; line-height: 15px;background: #0072c6;color: white;margin: -5px;padding: 5px;'><p><span>OrderId:"
                        + data.orderNo + "</span><span style='float: right; margin-right: 10px;'>Date:"
                        + moment.unix(data.createdTime/1000).format("YYYY-MM-DD")
                        +  "</span></p>";
                    html += "<p>Problem Type:" + data.typeName + "</p>";
                    html += "<p>Description:" + data.description + "</p></div>";
                    return html;
                }
            }
            ]
        })
    },

    queryMobileOrderSum : function () {
        jQuery('#orderSumTable').dataTable({
            sAjaxSource: appCtx + "/mobile/engineer/orderSum",
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
                    "sPrevious": "Back",
                    "sNext": "Next",
                    "sLast": ">>"
                }
            },
            bSort: false,                        // 是否排序功能
            bFilter: false,                       // 过滤功能
            bPaginate: true,                     // 翻页功能
            bInfo: false,                         // 页脚信息
            bProcessing: true,                   //显示正在加载中
            bServerSide: true,                   //开启服务器模式
            // sPaginationType: "full_numbers",    //分页策略
            bAutoWidth: false,                  // 是否非自动宽度
            sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
            iDisplayLength: 4,
            // bRetrieve:true,
            // bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "engineerId", "value":jQuery("#engineerId").val()});
                aoData.push({"name": "startTime", "value":jQuery("#startTime").val()});
                aoData.push({"name": "endTime", "value":jQuery("#endTime").val()});
            },
            aoColumns:[{
                mData : "engineerId",
                sDefaultContent : "",
                sTitle : "",
                sClass : "",
                mRender: function(value, type ,data){
                    var html = "<div onclick='javascript:Order.toEngineerMonthList(" + data.engineerId + ",\"" + data.monthBegin +  "\",\"" + data.monthEnd +"\");' style='text-align: left; line-height: 15px;background: #0072c6;color: white;margin: -5px;padding: 5px;'>"
                        +"<p><span>Month:" +  data.year + "-" + data.month + "</span></p>";
                    html += "<p style='font-size: 12px;'><span>Checking order:" + data.checkingNum + "</span> <span style='margin-left: 25px;'>Fixing order:" +data.fixingNum + "</span> <span style='margin-left: 25px;'>Complete order:" + data.completeNum +"</span></p></div>";
                    return html;
                }
            }
            ]
        })
    },

    queryMobileOrderRate : function () {
        jQuery('#orderRateTable').dataTable({
            sAjaxSource: appCtx + "/mobile/engineer/orderRate",
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
                    "sPrevious": "Back",
                    "sNext": "Next",
                    "sLast": ">>"
                }
            },
            bSort: false,                        // 是否排序功能
            bFilter: false,                       // 过滤功能
            bPaginate: true,                     // 翻页功能
            bInfo: false,                         // 页脚信息
            bProcessing: true,                   //显示正在加载中
            bServerSide: true,                   //开启服务器模式
            // sPaginationType: "full_numbers",    //分页策略
            bAutoWidth: false,                  // 是否非自动宽度
            sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
            iDisplayLength: 4,
            // bRetrieve:true,
            // bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "engineerId", "value":jQuery("#engineerId").val()});
                aoData.push({"name": "startTime", "value":jQuery("#startTime").val()});
                aoData.push({"name": "endTime", "value":jQuery("#endTime").val()});
            },
            aoColumns:[{
                mData : "engineerId",
                sDefaultContent : "",
                sTitle : "",
                sClass : "",
                mRender: function(value, type ,data){
                    var html = "<div style='text-align: left; line-height: 15px;background: #0072c6;color: white;margin: -5px;padding: 5px;'>"
                        +"<p><span>Month:" +  data.year + "-" + data.month + "</span></p>";
                    html += "<p style='font-size: 12px;'><span style='color: darkgreen'>Good:</span>" + data.goodNum + " <span style='margin-left: 25px;color: yellow'>Normal:</span>" +data.normalNum + " <span style='margin-left: 25px; color: red'>Bad:</span>" + data.badNum +"</span></p></div>";
                    return html;
                }
            }
            ]
        })
    },

    refreshMobileOrderSum: function () {
        jQuery("#orderSumTable").dataTable().fnDraw();
    },
    refreshMobileOrderRate: function () {
        jQuery("#orderRateTable").dataTable().fnDraw();
    },
    toEngineerMonthList : function (engineerId,monthBegin,monthEnd) {
        App.goToPage(appCtx + "/mobile/engineer/report/monthList", {"engineerId":engineerId,"monthBegin":monthBegin,"monthEnd":monthEnd});
    },

    toOrderPage: function (orderId, status) {
        if (status == 1) {//checking order
            App.goToPage(appCtx + "/mobile/engineer/checkingOrder", {"orderId":orderId});
        } else if (status == 2) {//fixing order
            App.goToPage(appCtx + "/mobile/engineer/fixingOrder", {"orderId":orderId});
        }
    },
    toReportOrderDetail: function (orderId) {
        var data = {};
        data.orderId = orderId;
        data.engineerId = jQuery("#engineerId").val();
        data.monthBegin = jQuery("#monthBegin").val();
        data.monthEnd = jQuery("#monthEnd").val();
        App.goToPage(appCtx + "/mobile/engineer/reportOrderDetail", data);
    },

    reject: function(id) {
        App.confirm("Are you sure？", function(){
            jQuery.ajax({
                url: appCtx + "/order/reject/" + id,
                type: 'post',
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.goToPage(appCtx + '/mobile/engineer/order/list', {'status':1});
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
    showText: function (title, text) {
        jQuery.dialog({
            title: title,
            content: text
        });
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
                    App.goToPage(appCtx + '/mobile/engineer/order/list?status=2');
                    // App.alert("checked");
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
        var fixRemark = jQuery("#fixRemark").val();
        if (_isNull(fixRemark)) {
            App.alert("Fix remark can't be empty!");
            return false;
        }
        jQuery.ajax({
            url: appCtx + "/order/fixed",
            type: 'post',
            data: {"orderId":id,"fixRemark":fixRemark},
            dataType:'json',
            success: function(json) {
                if (json.code == "0") {
                    App.goToPage(appCtx + '/mobile/engineer/order/list?status=2');
                    App.alert("success");
                } else {
                    App.alert(json.message);
                }
            },
            error: function(xhr, textStatus, errorThrown){
                alert(errorThrown);
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
        var engineerId = jQuery("#engineerId").val();
        var feedback = jQuery.trim(jQuery("#feedback").val());
        var score = jQuery("#score").val();
        if (_isNull(feedback)) {
            App.alert("Feedback can't be empty!");
            return false;
        }
        jQuery.ajax({
            url: appCtx + "/order/feedback",
            type: 'post',
            data: {"orderId":orderId, "engineerId":engineerId,"score":score, "feedback":feedback},
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

    },

    getMobileCustomerOrder: function (status) {
        jQuery('#orderTable').dataTable({
            sAjaxSource: appCtx + "/order/loadData",
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
                    "sPrevious": "Back",
                    "sNext": "Next",
                    "sLast": ">>"
                }
            },
            bSort: false,                        // 是否排序功能
            bFilter: false,                       // 过滤功能
            bPaginate: true,                     // 翻页功能
            bInfo: false,                         // 页脚信息
            bProcessing: true,                   //显示正在加载中
            bServerSide: true,                   //开启服务器模式
            // sPaginationType: "full_numbers",    //分页策略
            bAutoWidth: false,                  // 是否非自动宽度
            sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
            iDisplayLength: 4,
            // bRetrieve:true,
            // bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "status", "value":status});
                aoData.push({"name": "branchId", "value":jQuery("#branchId").val()});
                aoData.push({"name": "feedbackFlag", "value":jQuery("#feedbackFlag").val()});
            },
            aoColumns:[{
                mData : "orderNo",
                sDefaultContent : "",
                sTitle : "",
                sClass : "",
                mRender: function(value, type ,data){
                    var html = "<div onclick='Order.toCustomerOrder(" + data.id + "," + status + ");' style='text-align: left; line-height: 15px;background: #0072c6;color: white;margin: -5px;padding: 5px;'><p><span>OrderId:"
                        + data.orderNo + "</span><span style='float: right; margin-right: 10px;'>Date:"
                        + moment.unix(data.createdTime/1000).format("YYYY-MM-DD")
                        +  "</span></p>";
                    html += "<p>Problem Type:" + data.typeName + "</p>";
                    html += "<p>City:" + data.branchCity + "</p></div>";
                    return html;
                }
            }
            ]
        })
    },

    toCustomerOrder: function (orderId, status) {
        if (status == 2) {//fixing order
            App.goToPage(appCtx + "/mobile/customer/fixingOrder", {"orderId":orderId});
        } else if (status == 4) {//rate order
            App.goToPage(appCtx + "/mobile/customer/rateOrder", {"orderId":orderId});
        }
    },

    mobileFeedback: function () {
        var orderId = jQuery("#orderId").val();
        var engineerId = jQuery("#engineerId").val();
        var feedback = jQuery.trim(jQuery("#feedback").val());
        var score = jQuery("#score").val();
        if (_isNull(feedback)) {
            alert("Feedback can't be empty!");
            return false;
        }
        jQuery.ajax({
            url: appCtx + "/order/feedback",
            type: 'post',
            data: {"orderId":orderId, "engineerId":engineerId,"score":score, "feedback":feedback},
            dataType:'json',
            success: function(json) {
                if (json.code == "0") {
                    App.goToPage(appCtx + '/mobile/customer/order/list', {'status':4,'feedbackFlag':1});
                } else {
                    alert(json.message);
                }
            },
            error: function(xhr, textStatus, errorThrown){
                alert(errorThrown);
            }
        });

    },

    queryMobileCustomerOrderSum : function () {
        jQuery('#orderSumTable').dataTable({
            sAjaxSource: appCtx + "/mobile/customer/orderSum",
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
                    "sPrevious": "Back",
                    "sNext": "Next",
                    "sLast": ">>"
                }
            },
            bSort: false,                        // 是否排序功能
            bFilter: false,                       // 过滤功能
            bPaginate: true,                     // 翻页功能
            bInfo: false,                         // 页脚信息
            bProcessing: true,                   //显示正在加载中
            bServerSide: true,                   //开启服务器模式
            // sPaginationType: "full_numbers",    //分页策略
            bAutoWidth: false,                  // 是否非自动宽度
            sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
            iDisplayLength: 4,
            // bRetrieve:true,
            // bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "branchId", "value":jQuery("#branchId").val()});
                aoData.push({"name": "startTime", "value":jQuery("#startTime").val()});
                aoData.push({"name": "endTime", "value":jQuery("#endTime").val()});
            },
            aoColumns:[{
                mData : "branchId",
                sDefaultContent : "",
                sTitle : "",
                sClass : "",
                mRender: function(value, type ,data){
                    var html = "<div onclick='javascript:Order.toCustomerMonthList(" + data.branchId + ",\"" + data.monthBegin +  "\",\"" + data.monthEnd +"\");' style='text-align: left; line-height: 15px;background: #0072c6;color: white;margin: -5px;padding: 5px;'>"
                        +"<p><span>Month:" +  data.year + "-" + data.month + "</span></p>";
                    html += "<p style='font-size: 12px;'><span>Processing order:" + (data.fixingNum + data.checkingNum) + "</span> <span style='margin-left: 25px;'>Complete order:" +data.completeNum + "</span> </p></div>";
                    return html;
                }
            }
            ]
        })
    },
    toCustomerMonthList : function (branchId,monthBegin,monthEnd) {
        App.goToPage(appCtx + "/mobile/customer/report/monthList", {"branchId":branchId,"monthBegin":monthBegin,"monthEnd":monthEnd});
    },

    getCustomerMonthOrders: function (status) {
        jQuery('#m_orderTable'+status).dataTable({
            sAjaxSource: appCtx + "/mobile/customer/report/monthOrders",
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
                    "sPrevious": "Back",
                    "sNext": "Next",
                    "sLast": ">>"
                }
            },
            bSort: false,                        // 是否排序功能
            bFilter: false,                       // 过滤功能
            bPaginate: true,                     // 翻页功能
            bInfo: false,                         // 页脚信息
            bProcessing: true,                   //显示正在加载中
            bServerSide: true,                   //开启服务器模式
            // sPaginationType: "full_numbers",    //分页策略
            bAutoWidth: false,                  // 是否非自动宽度
            sServerMethod: "POST",              //请求方式为post 主要为了防止中文参数乱码
            iDisplayLength: 4,
            // bRetrieve:true,
            bDestroy:true,
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "status", "value":status});
                aoData.push({"name": "branchId", "value":jQuery("#branchId").val()});
                aoData.push({"name": "monthBegin", "value":jQuery("#monthBegin").val()});
                aoData.push({"name": "monthEnd", "value":jQuery("#monthEnd").val()});
            },
            aoColumns:[{
                mData : "orderNo",
                sDefaultContent : "",
                sTitle : "",
                sClass : "",
                mRender: function(value, type ,data){
                    var html = "<div onclick='Order.toCustomerReportOrderDetail(" + data.id + ");' style='text-align: left; line-height: 15px;background: #0072c6;color: white;margin: -5px;padding: 5px;'><p><span>OrderId:"
                        + data.orderNo + "</span><span style='float: right; margin-right: 10px;'>Date:"
                        + moment.unix(data.createdTime/1000).format("YYYY-MM-DD")
                        +  "</span></p>";
                    html += "<p>Problem Type:" + data.typeName + "</p>";
                    html += "<p>Description:" + data.description + "</p></div>";
                    return html;
                }
            }
            ]
        })
    },

    toCustomerReportOrderDetail: function (orderId) {
        var data = {};
        data.orderId = orderId;
        data.branchId = jQuery("#branchId").val();
        data.monthBegin = jQuery("#monthBegin").val();
        data.monthEnd = jQuery("#monthEnd").val();
        App.goToPage(appCtx + "/mobile/customer/reportOrderDetail", data);
    }

}


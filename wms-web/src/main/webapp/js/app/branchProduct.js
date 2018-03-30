var BranchProduct = {
    getTableData: function(){
        jQuery('#branchProductTable').dataTable({
            sAjaxSource: appCtx + "/branch/product/loadData",
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
                aoData.push({"name": "branchId", "value":jQuery("#branchId").val()});
            },
            aoColumns:[{
                mData : "productName",
                sDefaultContent : "",
                sTitle : "产品名称"
            },{
                mData : "productModel",
                sDefaultContent : "",
                sTitle : "产品型号"

            },{
                mData : "position",
                sDefaultContent : "",
                sTitle : "位置"
            },{
                mData : "sn",
                sDefaultContent : "",
                sTitle : "序列号"
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "操作",
                mRender: function(value, type ,data){
                    return '<a class="btn edit blue" href="javascript:BranchProduct.genQrcode('+ value + ')"><i class="icon-qrcode"></i></a>\
                            <a class="btn edit blue" href="javascript:BranchProduct.edit('+ value + ',' + data.productId + ',\''+data.productName+'\',\''+data.sn+'\',\''+data.position+'\',\'' + data.poi+'\',\''+data.beginTime+'\',\''+data.endTime+'\')"><i class="icon-edit"></i></a>\
                            <a class="btn edit blue" href="javascript:BranchProduct.delete('+ value + ')"><i class="icon-trash"></i></a>';
                }

            }]
        })
    },
			
    // 查询按钮
    queryList : function(){
    	jQuery("#branchProductTable").dataTable().fnDraw();
    },

    saveOrUpdate : function () {
        if (_isNull(jQuery("#productId").val())) {
            App.alert("产品型号必填！");
            return false;
        }
        if (_isNull(jQuery("#branchProductForm").find(":input[name='sn']").val())) {
            App.alert("序列号必填！");
            return false;
        }
        if (_isNull(jQuery("#branchProductForm").find(":input[name='id']").val())) {
            BranchProduct.save();
        } else {
            BranchProduct.update();
        }
    },
    
    save: function() {
        var data = jQuery("#branchProductForm").serialize();
        if (!_isNull(jQuery("#point_x").val()) && !_isNull(jQuery("#point_y").val())) {
            data += "&poi=" + jQuery("#point_x").val() + "," + jQuery("#point_y").val();
        }
        jQuery.ajax({
            url: appCtx + "/branch/product/create",
            type: 'post',
            data:data,
            dataType:'json',
            success: function(json) {
                if (json.code == "0") {
                    App.alert("创建成功!", function(){
                        BranchProduct.queryList();
                        BranchProduct.reset();
                    });
                } else {
                    App.alert(json.message);
                }
            },
            error: function(xhr, textStatus, errorThrown){
                alert(errorThrown);
            }
        });
    },
    
    edit : function(id, productId, productName, sn, position, poi, beginTime, endTime){
        jQuery("#branchProductForm").find(":input[name='id']").val(id);
        jQuery("#branchProductForm").find(":input[name='sn']").val(sn);
        jQuery("#branchProductForm").find(":input[name='position']").val(position);
        jQuery("#branchProductForm").find("#productName").val(productName);
        jQuery("#branchProductForm").find("#productName").trigger("change");
        jQuery("#branchProductForm").find("#productId").val(productId);
        if (!_isNull(beginTime)) {
            var bt = moment.unix(beginTime/1000).format("YYYY-MM-DD");
            jQuery("#branchProductForm").find("#beginTime").val(bt);
        }
        if (!_isNull(endTime)) {
            var et = moment.unix(endTime/1000).format("YYYY-MM-DD");
            jQuery("#branchProductForm").find("#endTime").val(et);
        }
        if (!_isNull(poi)) {
            var latlng = poi.split(",");
            var myCenter=new google.maps.LatLng(latlng[1],latlng[0]);
            placeMarker(BranchProduct.gMap, myCenter);
        } else {
            if (!_isNull(marker)) {
                marker.setMap(null);
            }
            jQuery("#point_x").val("");
            jQuery("#point_y").val("");
        }
    },

    update: function() {
        var data = jQuery("#branchProductForm").serialize();
        if (!_isNull(jQuery("#point_x").val()) && !_isNull(jQuery("#point_y").val())) {
            data += "&poi=" + jQuery("#point_x").val() + "," + jQuery("#point_y").val();
        }
        jQuery.ajax({
            url: appCtx + "/branch/product/update/",
            type: 'post',
            data:data,
            dataType:'json',
            success: function(json) {
                if (json.code == "0") {
                    App.alert("修改成功!", function(){
                        BranchProduct.queryList();
                        BranchProduct.reset();
                    });
                } else {
                    App.alert(json.message);
                }
            },
            error: function(xhr, textStatus, errorThrown){
                alert(errorThrown);
            }
        });
    },
    
    delete: function(id) {
        App.confirm("确定要删除？", function(){
    		jQuery.ajax({
				url: appCtx + "/branch/product/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
                        BranchProduct.queryList();
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
        jQuery("#branchProductForm")[0].reset();
        jQuery("#branchProductForm").find(":input[name='id']").val("");
        jQuery("#productName option:first").attr("selected",true);
        jQuery("#productName").trigger("change");
        if (!_isNull(marker)) {
            marker.setMap(null);
        }
        jQuery("#point_x").val("");
        jQuery("#point_y").val("");
    },

    genQrcode: function (id) {
        var prefix = window.location.href.substring(0, window.location.href.indexOf("/main"))
        var url = prefix + "/mobile/branch/product/"+id;
        jQuery.confirm({
            title: '二维码',
            confirmButton: '打印',
            confirmButtonClass: 'btn red',
            cancelButton: '关闭',
            content: '<div id="qrcode"></div>',
            confirm: function(){
                jQuery("#qrcode").jqprint();
            }
        });
        jQuery("#qrcode").qrcode({
            render: "canvas", //canvas方式
            width: 200, //宽度
            height:200, //高度
            text: url //任意内容
        });
        console.log(url);
        var canvas = jQuery('#qrcode canvas');
        var img = canvas[0].toDataURL("image/png")
        jQuery('#qrcode').html("<img src='" + img + "'>")

    },

    getMobileTableData: function(){
        jQuery('#branchProductTable').dataTable({
            sAjaxSource: appCtx + "/branch/product/loadData",
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
            //sPaginationType: "bootstrap",
            sDom: '<"top">rt<"tableFooter"ip<"clear">',
            fnServerParams : function (aoData) {
                aoData.push({"name": "branchId", "value":jQuery("#branchId").val()});
            },
            aoColumns:[{
                mData : "productName",
                sDefaultContent : "",
                sTitle : "产品名称"
            },{
                mData : "productModel",
                sDefaultContent : "",
                sTitle : "产品型号"

            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "操作",
                mRender: function(value, type ,data){
                    return '<a class="btn edit" href="javascript:App.goToPage(appCtx + \'/mobile/branch/product/edit?branchProductId='+value+'\')"><i class="icon-edit"></i></a>';
                }

            }]
        })
    },

}


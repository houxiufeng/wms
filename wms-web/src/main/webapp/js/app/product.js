var Product = {
		getTableData: function(){
			jQuery('#productTable').dataTable({
		        sAjaxSource: appCtx + "/product/loadData",
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
		            aoData.push({"name": "model", "value":jQuery("#model").val()});
		        },
		        aoColumns:[{
		            mData : "name",
		            sDefaultContent : "",
		            sTitle : "产品名称"
		        },{
		            mData : "code",
		            sDefaultContent : "",
		            sTitle : "产品编码"
		            
		        },{
		            mData : "model",
		            sDefaultContent : "",
		            sTitle : "产品型号"
		        },{
		            mData : "type",
		            sDefaultContent : "",
		            sTitle : "产品类型",
                    mRender: function(value, type ,data){
                        var html = "";
		                if (value == 1) {
                            html = '空调';
                        } else if (value == 2) {
                            html = '升降机';
                        } else if (value == 3) {
                            html = '风扇';
                        }
                        return html;
                    }
		        },{
		            mData : "id",
		            sDefaultContent : "",
		            sTitle : "操作",
		            mRender: function(value, type ,data){
		            	return '<a class="btn edit blue" href="javascript:Product.edit('+ value + ')"><i class="icon-edit"></i></a>\
		            	        <a class="btn edit blue" href="javascript:Product.delete('+ value + ')"><i class="icon-trash"></i></a>';
		            }
		            
		        }]
		    })
		},
			
    // 查询按钮
    queryList : function(){
    	jQuery("#productTable").dataTable().fnDraw();
    },
    
    save: function() {
        var params = Product.buildValidate();
        if (params.form.valid()) {
            var data = jQuery("#productForm").serialize();
            var a1 = [];
            jQuery("#productImgTable").find("a.imgPath").each(function () {
                a1.push(jQuery(this).attr("data-imgpath"));
            });
            var a2 = [];
            jQuery("#productFileTable").find("a.filePath").each(function () {
                a2.push(jQuery(this).attr("data-filepath"));
            });
            data += encodeURI("&imgUrl="+ a1.join(",") +"&fileUrl=" + a2.join(","));
            jQuery.ajax({
                url: appCtx + "/product/create",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("创建成功!", function(){
                            App.goToPage(appCtx+"/product");
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
        App.goToPage(appCtx + "/product/edit/" + id);
    },

    update: function() {
        var params = Product.buildValidate();
        if (params.form.valid()) {
            var data = jQuery("#productForm").serialize();
            var a1 = [];
            jQuery("#productImgTable").find("a.imgPath").each(function () {
                a1.push(jQuery(this).attr("data-imgpath"));
            });
            var a2 = [];
            jQuery("#productFileTable").find("a.filePath").each(function () {
                a2.push(jQuery(this).attr("data-filepath"));
            });
            data += encodeURI("&imgUrl="+ a1.join(",") +"&fileUrl=" + a2.join(","));
            jQuery.ajax({
                url: appCtx + "/product/update/",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("修改成功!", function(){
                            App.goToPage(appCtx+"/product");
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
				url: appCtx + "/product/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
						Product.queryList();
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
            model: {
                required: true,
                eng:true
            },
            sn: {
                required: true,
                eng:true
            },
            maintenancePerson: {
                required:true
            },
            maintenancePhone: {
                required:true
            }
        }
        params.messages={
            name: {
                required: "产品名称必填"
            },
            code: {
                required: "产品编码必填",
                eng:"只能是字母数字"
            },
            model: {
                required: "产品型号必填",
                eng:"只能是字母数字"
            },
            sn: {
                required: "产品序列号必填",
                eng:"只能是字母数字"
            },
            maintenancePerson: {
                required:"维保人必填"
            },
            maintenancePhone: {
                required:"维保人电话必填"
            }
        }
        params.form = jQuery("#productForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        return params;
    },

    addBrand : function(id) {
        jQuery.confirm({
            title: '添加品牌!',
            confirmButton: '确定',
            confirmButtonClass: 'btn red',
            cancelButton: '关闭',
            content: '<div>品牌：<input type="text" id="brandName" maxlength="32"></div>',
            confirm: function(){
                var brandName = jQuery.trim(jQuery("#brandName").val());
                if (_isNull(brandName)) {
                    App.alert("品牌不能为空！");
                    return false;
                }
                jQuery.ajax({
                    url: appCtx + "/product/addBrand",
                    type: 'post',
                    dataType:'json',
                    data:{"brandName":brandName},
                    success: function(json) {
                        if (json.code == "0") {
                            Product.appendBrand(json.data);
                        } else {
                            App.alert(json.message);
                        }
                    },
                    error: function(xhr, textStatus, errorThrown){
                        App.alert(errorThrown);
                    }
                });
            }
        });
    },
    appendBrand : function(brandDict) {
        jQuery("#productName").prepend("<option value='"+brandDict.name+"'>"+brandDict.name+"</option>");
        jQuery("#productName option:first").attr("selected","selected");
    }
}


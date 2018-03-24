var Vendor = {
    getTableData: function(){
        jQuery('#vendorTable').dataTable({
            sAjaxSource: appCtx + "/vendor/loadData",
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
                sTitle : "供应商名称"
            },{
                mData : "code",
                sDefaultContent : "",
                sTitle : "供应商编码"

            },{
                mData : "phone",
                sDefaultContent : "",
                sTitle : "供应商电话"
            },{
                mData : "levelName",
                sDefaultContent : "",
                sTitle : "供应商级别"
            },{
                mData : "skillList",
                sDefaultContent : "",
                sTitle : "供应商能力",
                mRender: function(value, type ,data){
                    if (!_isNull(value)) {
                        return value.join(",");
                    }
                    return '';
                }
            },{
                mData : "score",
                sDefaultContent : "",
                sTitle : "累计评分",
                mRender: function(value, type ,data){
                    return '好评:' + data.goodScore + " 中评:" + data.moderateScore + " 差评:" + data.badScore;
                }
            },{
                mData : "status",
                sDefaultContent : "",
                sTitle : "上岗情况",
                mRender: function(value, type ,data){
                    if (value == 1) {
                        return "启用";
                    } else {
                        return "闲置";
                    }
                }
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "操作",
                mRender: function(value, type ,data){
                    return '<a class="btn edit blue" href="javascript:Vendor.edit('+ value + ')"><i class="icon-edit"></i></a>\
                            <a class="btn edit blue" href="javascript:Vendor.delete('+ value + ')"><i class="icon-trash"></i></a>';
                }

            }]
        })
    },

    getTableDataSimple: function(){
        jQuery('#vendorTable').dataTable({
            sAjaxSource: appCtx + "/vendor/loadData",
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
                aoData.push({"name": "status", "value":1});
            },
            aoColumns:[{
                mData : "name",
                sDefaultContent : "",
                sTitle : "供应商名称"
            },{
                mData : "levelName",
                sDefaultContent : "",
                sTitle : "供应商级别"
            },{
                mData : "skillList",
                sDefaultContent : "",
                sTitle : "供应商能力",
                mRender: function(value, type ,data){
                    if (!_isNull(value)) {
                        return value.join(",");
                    }
                    return '';
                }
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "选择",
                mRender: function(value, type ,data){
                    return '<input type="checkbox" name="vendorId" value="' + value +'">';
                }
            }]
        })
    },
			
    // 查询按钮
    queryList : function(){
    	jQuery("#vendorTable").dataTable().fnDraw();
    },
    
    save: function() {
        var params = Vendor.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/vendor/create",
                type: 'post',
                data:jQuery("#vendorForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("创建成功!", function(){
                            App.goToPage(appCtx+"/vendor");
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
        App.goToPage(appCtx + "/vendor/edit/" + id);
    },

    update: function() {
        var params = Vendor.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/vendor/update/",
                type: 'post',
                data:jQuery("#vendorForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("修改成功!", function(){
                            App.goToPage(appCtx+"/vendor");
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
				url: appCtx + "/vendor/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
						Vendor.queryList();
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
            }
        }
        params.messages={
            name: {
                required: "名称必填！"
            },
            code: {
                required: "编码必填！",
                eng:"必须是字母数字！"
            }
        }
        params.form = jQuery("#vendorForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        return params;
    }

}


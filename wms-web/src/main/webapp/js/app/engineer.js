var Engineer = {
    getTableData: function(iDisplayLength){
        iDisplayLength = iDisplayLength || 10;
        jQuery('#engineerTable').dataTable({
            sAjaxSource: appCtx + "/engineer/loadData",
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
                jQuery.cookie("engineer_iDisplayLength",aoData[4]["value"]);
            },
            aoColumns:[{
                mData : "name",
                sDefaultContent : "",
                sTitle : "Name"
            },{
                mData : "code",
                sDefaultContent : "",
                sTitle : "Code"

            },{
                mData : "phone",
                sDefaultContent : "",
                sTitle : "Phone"
            },{
                mData : "levelName",
                sDefaultContent : "",
                sTitle : "Degree"
            },{
                mData : "skillList",
                sDefaultContent : "",
                sTitle : "Capability",
                mRender: function(value, type ,data){
                    if (!_isNull(value)) {
                        return value.join(",");
                    }
                    return '';
                }
            },{
                mData : "score",
                sDefaultContent : "",
                sTitle : "Cumulative score",
                mRender: function(value, type ,data){
                    return '5 star:' + data.fiveStar + " 4 star:" + data.fourStar + " 3 star:" + data.threeStar+ " 2 star:" + data.twoStar+ " 1 star:" + data.oneStar;
                }
            },{
                mData : "status",
                sDefaultContent : "",
                sTitle : "Status",
                mRender: function(value, type ,data){
                    if (value == 1) {
                        return "On";
                    } else {
                        return "Off";
                    }
                }
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "Operation",
                mRender: function(value, type ,data){
                    return '<a title="edit engineer" class="btn edit blue" href="javascript:Engineer.edit('+ value + ')"><i class="icon-edit"></i></a>\
                            <a title="delete engineer" class="btn edit blue" href="javascript:Engineer.delete('+ value + ')"><i class="icon-trash"></i></a>';
                }

            }]
        })
    },

    getTableDataSimple: function(){
        jQuery('#engineerTable').dataTable({
            sAjaxSource: appCtx + "/engineer/loadData",
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
                aoData.push({"name": "status", "value":1});
            },
            aoColumns:[{
                mData : "name",
                sDefaultContent : "",
                sTitle : "Engineer Name"
            },{
                mData : "levelName",
                sDefaultContent : "",
                sTitle : "Degree"
            },{
                mData : "skillList",
                sDefaultContent : "",
                sTitle : "Capability",
                mRender: function(value, type ,data){
                    if (!_isNull(value)) {
                        return value.join(",");
                    }
                    return '';
                }
            },{
                mData : "id",
                sDefaultContent : "",
                sTitle : "#",
                mRender: function(value, type ,data){
                    return '<input type="checkbox" name="engineerId" value="' + value +'">';
                }
            }]
        })
    },
			
    // 查询按钮
    queryList : function(){
    	jQuery("#engineerTable").dataTable().fnDraw();
    },
    
    save: function() {
        var data = jQuery("#engineerForm").serialize();
        var phoneStr = buildPhoneStr(jQuery('#phone_pre').val(), jQuery('#phone').val());
        data += "&phone="+phoneStr;
        var params = Engineer.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/engineer/create",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!", function(){
                            App.goToPage(appCtx+"/engineer");
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
        App.goToPage(appCtx + "/engineer/edit/" + id);
    },

    update: function() {
        var data = jQuery("#engineerForm").serialize();
        var phoneStr = buildPhoneStr(jQuery('#phone_pre').val(), jQuery('#phone').val());
        data += "&phone="+phoneStr;
        var params = Engineer.buildValidate();
        if (params.form.valid()) {
            jQuery.ajax({
                url: appCtx + "/engineer/update/",
                type: 'post',
                data:data,
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!", function(){
                            App.goToPage(appCtx+"/engineer");
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
				url: appCtx + "/engineer/delete/" + id,
				type: 'post',
				dataType:'json',
				success: function(json) {
					if (json.code == "0") {
						Engineer.queryList();
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
                required: "name can't be empty!"
            },
            code: {
                required: "code can't be empty",
                eng:"invalid code input！"
            }
        }
        params.form = jQuery("#engineerForm");
        params.form.validate({
            rules:params.rules,
            messages: params.messages
        });
        return params;
    }

}


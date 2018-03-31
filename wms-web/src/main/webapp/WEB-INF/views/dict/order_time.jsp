<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
    .form_row {
        margin-bottom: 25px;
    }
</style>
<div class="well light_gray">
    <div class="top_bar">
        <ul class="breadcrumb">
            <li><a href="javascript:void(0);" style="color: #037dc5;font-size: 15px;">My basic setup</a> <span class="divider">/</span></li>
            <li class="active"><a href="javascript:void(0)" style="font-weight: 600;font-size: 13px;color: #037dc5;">Order status time setting</a></li>
        </ul>
    </div>

    <div class="well-content">
        <form id="orderTimeForm" style="margin-bottom: 10px;">
            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right" style="margin-top: 20px;">Assigning(h):</label>
                    <div class="field" style="text-align: center">
                        <div class="span5">
                            <span>Warn time</span>
                            <input name="assignWarn" class="span12" type="text" maxlength="32" onkeyup="onlyInt(this);" value="${setting.assignWarn}" readonly>
                        </div>
                        <div class="span5">
                            <span>Over time</span>
                            <input name="assignOver" class="span12" type="text" maxlength="32" onkeyup="onlyInt(this);" value="${setting.assignOver}" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right" style="margin-top: 20px;">Checking(h):</label>
                    <div class="field" style="text-align: center">
                        <div class="span5">
                            <span>Warn time</span>
                            <input name="checkWarn" class="span12" type="text" maxlength="32" onkeyup="onlyInt(this);" value="${setting.checkWarn}" readonly>
                        </div>
                        <div class="span5">
                            <span>Over time</span>
                            <input name="checkOver" class="span12" type="text" maxlength="32" onkeyup="onlyInt(this);" value="${setting.checkOver}" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form_row">
                <div class="span6">
                    <label class="field_name align_right" style="margin-top: 20px;">Fixing(h):</label>
                    <div class="field" style="text-align: center">
                        <div class="span5">
                            <span>Warn time</span>
                            <input name="fixWarn" class="span12" type="text" maxlength="32" onkeyup="onlyInt(this);" value="${setting.fixWarn}" readonly>
                        </div>
                        <div class="span5">
                            <span>Over time</span>
                            <input name="fixOver" class="span12" type="text" maxlength="32" onkeyup="onlyInt(this);" value="${setting.fixOver}" readonly>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form_row">
                <div class="span6">
                    <a id="btn_lock" href="javascript:void(0);" class="gray btn" style="margin-left: 45%"><i class="icon-lock"></i></a>
                    <a id="btn_unlock" href="javascript:void(0);" class="green btn" style="margin-left: 45%; display: none"><i class="icon-unlock"></i></a>
                </div>
            </div>
        </form>


    </div>
</div>

<script>
    jQuery(function ($) {
        $("#btn_lock").click(function () {
            $("#btn_unlock").show();
            $(this).hide();
            $("#orderTimeForm").find(":input").removeAttr("readonly");
        });
        $("#btn_unlock").click(function () {
            jQuery.ajax({
                url: appCtx + "/dict/orderTime/update",
                type: 'post',
                data:jQuery("#orderTimeForm").serialize(),
                dataType:'json',
                success: function(json) {
                    if (json.code == "0") {
                        App.alert("success!");
                        $("#btn_lock").show();
                        $("#btn_unlock").hide();
                        $("#orderTimeForm").find(":input").attr("readonly","readonly");
                    } else {
                        App.alert(json.message);
                    }
                },
                error: function(xhr, textStatus, errorThrown){
                    alert(errorThrown);
                }
            });
        });
    });
</script>

    

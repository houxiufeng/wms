<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="span12">
    
    <div class="well light_gray">
	    <div class="top_bar">
		    <ul class="breadcrumb">
			    <li><a href="javascript:void(0);"style="color: #037dc5;font-size: 15px;">My customer</a> <span class="divider">/</span></li>
			    <li><a href="javascript:void(0)"style="color: #037dc5;font-size: 15px;">Branch management</a><span class="divider">/</span></li>
			    <li class="active" style="font-weight: 600;font-size: 13px;color: #037dc5;">Edit Branch</li>
		    </ul>
	    </div>

        <div class="well-content">
        <form id="branchForm">
            
            <input type="hidden" name="id" value="${branch.id}" />
	        <div class="form_row">
		        <div class="span6">
			        <label class="field_name align_right"><span style="color: red">*</span>Customer name:</label>
			        <div class="field">
				        <select name="customerId" class="span12">
					        <c:forEach items="${customers}" var="item">
						        <option value="${item.id}" <c:if test="${item.id == branch.customerId}">selected</c:if>>${item.name}</option>
					        </c:forEach>
				        </select>
			        </div>
		        </div>
		        <div class="span6">
			        <label class="field_name align_right">Bind user:</label>
			        <div class="field">
				        <select name="userId" class="span12">
					        <c:forEach items="${users}" var="item">
					            <option value="${item.id}" <c:if test="${item.id == customer.userId}">selected</c:if>>${item.name}</option>
					        </c:forEach>
				        </select>
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6">
			        <label class="field_name align_right"><span style="color: red">*</span>Branch name:</label>
			        <div class="field">
				        <input name="name" class="span12" type="text" maxlength="100" value="${branch.name}">
			        </div>
		        </div>
		        <div class="span6">
			        <label class="field_name align_right"><span style="color: red">*</span>Branch code:</label>
			        <div class="field">
				        <input name="code" class="span12" type="text" maxlength="3" value="${branch.code}">
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6">
			        <label class="field_name align_right"><span style="color: red">*</span>Contact name:</label>
			        <div class="field">
				        <input name="contactPerson" class="span12" type="text" maxlength="64" value="${branch.contactPerson}">
			        </div>
		        </div>
		        <div class="span6">
			        <label class="field_name align_right"><span style="color: red">*</span>Contact phone:</label>
			        <div class="field">
				        <%--<input name="contactPhone" class="span12" type="text" maxlength="32" value="${branch.contactPhone}">--%>
				        <input id="contactPhone_pre" class="span3" type="text" maxlength="5"> -
				        <input id="contactPhone" class="span8" type="text" maxlength="14">
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6">
			        <label class="field_name align_right">Province/State:</label>
			        <div class="field">
				        <input name="province" class="span12" type="text" maxlength="64" value="${branch.province}">
			        </div>
		        </div>
		        <div class="span6">
			        <label class="field_name align_right">City:</label>
			        <div class="field">
				        <input name="city" class="span12" type="text" maxlength="64" value="${branch.city}">
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6">
			        <label class="field_name align_right"><span style="color: red">*</span>Branch address:</label>
			        <div class="field">
				        <input id="address" name="address" class="span10" type="text" maxlength="128" value="${branch.address}">
				        <a id="searchPoi" href="javascript:void(0)" class="btn dark_green">Search</a>
			        </div>
		        </div>
		        <div class="span6">
			        <label class="field_name align_right">Post code:</label>
			        <div class="field">
				        <input name="postCode" class="span12" type="text" maxlength="64" value="${branch.postCode}">
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span12">
			        <label class="field_name align_right" style="width: 9%">poi</label>
			        <div class="field" style="margin-left: 10%">
				        <div id="googleMap" style="height:350px;"></div>
			        </div>
		        </div>
	        </div>

	        <div class="form_row">
		        <div class="span6" >
			        <div class="field">
				        <a title="save" href="javascript:Branch.update();" class="btn red btn-large" style="width: 60px;"><i class="icon-save"></i></a>
				        <a title="back" href="javascript:App.goToPage(appCtx+'/branch')" class="btn dark_green btn-large" style="width: 60px;"><i class="icon-reply"></i></a>
			        </div>
		        </div>
	        </div>
            
        </form>
        </div>
    </div>
</div>
<script>
    jQuery(function ($) {
        var contactPhone = '${branch.contactPhone}';
        var p = splitPhoneStr(contactPhone);
        $("#contactPhone_pre").val(p[0]);
        $("#contactPhone").val(p[1]);

        var gMap = initializeMap("googleMap");
        var geocoder = new google.maps.Geocoder();
        $("#searchPoi").click(function () {
            var address = $("#address").val();
            if (!_isNull(address)) {
                geocoder.geocode( {'address': $.trim(address)}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        placeMarker(gMap, results[0].geometry.location);
                    } else {
                        App.alert("Geocode was not successful for the following reason: " + status);
                    }
                });
            }
        });
	    var poi = '${branch.poi}';
        if (!_isNull(poi)) {
            var latlng = poi.split(",");
            var myCenter=new google.maps.LatLng(latlng[1],latlng[0]);
            placeMarker(gMap, myCenter);
        } else {
            if (!_isNull(marker)) {
                marker.setMap(null);
            }
            cleanPointxy();
        }
    });
</script>
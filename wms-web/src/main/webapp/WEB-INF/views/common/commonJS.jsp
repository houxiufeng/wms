<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="${pageContext.request.contextPath}/flatpoint/js/jquery-1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/jquery-ui-1.10.3.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/bootstrap.js"></script>

<script src="${pageContext.request.contextPath}/flatpoint/js/library/jquery.collapsible.min.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/library/jquery.mousewheel.min.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/library/jquery.mCustomScrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/library/jquery.uniform.min.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/library/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/library/jquery.dataTables.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/library/chosen.jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/library/bootstrap-fileupload.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/library/bootstrap-datetimepicker.js"></script>
<script src="${pageContext.request.contextPath}/flatpoint/js/flatpoint_core.js"></script>

<script src="${pageContext.request.contextPath}/js/jquery-confirm.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
<script src="${pageContext.request.contextPath}/js/datatables.js"></script>
<script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/js/crypto-js.js"></script>
<script src="${pageContext.request.contextPath}/js/app.js"></script>

<script>
appCtx = "${pageContext.request.contextPath}";
var currentUserInfo;
if (!_isNull('${currentUserInfo}')) {
    currentUserInfo = JSON.parse('${currentUserInfo}');
}
//jQuery(document).on('click','a.treeNode', function(){//菜单高亮显示
//    jQuery("#main_navigation").find('.current_menu').removeClass('current_menu');
//    jQuery("#main_navigation").find('.subOpened').removeClass('subOpened');
//    jQuery(this).addClass("current_menu");
//
//});
</script>

<script type="text/javascript">
    var apiKey = 'AIzaSyCSGGRvP6E-ygnhbUDPYhDGQ3wacaGe2Mg';
//    var google_map_url = "http://maps.googleapis.com/maps/api/js?key=AIzaSyBzE9xAESye6Kde-3hT-6B90nfwUkcS8Yw&sensor=false";
    var google_map_url = "http://maps.googleapis.com/maps/api/js?key=" + apiKey +"&sensor=false";
    var mapJson;
    var marker;
    jQuery.ajax({
        url: "http://ip-api.com/json/",
        type: 'get',
        dataType:'json',
        async: false,
        success: function(json) {
            if(json.countryCode == 'CN') {
                google_map_url = "http://ditu.google.cn/maps/api/js?key=" + apiKey +"&sensor=false&language=zh-CN";
            }
            mapJson = json;
        },
        error: function(xhr, textStatus, errorThrown){}
    });
    document.writeln("<script src='" + google_map_url + "'><\/script>");
    function initializeMap(divId)
    {
        var mapProp = {
            // center:new google.maps.LatLng(51.508742,-0.120850),
            center:new google.maps.LatLng(mapJson.lat,mapJson.lon),
            zoom:10,
            mapTypeId:google.maps.MapTypeId.ROADMAP
        };
        var map=new google.maps.Map(document.getElementById(divId),mapProp);
        google.maps.event.addListener(map, 'click', function (event) { placeMarker(map, event.latLng); });
        return map;
    }
    function placeMarker(map, location) {
        if (!_isNull(marker)) {
            marker.setMap(null);
        }
        marker = new google.maps.Marker({
            position: location,
            map: map,
        });
        google.maps.event.addListener(marker, 'click', function() {
            infowindow.open(map,marker);
        });
        var infowindow = new google.maps.InfoWindow({
            content: 'Latitude: ' + location.lat() + '<br>Longitude: ' + location.lng()
        });
        infowindow.open(map,marker);
        jQuery("#point_y").val(location.lat());
        jQuery("#point_x").val(location.lng());
    }
    function cleanPointxy() {
        jQuery("#point_y").val("");
        jQuery("#point_x").val("");
    }
</script>



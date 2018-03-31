<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="span12">
    <div class="well light_gray">
        <div class="top_bar">
            <ul class="breadcrumb">
                <li><a href="javascript:void(0);" style="color: #037dc5;font-size: 15px;">System setting</a> <span class="divider">/</span></li>
                <li class="active"><a href="javascript:void(0)" style="font-weight: 600;font-size: 13px;color: #037dc5;">User management</a></li>
            </ul>
        </div>

        <div class="well-content">
          
          <!-- start -->
          <div class="table_options top_options">
              <div>
                  <span class="inline">Name:</span>
                  <input id="name" name="name" type="text" style="width:130px;">
                  <c:if test="${CURRENT_USER.roleCode == 'admin'}">
                      <span class="inline">Organization:</span>
                      <select id="organizationId" style="width: 150px;">
                          <option value="">All</option>
                          <c:forEach items="${organizations}" var="item">
                              <option value="${item.id}">${item.name}</option>
                          </c:forEach>
                      </select>
                  </c:if>
                  <a id="search" href="javascript:User.queryList()" class="dark_green btn" style="margin-left:10px;"><i class="icon-search"></i></a>
                  <a id="addUser" href="javascript:void(0);" class="dark_green btn"><i class="icon-plus"></i></a>
                  <%--<a id="refresh" href="javascript:void(0);" class="dark_green btn">刷新</a>--%>
              </div>
          </div>
          <!-- end   -->
          
          <div class="dataTables_wrapper">
            <table class="table table-striped table-bordered table-hover datatable"></table>
          </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/user.js"></script>
<script>
  jQuery(function($){
	  User.getTableData();
	  $("#addUser").click(function(){
	      App.goToPage('user/add');
	  });
	  
	  $("#refresh").click(function(){
		  App.goToPage(appCtx+"/user");
	  }); 
  });
</script>

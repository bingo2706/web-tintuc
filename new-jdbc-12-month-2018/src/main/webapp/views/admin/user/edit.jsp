<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<c:url var="APIurl" value="/api-admin-user"/>
<c:url var ="NewURL" value="/admin-users"/>
<html>
<head>
    <title>Chỉnh sửa bài viết</title>

</head>
<body>

<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
            </script>
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Trang chủ</a>
                </li>
                <c:if test="${not empty list.id}">
                     <li class="active">Chỉnh sửa tài khoản</li>              
                 </c:if>
                 <c:if test="${empty list.id}">
                     <li class="active">Thêm tài khoản</li>              
                 </c:if>
           
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                         
                        <c:if test="${not empty messageResponse}">
									<div style="width:200px;" class="alert alert-${alert}">
  										${messageResponse}
									</div>
						</c:if>
                        <form id="formSubmit">
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">Phân Quyền</label>
                                <div class="col-sm-9">
                                    <select class="form-control" id="roleCode" name="roleCode">
                                        <c:if test="${empty list.getRoleCode()}">
                                            <option value="">Chọn quyền</option>
                                            <c:forEach var="item" items="${role}">
                                                <option value="${item.code}">${item.name}</option>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${not empty list.getRoleCode()}">
                                            <option value="">Chọn loại quyền</option>
                                            <c:forEach var="item" items="${role}">
                                                <option value="${item.code}" <c:if test="${item.code == list.getRoleCode()}">selected="selected"</c:if>>
                                                        ${item.name}
                                                </option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </div>
                            </div>
                            <br/>
                            <br/>
                            
                            <br/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">Tên tài khoản</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="username" name="username" value="${list.username}"/>
                                </div>
                            </div>
                            <br/>
                            <br/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">Họ và tên</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="fullname" name="fullname" value="${list.fullname}"/>
                                </div>
                            </div>
                            <br/>
                            <br/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">Mật khẩu</label>
                                <div class="col-sm-9">
                                    <input type="password" class="form-control" id="password" name="password" value="${list.password}"/>
                                </div>
                            </div>
                            <br/>
                            <br/>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <c:if test="${not empty list.id}">
                                        <input type="button" class="btn btn-white btn-warning btn-bold" value="Cập nhật tài khoản" id="btnAddOrUpdateNew"/>
                                    </c:if>
                                    <c:if test="${empty list.id}">
                                        <input type="button" class="btn btn-white btn-warning btn-bold" value="Thêm tài khoản" id="btnAddOrUpdateNew"/>
                                    </c:if>
                                </div>
                            </div>
                            <input type="hidden" value="${list.id}" id="id" name="id"/>
                        </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
	
    $('#btnAddOrUpdateNew').click(function (e) {
        e.preventDefault();
        var data = {};
        var formData = $('#formSubmit').serializeArray();
        $.each(formData, function (i, v) {
            data[""+v.name+""] = v.value;
        });
 
        var id = $('#id').val();
        if (id == "") {
            addNew(data);
        } else {
            updateNew(data);
        }
        console.log(data);
    });
    function addNew(data) {
        $.ajax({
            url: '${APIurl}',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
            	window.location.href = "${NewURL}?type=edit&id="+result.id+"&message=insert_success";
            },
            error: function (error) {
            	window.location.href = "${NewURL}?type=list&maxPageItem=5&page=1&sortName=fullname&sortBy=asc&message=error_system";
            }
        });
    }
    function updateNew(data) {
        $.ajax({
            url: '${APIurl}',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function (result) {
            	window.location.href = "${NewURL}?type=edit&id="+result.id+"&message=update_success";
            },
            error: function (error) {
            	window.location.href = "${NewURL}?type=list&maxPageItem=5&page=1&sortName=fullname&sortBy=asc&message=error_system";
            }
        });
    }
    setTimeout(function () { $(".alert").fadeOut(); }, 1500);
</script>
</body>
</html>

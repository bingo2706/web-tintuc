<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp"%>
<c:url var="APIurl" value="/api-admin-category"/>
<c:url var ="NewURL" value="/admin-category"/>
<html>
<head>
    <title>Chỉnh sửa danh mục</title>

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
                     <li class="active">Chỉnh sửa danh mục</li>              
                 </c:if>
                 <c:if test="${empty list.id}">
                     <li class="active">Thêm danh mục</li>              
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
                                <label class="col-sm-3 control-label no-padding-right">Tên danh mục</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="name" name="name" value="${list.name}"/>
                                </div>
                            </div>
                            <br/>
                            <br/>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">Mã code</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="code" name="code" value="${list.code}"/>
                                </div>
                            </div>
           
                            
                            <br/>
                            <br/>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <c:if test="${not empty list.id}">
                                        <input type="button" class="btn btn-white btn-warning btn-bold" value="Cập nhật danh mục" id="btnAddOrUpdateNew"/>
                                    </c:if>
                                    <c:if test="${empty list.id}">
                                        <input type="button" class="btn btn-white btn-warning btn-bold" value="Thêm danh mục" id="btnAddOrUpdateNew"/>
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
            	window.location.href = "${NewURL}?type=list&message=error_system";
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
            	window.location.href = "${NewURL}?type=list&message=error_system";
            }
        });
    }
    setTimeout(function () { $(".alert").fadeOut(); }, 1500);
</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="/common/taglib.jsp"%>	
    <c:url var="APIurl" value="/api-admin-comment"/>
	<c:url var ="NewURL" value="/admin-category"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body>
          <div class="container mt-5">
            <div class="row">
                <div class="col-lg-8">
                    <!-- Post content-->
                    <article>
                        <!-- Post header-->
                        <header class="mb-4">
                            <!-- Post title-->
                            <h1 class="fw-bolder mb-1">${item.title}</h1>
                            <!-- Post meta content-->
                            <div class="text-muted fst-italic mb-2">${item.createdDate}</div>
                            <!-- Post categories-->
                            <a class="badge bg-secondary text-decoration-none link-light" href="#!">Web Design</a>
                            <a class="badge bg-secondary text-decoration-none link-light" href="#!">Freebies</a>
                        </header>
                        <!-- Preview image figure-->
                        <figure class="mb-4"><img style="width:800px;height:400px;object-fit:cover;" src="${item.thumbnail}" alt="..." /></figure>
                        <!-- Post content-->
                        <section class="mb-5">
                            <p class="fs-5 mb-4">${item.content}</p>
                            
                        </section>
                    </article>
                    <!-- Comments section-->
                    <section class="mb-5">
                        <div class="card bg-light">
                            <div class="card-body">
                                <!-- Comment form-->
                                <form id="formSubmit">
                                	<div class="input-group">
                                	
                                <textarea id="cmtInput" class="form-control" name="content" placeholder="Nhập bình luận" aria-label="Enter search term..." aria-describedby="button-search"></textarea>
                                <input type="hidden" id="userId" name = "userId" value=${USERMODEL.id }>
                                <input type="hidden" id="newId" name = "newId" value=${item.id }>
                                 <button class="btn btn-primary" id="button-save" type="button">Đăng</button>
                            	</div>
                                </form>
                                <br>
                                <!-- Comment with nested comments-->
                                <!-- Single comment-->
                               
                             
                             
                              <c:forEach var="cmt" items="${comment.getListResult()}">
                                  <c:if test="${cmt.parentId == 0}">
                                  	   <div class="d">
                                    <div style="display: flex !important;">
                                        <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>
                                        <div class="ms-3">
                                            <div class="fw-bold">${cmt.nameUser}</div>
                                            <span class="content content_${cmt.id }">${cmt.content}</span>
                                            <form id="formEdit_${cmt.id}" style="display:flex; flex-direction:row;">
                                            	<textarea name="content" class="content-input content_input_${cmt.id }" ></textarea>
                                            	<input type="hidden" id="userId" name = "userId" value=${USERMODEL.id }>
                                				<input type="hidden" id="newId" name = "newId" value=${item.id }>
                                				<input type="hidden" id="cmtId" name = "id" value=${cmt.id }>
                                            <button onclick="btn_editFunc(${cmt.id})" style="margin-left:10px; display:none;" type="submit" class="edit-button edit_${cmt.id }">Đăng</button>
                                            </form>
                                            
                                        </div>
                                    </div>
                                 
                                  <span onclick="DisplayEdit(${cmt.id})" class="text-edit">Trả lời</span>
                                  <c:if test="${USERMODEL.id == cmt.userId }">
                                  	 <span onclick="Edit(${cmt.id})"class="text-delete">Chỉnh sửa</span>
                                     <span onclick="deleteFunc(${cmt.id})" class="text-delete">Xóa</span>
                                  </c:if>
                                 
                                  <br>
                                  <form id="editForm_${cmt.id}">
                                  	    <div class="box-edit boxedit_${cmt.id}">
                                    <input class="edit-input" name="content" placeholder="Nhập bình luận của bạn" type="text" >
                                     <input type="hidden" id="userId" name = "userId" value=${USERMODEL.id }>
                               	     <input type="hidden" id="newId" name = "newId" value=${item.id }>
                               	    <input type="hidden" id="parentId" name = "parentId" value=${cmt.id }>
                                    <button type="submit" onclick="editClick(${cmt.id})"  class="edit-button">Đăng</button>
                                  </div>
                                  </form>
                              
                                  <c:forEach var="child" items="${child.getListResult() }">
                                    <c:if test="${child.parentId == cmt.id }">
                                    	  <div style="margin-left: 40px;" class="box-con mt-4">
                                  <div style="display:flex;">
                                  	<div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>
                                    <div class="ms-3">
                                        <div class="fw-bold">${child.nameUser }</div>
                                        <div class="content content_${child.id}">${child.content }</div>
                                        <form id="formEdit_${child.id}" style="display:flex; flex-direction:row;">
                                        	 <textarea name="content" class="content-input content_input_${child.id }" ></textarea>
                                        	 <input type="hidden" id="userId" name = "userId" value=${USERMODEL.id }>
                                				<input type="hidden" id="newId" name = "newId" value=${item.id }>
                                				<input type="hidden" id="cmtId" name = "id" value=${child.id }>
                                          <button onclick="btn_editFunc(${child.id})" style="margin-left:10px; display:none;" type="submit" class="edit-button edit_${child.id }">Đăng</button>
                                        </form>
                                       
                                    </div>
		                                  </div>
		                                   <c:if test="${USERMODEL.id == child.userId }">
		                                   <div style = "margin-left: 55px;">
		                                   	<span onclick="Edit(${child.id})"class="text-delete">Chỉnh sửa</span>
                                             <span onclick="deleteFunc(${child.id})" class="text-delete">Xóa</span>
		                                   </div>
                                        	
                                        </c:if>                 
		                                </div>
                                    </c:if>
                                  </c:forEach>
                                
                              
                                </div>
                                  </c:if>
                              </c:forEach>
            
                            </div>
                        </div>
                    </section>
                </div>
                <!-- Side widgets-->
                <div class="col-lg-4">
                    <!-- Search widget-->
                    <div class="card mb-4">
                        <div class="card-header">Search</div>
                        <div class="card-body">
                            <div class="input-group">
                                <input id="cmtInput" class="form-control" type="text" placeholder="Enter search term..." aria-label="Enter search term..." aria-describedby="button-search" />
                                <button class="btn btn-primary" id="button-search" type="button">Go!</button>
                            </div>
                        </div>
                    </div>
                    <!-- Categories widget-->
                    <div class="card mb-4">
                        <div class="card-header">Categories</div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-6">
                                    <ul class="list-unstyled mb-0">
                                        <li><a href="#!">Web Design</a></li>
                                        <li><a href="#!">HTML</a></li>
                                        <li><a href="#!">Freebies</a></li>
                                    </ul>
                                </div>
                                <div class="col-sm-6">
                                    <ul class="list-unstyled mb-0">
                                        <li><a href="#!">JavaScript</a></li>
                                        <li><a href="#!">CSS</a></li>
                                        <li><a href="#!">Tutorials</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Side widget-->
                    <div class="card mb-4">
                        <div class="card-header">Mô tả</div>
                        <div class="card-body">${item.shortdescription}</div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
        if(localStorage.getItem("flag") == 1){
        	var id = localStorage.getItem("id");
        	var elmnt = document.querySelector(".content_"+id);
        
        	
        	localStorage.setItem("flag", 0);
        	
        }
        
        $('#button-save').click(function (e) {
            e.preventDefault();
            var data = {};
            var formData = $('#formSubmit').serializeArray();
            $.each(formData, function (i, v) {
                data[""+v.name+""] = v.value;
            });          
            addNew(data);
             console.log("ádsad")
        });
        $('.edit-button').click(function (e) {
        	 e.preventDefault();    
        });
        function btn_editFunc(id){
        	var data = {};
        	var formData = $('#formEdit_'+id).serializeArray();
            $.each(formData, function (i, v) {
                data[""+v.name+""] = v.value;
            });
            
            updateNew(data);
        }
        function editClick(id){
        	 var data = {};
             var formData = $('#editForm_'+id).serializeArray();
             $.each(formData, function (i, v) {
                 data[""+v.name+""] = v.value;
             });     
             addNew(data);
        }
        function addNew(data) {
            $.ajax({
                url: '${APIurl}',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                dataType: 'json',
                success: function (result) {
                	localStorage.setItem("flag", 1);
                	window.location.href = "http://localhost:8080/new-jdbc-12-month-2018/detail?action=detail&id="+${item.id};
                	
                },
                error: function (error) {
                	window.location.href = "http://localhost:8080/new-jdbc-12-month-2018/trang-chu?&page=1&maxPageItem=6&sortName=title&sortBy=asc";
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
                	localStorage.setItem("flag", 1);
                	localStorage.setItem("id", data.id);
                	window.location.href = "http://localhost:8080/new-jdbc-12-month-2018/detail?action=detail&id="+${item.id};
                },
                error: function (error) {
                	window.location.href = "http://localhost:8080/new-jdbc-12-month-2018/trang-chu?&page=1&maxPageItem=6&sortName=title&sortBy=asc";
                }
            });
        }
    	function deleteFunc(id){
    		var data = {};
    		var ids = [];
    		ids[0] = id;
    		data['ids'] = ids;
    		deleteNew(data);
    	}
    	function deleteNew(data) {
            $.ajax({
                url: '${APIurl}',
                type: 'DELETE',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (result) {
                    window.location.href = "http://localhost:8080/new-jdbc-12-month-2018/detail?action=detail&id="+${item.id};
                },
                error: function (error) {
                	window.location.href = "http://localhost:8080/new-jdbc-12-month-2018/trang-chu?&page=1&maxPageItem=6&sortName=title&sortBy=asc";
                }
            });
        }
        function DisplayEdit(id) {
        	document.querySelector(".boxedit_"+id).style.display = "block";
           
        }
        function Edit(id) {
            document.querySelector(".content_"+id).style.display = "none";
            document.querySelector(".content_input_"+id).style.display = "block";
            document.querySelector(".edit_"+id).style.display = "block";
            document.querySelector(".content_input_"+id).innerText = document.querySelector(".content_"+id).textContent
        }
        </script>
</body>
</html>
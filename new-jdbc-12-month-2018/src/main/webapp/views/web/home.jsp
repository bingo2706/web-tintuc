<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trang chủ</title>
</head>
<body>
<div class="row">

        <div class="col-lg-3">

          <h1 class="my-4">Shop Name</h1>
          <div class="list-group">
          <c:forEach  var="item" items="${categories}">
           <a href="/new-jdbc-12-month-2018/trang-chu?action=category&page=1&maxPageItem=6&sortName=title&sortBy=asc&id=${item.id}" class="list-group-item">${item.name}</a>
          </c:forEach>
           
            
          </div>

        </div>
        <!-- /.col-lg-3 -->
    
        <div class="col-lg-9">

          <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
            <ol class="carousel-indicators">
              <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
              <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
              <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner" role="listbox">
              <div class="carousel-item active">
                <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="First slide">
              </div>
              <div class="carousel-item">
                <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Second slide">
              </div>
              <div class="carousel-item">
                <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Third slide">
              </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="sr-only">Next</span>
            </a>
          </div>

          <div class="row">
            <c:forEach var="item" items="${list.getListResult()}">
               <div class="col-lg-4 col-md-6 mb-4">
              <div class="card h-100">
                <a href="/new-jdbc-12-month-2018/detail?action=detail&id=${item.id}"><img class="card-img-top" src="${item.thumbnail}" alt=""></a>
                <div class="card-body">
                  <h4 class="card-title">
                    <a href="/new-jdbc-12-month-2018/detail?action=detail&id=${item.id}">${item.title}</a>
                  </h4>
                  <h5>$24.99</h5>
                  <p class="card-text">${item.shortdescription}</p>
                </div>
                <div class="card-footer">
                  <small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>
                </div>
              </div>
            </div>
            </c:forEach>
         


          </div>
            <form action="<c:url value='/trang-chu'/>" id="formSubmit" method="get">
		             <ul style="justify-content: center;" class="pagination" id="pagination"></ul>
				   <input type="hidden" value="" id="page" name="page"/>
				   <input type="hidden" value="" id="maxPageItem" name="maxPageItem"/>
					<input type="hidden" value="" id="sortName" name="sortName"/>
				   <input type="hidden" value="" id="sortBy" name="sortBy"/>
            </form>
         
          <!-- /.row -->

        </div>
       
        <!-- /.col-lg-9 -->

      </div>
      <!-- /.row -->
      <script>
      $(function () {
  		var totalPages = ${list.totalPage};
  		var currentPage = ${list.page};
  		var limit = 6;
  		window.pagObj = $('#pagination').twbsPagination({
  			totalPages: totalPages,
  			visiblePages: 10,
  			startPage: currentPage,
  			onPageClick: function (event, page) {
  				if (currentPage != page) {
  					$('#maxPageItem').val(limit);
  					$('#page').val(page);
  					$('#sortName').val("title");
  					$('#sortBy').val("asc");
  					$('#formSubmit').submit();
  				}
  			}
  		});
  	});
      </script>
</body>
</html>
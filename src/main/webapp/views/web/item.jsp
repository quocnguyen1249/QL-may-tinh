<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TechZone - Laptop</title>
    <link rel="stylesheet" href="<c:url value='/template/bootstrap/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <script type="text/javascript" src="<c:url value='/template/jquery/jquery.min.js' />"></script>
	<script src="<c:url value='/template/paging/jquery.twbsPagination.js' />"></script>  
    <style>
        html {
            scroll-behavior: smooth;
        }
        .dropdown:hover .dropdown-menu {
            display: block;
            margin-top: 0;
        }
    </style>
</head>
<body>

    <nav class="navbar navbar-expand-lg shadow-sm border-bottom sticky-top">
	    <div class="container-fluid">
	        <a class="navbar-brand fw-bold text-primary fs-3 d-flex align-items-center gap-2" href="${pageContext.request.contextPath}/trang-chu?page=1&maxPageItem=12&sortName=name&sortBy=desc&type=list">
	             <img src="<c:url value='/template/web/assets/img/logo.png'/>" width="40">
	            TechZone
	        </a>
	        <form class="d-flex mx-3 flex-grow-1"
		      action="${pageContext.request.contextPath}/trang-chu"
		      method="get">
	            <input name="keyword"
	                   class="form-control me-2"
	                   type="search"
	                   placeholder="Bạn muốn mua laptop, PC, linh kiện gì hôm nay..."
	                   value="${param.keyword}">
	
	           	<input type="hidden" name="page" value="1">
				<input type="hidden" name="maxPageItem" value="12">
				<input type="hidden" name="sortName" value="name">
				<input type="hidden" name="sortBy" value="desc">
	            <button class="btn btn-primary">Tìm</button>
	        </form>
	        <div class="d-flex align-items-center gap-4">
	            <a href="#footer" class="text-decoration-none text-dark d-flex align-items-center gap-1">
	                <i class="fa-solid fa-phone text-success"></i>
	                <span class="fw-semibold">Hotline</span>
	            </a>
	
	            <a href="#footer" class="text-decoration-none text-dark d-flex align-items-center gap-1">
	                <i class="fa-solid fa-location-dot text-danger"></i>
	                <span class="fw-semibold">Địa chỉ</span>
	            </a>
	
	            <c:choose>
				    <c:when test="${empty sessionScope.USERENTITY}">
				        <a href="${pageContext.request.contextPath}/dang-nhap?action=login"
				           class="btn btn-outline-primary">
				            <i class="fa-solid fa-cart-shopping"></i> Giỏ hàng
				        </a>
				    </c:when>
				    <c:otherwise>
				        <a href="${pageContext.request.contextPath}/quan-ly-don-hang"
				           class="btn btn-outline-primary position-relative">
				            <i class="fa-solid fa-cart-shopping"></i> Giỏ hàng
				            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
				                ${orderCount}
				            </span>
				        </a>
				    </c:otherwise>
				</c:choose>
	            <c:choose>
	                <c:when test="${empty sessionScope.USERENTITY}">
	                    <a href="${pageContext.request.contextPath}/dang-nhap?action=login"
	                       class="btn btn-outline-primary">
	                        <i class="fa-solid fa-right-to-bracket"></i> Đăng nhập
	                    </a>
	                </c:when>
	
	                <c:otherwise>
	                    <div class="dropdown">
	                        <div class="d-flex align-items-center gap-2 dropdown-toggle"
	                             role="button" data-bs-toggle="dropdown">
	
	                            <c:choose>
	                                <c:when test="${empty sessionScope.USERENTITY.id || empty sessionScope.USERENTITY.avatar}">
	                                    <img src="https://i.pravatar.cc/40"
	                                         class="rounded-circle" width="40">
	                                </c:when>
	                                <c:otherwise>
	                                    <img src="${sessionScope.USERENTITY.avatar}"
	                                         class="rounded-circle" width="40">
	                                </c:otherwise>
	                            </c:choose>
	                            <span class="fw-semibold">
	                                ${sessionScope.USERENTITY.fullName}
	                            </span>
	                        </div>
	
	                        <ul class="dropdown-menu dropdown-menu-end shadow">
							    <li>
							        <a class="dropdown-item"
							           href="${pageContext.request.contextPath}/tai-khoan">
							            <i class="fa-solid fa-user me-2"></i>
							            Thông tin cá nhân
							        </a>
							    </li>
							    <li><hr class="dropdown-divider"></li>
							
							    <li>
							        <a class="dropdown-item text-danger"
							           href="${pageContext.request.contextPath}/thoat?action=logout">
							            <i class="fa-solid fa-right-from-bracket me-2"></i>
							            Thoát
							        </a>
							    </li>
							</ul>
	                    </div>
	                </c:otherwise>
	            </c:choose>
	        </div>
	    </div>
	</nav>

    <div class="container">
        <c:set var="product" value="${model}" />
		<div class="row mt-4">
			    
			    <div class="col-md-5">
			        <img src="${product.image}" class="img-fluid border rounded" alt="${product.name}">
			    </div>
			
			    <div class="col-md-7">
			        <h2>${product.name}</h2>
			        <p class="text-muted">Thương hiệu: ${product.brand}</p>
			
			        <h4 class="text-danger">
			            <fmt:formatNumber value="${product.price * (1 - product.discount)}" type="number"/> đ
			        </h4>
			
			        <c:if test="${product.discount > 0}">
			            <p>
			                <del class="text-muted">
			                    <fmt:formatNumber value="${product.price}" type="number"/> đ
			                </del>
			                - <fmt:formatNumber value="${product.discount * 100}" maxFractionDigits="0"/>%
			            </p>
			        </c:if>
			
			        <p><strong>Mô tả:</strong> ${product.description}</p>
			
			        <p><strong>Bảo hành:</strong> ${product.warrantytime} tháng</p>
			        <p><strong>Số lượng còn:</strong> ${product.quantity}</p>
			
			        <!-- Nút -->
			        <div class="mt-3">
			            <button class="btn btn-primary" onclick="addToCart(${item.id})">
			                <i class="fa fa-shopping-cart"></i> Thêm vào giỏ
			            </button>
			        </div>
				</div>
	    </div>
	    <script>
			function addToCart(productId) {
			    fetch('${pageContext.request.contextPath}/api-order', {
			        method: 'POST',
			        headers: {'Content-Type': 'application/json'},
			        body: JSON.stringify({
			            productId: productId,
			            quantity: 1
			        })
			    })
			    .then(res => {
			        if (res.status === 401) {
			            window.location.href = '${pageContext.request.contextPath}/dang-nhap';
			            return null;
			        }
			        return res.json();
			    })
			    .then(data => {
			        if (data) alert(data.messageText);
			    });
			}
 		</script>
    </div>

    <%@ include file="/common/web/footer.jsp" %>
    <script src="<c:url value='/template/bootstrap/js/bootstrap.min.js' />"></script>
    <script src="<c:url value='/template/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>
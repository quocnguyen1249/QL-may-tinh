<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp"%>

<nav class="navbar navbar-expand-lg shadow-sm border-bottom sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold text-primary fs-3 d-flex align-items-center gap-2" href="${pageContext.request.contextPath}/trang-chu?page=1&maxPageItem=12&sortName=name&sortBy=desc&type=list">
             <img src="<c:url value='/template/web/assets/img/logo.png'/>" width="40">
            TechZone
        </a>
		<c:if test="${not empty model.page}">
        <form class="d-flex mx-3 flex-grow-1">
            <input name="keyword"
                   class="form-control me-2"
                   type="search"
                   placeholder="Bạn muốn mua laptop, PC, linh kiện gì hôm nay..."
                   value="${param.keyword}">

           	<input type="hidden" name="page" value="${empty model.page ? 1 : model.page}">
			<input type="hidden" name="maxPageItem" value="${empty model.maxPageItem ? 12 : model.maxPageItem}">
			<input type="hidden" name="sortName" value="${empty model.sortName ? 'name' : model.sortName}">
			<input type="hidden" name="sortBy" value="${empty model.sortBy ? 'desc' : model.sortBy}">
        
            <input type="hidden" name="minPrice" value="${empty model.minPrice ? 0 : model.minPrice}">
            <input type="hidden" name="maxPrice" value="${empty model.maxPrice ? 100000000 : model.maxPrice}">

            <c:forEach var="b" items="${model.brands}">
                <input type="hidden" name="brands" value="${b}">
            </c:forEach>

            <c:forEach var="f" items="${model.filters}">
                <input type="hidden" name="filters" value="${f}">
            </c:forEach>

            <button class="btn btn-primary">Tìm</button>
        </form>
		</c:if>
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
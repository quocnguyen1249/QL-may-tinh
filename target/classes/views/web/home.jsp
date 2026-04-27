<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp"%>

<c:url var="homeUrl" value="/trang-chu"/>

<div class="row mt-4">
    <p>Filters: ${model.filters}</p>

    <!-- FILTER -->
    <div class="col-md-3">
        <form action="${homeUrl}" method="get" class="border rounded p-3 shadow-sm">

            <!-- PRICE -->
            <div class="filter-group mb-3">
                <h6 class="fw-bold mb-2">Khoảng giá</h6>
                <input type="number" name="minPrice" class="form-control mb-2"
                       value="${empty model.minPrice ? 0 : model.minPrice}"
                       placeholder="Từ" min="0" step="100000">

                <input type="number" name="maxPrice" class="form-control"
                       value="${empty model.maxPrice ? 100000000 : model.maxPrice}"
                       placeholder="Đến" min="0" step="100000">
            </div>
            <hr>

            <!-- BRAND -->
            <div class="filter-group mb-3">
                <h6 class="fw-bold mb-2">Hãng</h6>
                <c:forEach var="b" items="${brands}">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="brands"
                               value="${b}"
                               <c:if test="${not empty model.brands and fn:contains(fn:join(model.brands, ','), b)}">checked</c:if>>
                        <label class="form-check-label">${b}</label>
                    </div>
                </c:forEach>
            </div>
            <hr>

            <!-- CPU -->
            <div class="filter-group mb-3">
                <h6 class="fw-bold mb-2">Tên CPU</h6>
                <c:set var="cpus" value="${fn:split('M2,M3,M4', ',')}" />
                <c:forEach var="cpu" items="${cpus}">
                    <c:set var="cpuVal" value="cpu:${cpu}" />
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="filters"
                               value="${cpuVal}"
                               <c:if test="${not empty model.filters and fn:contains(fn:join(model.filters, ','), cpuVal)}">checked</c:if>>
                        <label class="form-check-label">Apple ${cpu}</label>
                    </div>
                </c:forEach>
            </div>
            <hr>

            <!-- RAM -->
            <div class="filter-group mb-3">
                <h6 class="fw-bold mb-2">Dung lượng RAM</h6>
                <c:set var="rams" value="${fn:split('8GB,16GB,24GB', ',')}" />
                <c:forEach var="r" items="${rams}">
                    <c:set var="ramVal" value="ram:${r}" />
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="filters"
                               value="${ramVal}"
                               <c:if test="${not empty model.filters and fn:contains(fn:join(model.filters, ','), ramVal)}">checked</c:if>>
                        <label class="form-check-label">${r}</label>
                    </div>
                </c:forEach>
            </div>
            <hr>

            <!-- SSD -->
            <div class="filter-group mb-3">
                <h6 class="fw-bold mb-2">Dung lượng SSD</h6>
                <c:set var="ssds" value="${fn:split('256GB,512GB', ',')}" />
                <c:forEach var="s" items="${ssds}">
                    <c:set var="ssdVal" value="ssd:${s}" />
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="filters"
                               value="${ssdVal}"
                               <c:if test="${not empty model.filters and fn:contains(fn:join(model.filters, ','), ssdVal)}">checked</c:if>>
                        <label class="form-check-label">${s}</label>
                    </div>
                </c:forEach>
            </div>
            <hr>

            <!-- SCREEN -->
            <div class="filter-group mb-3">
                <h6 class="fw-bold mb-2">Kích thước màn hình</h6>
                <c:set var="screens" value="${fn:split('11-13.9,15-15.9', ',')}" />
                <c:forEach var="sc" items="${screens}">
                    <c:set var="screenVal" value="screen:${sc}" />
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="filters"
                               value="${screenVal}"
                               <c:if test="${not empty model.filters and fn:contains(fn:join(model.filters, ','), screenVal)}">checked</c:if>>
                        <label class="form-check-label">${sc}</label>
                    </div>
                </c:forEach>
            </div>

            <input type="hidden" name="page" value="1">
            <input type="hidden" name="maxPageItem" value="${model.maxPageItem}">
            <input type="hidden" name="sortName" value="${model.sortName}">
            <input type="hidden" name="sortBy" value="${model.sortBy}">

            <button type="submit" class="btn btn-primary w-100 mt-3">
                <i class="fa-solid fa-magnifying-glass"></i> Tìm kiếm
            </button>
        </form>
    </div>

    <!-- PRODUCT LIST -->
    <div class="col-md-9 border rounded p-3 shadow-sm d-flex flex-column" style="min-height: 600px;">

        <!-- SORT -->
        <div class="d-flex align-items-center gap-2 flex-wrap mb-3">
            <span class="fw-semibold me-2">Sắp xếp theo:</span>

            <button type="button"
                    class="btn btn-sm ${model.sortName=='name' && model.sortBy=='asc' ? 'btn-primary' : 'btn-outline-secondary'}"
                    onclick="sortData('name','asc')">A → Z</button>

            <button type="button"
                    class="btn btn-sm ${model.sortName=='name' && model.sortBy=='desc' ? 'btn-primary' : 'btn-outline-secondary'}"
                    onclick="sortData('name','desc')">Z → A</button>

            <button type="button"
                    class="btn btn-sm ${model.sortName=='price' && model.sortBy=='asc' ? 'btn-primary' : 'btn-outline-secondary'}"
                    onclick="sortData('price','asc')">Giá tăng</button>

            <button type="button"
                    class="btn btn-sm ${model.sortName=='price' && model.sortBy=='desc' ? 'btn-primary' : 'btn-outline-secondary'}"
                    onclick="sortData('price','desc')">Giá giảm</button>

            <button type="button"
                    class="btn btn-sm ${model.sortName=='createdDate' && model.sortBy=='desc' ? 'btn-primary' : 'btn-outline-secondary'}"
                    onclick="sortData('createdDate','desc')">Mới nhất</button>
        </div>

        <!-- LIST -->
        <div class="flex-grow-1">
		    <div class="row row-cols-1 row-cols-md-4 g-4">
		        <c:forEach var="item" items="${model.listResult}">
		            <div class="col">
		                <div class="card h-100 shadow-sm">
		
		                    <!-- CLICK IMAGE -->
		                    <a href="<c:url value='/item?id=${item.id}'/>">
		                        <img src="${item.image}" class="card-img-top" 
		                             style="height:180px; object-fit:cover;">
		                    </a>
		
		                    <div class="card-body">
		                        <h6 class="text-muted">${item.brand}</h6>
		
		                        <!-- CLICK NAME -->
		                        <a href="<c:url value='/item?id=${item.id}'/>" 
		                           class="text-decoration-none text-dark">
		                            <p class="card-title small">${item.name}</p>
		                        </a>
		
		                        <h5 class="text-primary fw-bold">
		                            <fmt:formatNumber value="${item.price}" type="number"/> đ
		                        </h5>
		
		                        <button class="btn btn-outline-primary w-100"
		                                onclick="addToCart(${item.id})">
		                            Thêm vào giỏ
		                        </button>
		                    </div>
		
		                </div>
		            </div>
		        </c:forEach>
		    </div>
		</div>

        <!-- FORM PAGINATION -->
        <form id="formSubmit" method="get" action="${homeUrl}">
            <input type="hidden" id="page" name="page" value="${model.page}">
            <input type="hidden" id="maxPageItem" name="maxPageItem" value="${model.maxPageItem}">
            <input type="hidden" id="sortName" name="sortName" value="${model.sortName}">
            <input type="hidden" id="sortBy" name="sortBy" value="${model.sortBy}">
        
            <input type="hidden" name="minPrice" value="${empty model.minPrice ? 0 : model.minPrice}">
            <input type="hidden" name="maxPrice" value="${empty model.maxPrice ? 100000000 : model.maxPrice}">

            <c:forEach var="b" items="${model.brands}">
                <input type="hidden" name="brands" value="${b}">
            </c:forEach>

            <c:forEach var="f" items="${model.filters}">
                <input type="hidden" name="filters" value="${f}">
            </c:forEach>
            <nav class="mt-auto pt-3">
            		<ul class="pagination justify-content-center" id="pagination"></ul>
        		</nav>
        </form>

        <!-- PAGINATION -->
        
    </div>
</div>

<script>
function sortData(sortName, sortBy) {
    document.getElementById('sortName').value = sortName;
    document.getElementById('sortBy').value = sortBy;
    document.getElementById('page').value = 1;
    document.getElementById('formSubmit').submit();
  }
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

var totalPages = ${model.totalPage};
var currentPage = ${model.page};
var limit = ${model.maxPageItem};

$(function () {
    $('#pagination').twbsPagination({
        totalPages: totalPages,
        visiblePages: 10,
        startPage: currentPage,
        onPageClick: function (event, page) {
            if (currentPage != page) {
                $('#page').val(page);
                $('#maxPageItem').val(limit);
                $('#formSubmit').submit();
            }
        }
    });
});
</script>
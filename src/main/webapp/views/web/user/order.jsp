<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="orderUrl" value="/quan-ly-don-hang" />
<c:url var="apiUrl" value="/api-order-user" />

<section class="mt-4">
    <div class="row">

        <!-- SIDEBAR -->
        <div class="col-md-3">
            <div class="bg-white p-3 rounded shadow-sm">

                <div class="text-center mb-3">
                    <img src="${user.avatar != null ? user.avatar : 'https://i.pravatar.cc/100'}"
                        class="rounded-circle mb-2" width="90" height="90">

                    <div class="fw-semibold">
                        ${user.fullName}
                    </div>
                </div>

                <ul class="nav flex-column">

                    <li class="nav-item">
                        <a href="<c:url value='/tai-khoan'/>" class="nav-link text-dark">
                            <i class="fa-solid fa-user me-2"></i>
                            Thông tin tài khoản
                        </a>
                    </li>

                    <li class="nav-item">
                        <a href="<c:url value='/quan-ly-don-hang'/>" class="nav-link fw-semibold text-primary">
                            <i class="fa-solid fa-box me-2"></i>
                            Quản lý đơn hàng
                        </a>
                    </li>

                </ul>
            </div>
        </div>

        <!-- MAIN -->
        <div class="col-md-9">

            <h4 class="mb-3">Quản lý đơn hàng</h4>

            <div id="alertBox"></div>

            <!-- TABS -->
            <ul class="nav nav-tabs mb-4">

                <li class="nav-item">
                    <a class="nav-link ${currentStatus == 'PENDING' ? 'active' : ''}"
                        href="${orderUrl}?status=PENDING">
                        Chờ thanh toán
                    </a>
                </li>

                <li class="nav-item">
                    <a class="nav-link ${currentStatus == 'PAID' ? 'active' : ''}"
                        href="${orderUrl}?status=PAID">
                        Đã thanh toán
                    </a>
                </li>

            </ul>

            <!-- EMPTY -->
            <c:if test="${empty orders}">
                <div class="alert alert-info">
                    Không có đơn hàng nào
                </div>
            </c:if>

            <!-- LIST ORDER -->
            <c:forEach var="order" items="${orders}">

                <div class="card mb-3 shadow-sm" id="order-${order.id}">
                    <div class="card-body">

                        <div class="d-flex justify-content-between">

                            <div>
                                <strong>Mã đơn:</strong> #${order.id}
                                <br>

                                <small class="text-muted">
                                    <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" />
                                </small>
                            </div>

                            <div class="text-end">

                                <strong class="text-danger">
                                    <fmt:formatNumber value="${order.totalAmount}" type="number"
                                        groupingUsed="true" /> đ
                                </strong>
                                <br>

                                <c:choose>

                                    <c:when test="${order.status == 'PENDING'}">
                                        <span class="badge bg-warning text-dark">
                                            Chờ thanh toán
                                        </span>
                                    </c:when>

                                    <c:when test="${order.status == 'PAID'}">
                                        <span class="badge bg-success">
                                            Đã thanh toán
                                        </span>
                                    </c:when>

                                </c:choose>

                            </div>
                        </div>

                        <hr>

                        <div class="row align-items-center">

                            <div class="col-md-2">
                                <img src="${order.productShortResponse.image}" class="img-fluid rounded">
                            </div>

                            <div class="col-md-7">

                                <h6>${order.productShortResponse.name}</h6>

                                <small class="text-muted">
                                    Hãng: ${order.productShortResponse.brand}
                                </small>

                                <br>

                                <small>Số lượng: ${order.quantity}</small>

                            </div>

                            <div class="col-md-3 text-end">

                                <c:if test="${order.status == 'PENDING'}">

                                    <button class="btn btn-sm btn-success btn-pay" data-id="${order.id}">
                                        Thanh toán
                                    </button>

                                    <button class="btn btn-sm btn-outline-warning btn-edit"
                                        data-id="${order.id}" data-quantity="${order.quantity}">
                                        Sửa
                                    </button>

                                    <button class="btn btn-sm btn-outline-danger btn-delete"
                                        data-id="${order.id}">
                                        Xóa
                                    </button>

                                </c:if>

                            </div>
                        </div>

                    </div>
                </div>

            </c:forEach>


            <!-- EDIT FORM -->
            <div id="editContainer" class="card shadow p-4 mt-4" style="display:none;">

                <h5>Cập nhật đơn hàng</h5>

                <input type="hidden" id="editId">

                <div class="mb-3">

                    <label>Số lượng</label>

                    <input type="number" id="editQuantity" class="form-control">

                </div>

                <div class="mt-3">

                    <button class="btn btn-primary" id="btnUpdate">
                        Cập nhật
                    </button>

                    <button class="btn btn-secondary" id="btnCancel">
                        Hủy
                    </button>

                </div>

            </div>

        </div>
    </div>
</section>

<script>

    function showAlert(type, message) {
        $("#alertBox").html(
            '<div class="alert alert-' + type + '">' + message + '</div>'
        );
    }

    $(document).ready(function () {

        // PAY
        $(".btn-pay").click(function () {

            let id = $(this).data("id");

            if (!confirm("Bạn muốn thanh toán đơn hàng này?")) {
                return;
            }

            $.ajax({

                url: "${apiUrl}",
                type: "PUT",
                contentType: "application/json",

                data: JSON.stringify({
                    id: id
                }),

                success: function (response) {

                    showAlert(response.alert, response.messageText);
                    location.reload();

                },

                error: function (xhr) {

                    let res = xhr.responseJSON;
                    showAlert(res.alert, res.messageText);

                }

            });

        });


        // EDIT
        $(".btn-edit").click(function () {

            let id = $(this).data("id");
            let quantity = $(this).data("quantity");

            $("#editId").val(id);
            $("#editQuantity").val(quantity);

            $("#editContainer").slideDown();

            $("html, body").animate({
                scrollTop: $("#editContainer").offset().top
            }, 500);

        });


        // CANCEL
        $("#btnCancel").click(function () {
            $("#editContainer").slideUp();
        });


        // UPDATE
        $("#btnUpdate").click(function () {

            let data = {

                id: $("#editId").val(),
                quantity: $("#editQuantity").val()

            };

            $.ajax({

                url: "${apiUrl}",
                type: "PUT",
                contentType: "application/json",

                data: JSON.stringify(data),

                success: function (response) {

                    showAlert(response.alert, response.messageText);
                    location.reload();

                },

                error: function (xhr) {

                    let res = xhr.responseJSON;
                    showAlert(res.alert, res.messageText);

                }

            });

        });


        // DELETE
        $(".btn-delete").click(function () {

            let id = $(this).data("id");

            if (!confirm("Bạn có chắc muốn xóa đơn hàng này?")) {
                return;
            }

            $.ajax({

                url: "${apiUrl}",
                type: "DELETE",
                contentType: "application/json",

                data: JSON.stringify({
                    ids: [id]
                }),

                success: function (response) {

                    showAlert(response.alert, response.messageText);

                    $("#order-" + id).fadeOut(300, function () {
                        $(this).remove();
                    });

                },

                error: function (xhr) {

                    let res = xhr.responseJSON;
                    showAlert(res.alert, res.messageText);

                }

            });

        });

    });

</script>
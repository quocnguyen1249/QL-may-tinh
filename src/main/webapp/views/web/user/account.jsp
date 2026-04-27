<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp"%>

<c:url var="apiUserUrl" value="/api-web-user"/>

<div class="row mt-4">

    <!-- SIDEBAR -->
    <div class="col-md-3">
        <div class="bg-white p-3 rounded shadow-sm">

            <div class="text-center mb-3">

                <!-- AVATAR -->
                <img id="avatarPreview"
                     src="${user.avatar != null ? user.avatar : 'https://i.pravatar.cc/100'}"
                     class="rounded-circle mb-2"
                     width="90" height="90"
                     style="cursor:pointer; object-fit:cover;">

                <div class="fw-semibold">
                    ${user.fullName}
                </div>

            </div>

            <ul class="nav flex-column">
                <li class="nav-item mb-2">
                    <a href="<c:url value='/tai-khoan'/>"
                       class="nav-link active fw-semibold text-primary">
                        <i class="fa-solid fa-user me-2"></i>
                        Thông tin tài khoản
                    </a>
                </li>

                <li class="nav-item">
                    <a href="<c:url value='/quan-ly-don-hang'/>"
                       class="nav-link text-dark">
                        <i class="fa-solid fa-box me-2"></i>
                        Quản lý đơn hàng
                    </a>
                </li>
            </ul>

        </div>
    </div>

    <!-- CONTENT -->
    <div class="col-md-9">
        <div class="bg-white p-4 rounded shadow-sm">

            <h4 class="mb-4">Thông tin tài khoản</h4>

            <div id="messageBox"></div>

            <form id="updateUserForm">

                <input type="hidden" name="id" value="${user.id}" />
                <input type="hidden" name="avatar" id="avatarInput" value="${user.avatar}" />

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Họ và tên</label>
                        <input type="text"
                               name="fullName"
                               class="form-control"
                               value="${user.fullName}">
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Tên đăng nhập</label>
                        <input type="text"
                               class="form-control"
                               value="${user.username}"
                               readonly>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Email</label>
                        <input type="email"
                               name="email"
                               class="form-control"
                               value="${user.email}">
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">Số điện thoại</label>
                        <input type="text"
                               name="phone"
                               class="form-control"
                               value="${user.phone}">
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">CCCD</label>
                    <input type="text"
                           name="customercitizenid"
                           class="form-control"
                           value="${user.customercitizenid}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Địa chỉ</label>
                    <input type="text"
                           name="address"
                           class="form-control"
                           value="${user.address}">
                </div>

                <div class="text-end mt-3">
                    <button type="submit" class="btn btn-primary">
                        <i class="fa-solid fa-floppy-disk me-2"></i>
                        Cập nhật thông tin
                    </button>
                </div>

            </form>

        </div>
    </div>
</div>

<script>

// click avatar để đổi ảnh
document.getElementById("avatarPreview").addEventListener("click", function () {

    const url = prompt("Nhập URL avatar:");

    if (url) {
        document.getElementById("avatarPreview").src = url;
        document.getElementById("avatarInput").value = url;
    }

});

document.getElementById("updateUserForm").addEventListener("submit", function(e) {

    e.preventDefault();

    const data = {
        id: document.querySelector("input[name='id']").value,
        fullName: document.querySelector("input[name='fullName']").value,
        email: document.querySelector("input[name='email']").value,
        phone: document.querySelector("input[name='phone']").value,
        customercitizenid: document.querySelector("input[name='customercitizenid']").value,
        address: document.querySelector("input[name='address']").value,
        avatar: document.querySelector("#avatarInput").value
    };

    fetch("${apiUserUrl}", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(async response => {
        const result = await response.json();

        if (!response.ok) {
            throw result;
        }

        return result;
    })
    .then(result => {
        showMessage(result.alert, result.messageText);
    })
    .catch(error => {
        showMessage(error.alert || "danger",
                    error.messageText || "Có lỗi xảy ra!");
    });

});

function showMessage(type, message) {
    document.getElementById("messageBox").innerHTML =
        `<div class="alert alert-${type}">
            ${message}
        </div>`;
}

</script>
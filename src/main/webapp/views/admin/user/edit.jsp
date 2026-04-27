<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>

<c:url var="APIurl" value="/api-admin-user" />
<c:url var="UserURL" value="/admin-users" />

<div class="main-content">
    <div class="main-content-inner">

        <div class="breadcrumbs" id="breadcrumbs">
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Trang chủ</a>
                </li>
                <li class="active">
                    <c:choose>
                        <c:when test="${not empty model.id}">Cập nhật người dùng</c:when>
                        <c:otherwise>Thêm người dùng</c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </div>

        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <div id="alertBox"></div>

                    <form id="formSubmit">

                        <div class="form-group">
                            <label>Họ và tên</label>
                            <input type="text" class="form-control" name="fullName" value="${model.fullName}" />
                        </div>

                        <div class="form-group">
                            <label>Tên đăng nhập</label>
                            <input type="text" class="form-control" name="username" value="${model.username}" />
                        </div>

                        <div class="form-group">
                            <label>Mật khẩu</label>
                            <input type="password" class="form-control" name="password" placeholder="Nhập mật khẩu mới (bỏ trống nếu không đổi)" />
                        </div>

                        <div class="form-group">
                            <label>CCCD</label>
                            <input type="text" class="form-control" name="customercitizenid" value="${model.customercitizenid}" />
                        </div>

                        <div class="form-group">
                            <label>Avatar (URL)</label>
                            <input type="text" class="form-control" name="avatar" value="${model.avatar}" />
                        </div>

                        <div class="form-group">
                            <label>Số điện thoại</label>
                            <input type="text" class="form-control" name="phone" value="${model.phone}" />
                        </div>

                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" class="form-control" name="email" value="${model.email}" />
                        </div>

                        <div class="form-group">
                            <label>Địa chỉ</label>
                            <input type="text" class="form-control" name="address" value="${model.address}" />
                        </div>

                        <div class="form-group">
                            <label>Role ID</label>
                            <input type="number" class="form-control" name="roleId" value="${model.roleId}" />
                        </div>

                        <input type="hidden" id="id" name="id" value="${model.id}" />

                        <div class="form-group">
                            <button class="btn btn-primary" id="btnSave">
                                <c:choose>
                                    <c:when test="${not empty model.id}">Cập nhật người dùng</c:when>
                                    <c:otherwise>Thêm người dùng</c:otherwise>
                                </c:choose>
                            </button>
                            <a href="${UserURL}?type=list&page=1&maxPageItem=10&sortName=fullName&sortBy=asc"
                               class="btn btn-default">
                                Thoát
                            </a>
                        </div>

                    </form>

                </div>
            </div>
        </div>

    </div>
</div>

<script>
$('#btnSave').click(function (e) {
    e.preventDefault();

    var data = {};
    var formData = $('#formSubmit').serializeArray();

    $.each(formData, function (i, v) {
        if (v.value !== "") {
            data[v.name] = v.value;
        }
    });

    if (data.id) {
        updateUser(data);
    } else {
        addUser(data);
    }
});

function showAlert(type, message) {
    $('#alertBox').html(
        '<div class="alert alert-' + type + '">' + message + '</div>'
    );
}


function bindDataToForm(entity) {
    $('[name="id"]').val(entity.id);
    $('[name="fullname"]').val(entity.fullName);
    $('[name="username"]').val(entity.username);
    $('[name="customercitizenid"]').val(entity.customercitizenid);
    $('[name="avatar"]').val(entity.avatar);
    $('[name="phone"]').val(entity.phone);
    $('[name="email"]').val(entity.email);
    $('[name="address"]').val(entity.address);
    $('[name="roleId"]').val(entity.roleId);
}

function addUser(data) {
    $.ajax({
        url: '${APIurl}',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            showAlert(response.alert, response.messageText);
            if (response.entity) {
                bindDataToForm(response.entity); // hiển thị dữ liệu DB trả về
            }
        },
        error: function (xhr) {
            var res = xhr.responseJSON;
            showAlert(res.alert, res.messageText);
        }
    });
}

function updateUser(data) {
    $.ajax({
        url: '${APIurl}',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            showAlert(response.alert, response.messageText);
            if (response.entity) {
                bindDataToForm(response.entity); // cập nhật dữ liệu sau khi sửa
            }
        },
        error: function (xhr) {
            var res = xhr.responseJSON;
            showAlert(res.alert, res.messageText);
        }
    });
}
</script>
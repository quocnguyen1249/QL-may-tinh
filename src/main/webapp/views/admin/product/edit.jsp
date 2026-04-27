<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>

<c:url var="APIurl" value="/api-admin-product" />

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
                        <c:when test="${not empty model.id}">Cập nhật sản phẩm</c:when>
                        <c:otherwise>Thêm sản phẩm</c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </div>

        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">

                    <!-- ALERT -->
                    <div id="alertBox"></div>

                    <form id="formSubmit">

                        <div class="form-group">
                            <label>Tên sản phẩm</label>
                            <input type="text" class="form-control" name="name" value="${model.name}" />
                        </div>

                        <div class="form-group">
                            <label>Thương hiệu</label>
                            <input type="text" class="form-control" name="brand" value="${model.brand}" />
                        </div>

                        <div class="form-group">
                            <label>Hình ảnh (URL)</label>
                            <input type="text" class="form-control" name="image" value="${model.image}" />
                        </div>

                        <div class="form-group">
                            <label>Mô tả</label>
                            <textarea class="form-control" name="description">${model.description}</textarea>
                        </div>

                        <div class="form-group">
                            <label>Giá</label>
                            <input type="number" step="0.01" class="form-control" name="price" value="${model.price}" />
                        </div>

                        <div class="form-group">
                            <label>Số lượng</label>
                            <input type="number" class="form-control" name="quantity" value="${model.quantity}" />
                        </div>

                        <div class="form-group">
                            <label>Bảo hành (tháng)</label>
                            <input type="number" class="form-control" name="warrantytime" value="${model.warrantytime}" />
                        </div>

                        <div class="form-group">
                            <label>Giảm giá (0 → 1)</label>
                            <input type="number" step="0.01" class="form-control" name="discount" value="${model.discount}" />
                        </div>

                        <input type="hidden" id="id" name="id" value="${model.id}" />

                        <div class="form-group">
                            <button class="btn btn-primary" id="btnSave">
                                <c:choose>
                                    <c:when test="${not empty model.id}">Cập nhật sản phẩm</c:when>
                                    <c:otherwise>Thêm sản phẩm</c:otherwise>
                                </c:choose>
                            </button>
                            <a href="${ProductURL}?type=list&page=1&maxPageItem=10&sortName=name&sortBy=asc"
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
        updateProduct(data);
    } else {
        addProduct(data);
    }
});

function showAlert(type, message) {
    $('#alertBox').html(
        '<div class="alert alert-' + type + '">' + message + '</div>'
    );
}

/* GÁN DỮ LIỆU TRẢ VỀ TỪ DB LÊN FORM */
function bindDataToForm(entity) {
    $('[name="id"]').val(entity.id);
    $('[name="name"]').val(entity.name);
    $('[name="brand"]').val(entity.brand);
    $('[name="image"]').val(entity.image);
    $('[name="description"]').val(entity.description);
    $('[name="price"]').val(entity.price);
    $('[name="quantity"]').val(entity.quantity);
    $('[name="warrantytime"]').val(entity.warrantytime);
    $('[name="discount"]').val(entity.discount);
}

function addProduct(data) {
    $.ajax({
        url: '${APIurl}',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            showAlert(response.alert, response.messageText);
            if (response.entity) {
                bindDataToForm(response.entity); // HIỂN THỊ DỮ LIỆU DB TRẢ VỀ
            }
        },
        error: function (xhr) {
            var res = xhr.responseJSON;
            showAlert(res.alert, res.messageText);
        }
    });
}

function updateProduct(data) {
    $.ajax({
        url: '${APIurl}',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            showAlert(response.alert, response.messageText);
            if (response.entity) {
                bindDataToForm(response.entity); // HIỂN THỊ DỮ LIỆU DB SAU UPDATE
            }
        },
        error: function (xhr) {
            var res = xhr.responseJSON;
            showAlert(res.alert, res.messageText);
        }
    });
}
</script>
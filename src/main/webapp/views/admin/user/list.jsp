<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/common/taglib.jsp"%>

<c:url var="APIurl" value="/api-admin-user"/>
<c:url var="NewURL" value="/admin-users"/>

<div class="main-content">
<form action="<c:url value='/admin-users'/>" id="formSubmit" method="get">

    <div class="container mt-3">
        <h3 class="mb-3">Quản lý người dùng</h3>

        <!-- MESSAGE AREA -->
        <div id="messageArea"></div>

        <!-- BUTTON CONTROLS -->
        <div class="mb-3 text-end">
            <a class="btn btn-primary"
               href='<c:url value="/admin-users?type=edit"/>'>
                <i class="fa fa-plus-circle"></i> Thêm
            </a>

            <button id="btnDelete" type="button" class="btn btn-danger">
                <i class="fa fa-trash"></i> Xóa
            </button>
        </div>

        <!-- TABLE -->
        <table class="table table-bordered table-hover">
            <thead class="table-dark">
            <tr>
                <th style="width:50px">
                    <input type="checkbox" id="checkAll"/>
                </th>
                <th>ID</th>
                <th>Họ tên</th>
                <th>Username</th>
                <th>CCCD</th>
                <th>SĐT</th>
                <th>Email</th>
                <th>Role</th>
                <th style="width:120px">Thao tác</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="item" items="${model.listResult}">
                <tr>
                    <td>
                        <input type="checkbox" value="${item.id}"/>
                    </td>
                    <td>${item.id}</td>
                    <td>${item.fullName}</td>
                    <td>${item.username}</td>
                    <td>${item.customercitizenid}</td>
                    <td>${item.phone}</td>
                    <td>${item.email}</td>
                    <td>${item.roleId}</td>
                    <td>
                        <c:url var="editURL" value="/admin-users">
                            <c:param name="id" value="${item.id}" />
                            <c:param name="type" value="edit" />
                        </c:url>

                        <a class="btn btn-sm btn-primary"
                           href="${editURL}" title="Sửa">
                            <i class="fa fa-pencil"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- PAGINATION -->
        <ul class="pagination justify-content-center" id="pagination"></ul>

        <!-- HIDDEN -->
        <input type="hidden" id="page" name="page"/>
        <input type="hidden" id="maxPageItem" name="maxPageItem"/>
        <input type="hidden" id="sortName" name="sortName"/>
        <input type="hidden" id="sortBy" name="sortBy"/>
        <input type="hidden" id="type" name="type"/>

    </div>
</form>
</div>

<script>
    var totalPages = ${model.totalPage};
    var currentPage = ${model.page};
    var limit = 10;

    // PAGINATION
    $(function () {
        $('#pagination').twbsPagination({
            totalPages: totalPages,
            visiblePages: 5,
            startPage: currentPage,
            onPageClick: function (event, page) {
                if (currentPage != page) {
                    $('#maxPageItem').val(limit);
                    $('#page').val(page);
                    $('#sortName').val('fullName');
                    $('#sortBy').val('asc');
                    $('#type').val('list');
                    $('#formSubmit').submit();
                }
            }
        });
    });

    // CHECK ALL
    $('#checkAll').click(function () {
        $('tbody input[type="checkbox"]').prop('checked', this.checked);
    });

    // DELETE USING API
    $('#btnDelete').click(function () {

        var ids = $('tbody input[type="checkbox"]:checked').map(function () {
            return $(this).val();
        }).get();

        if (ids.length === 0) {
            showMessage("warning", "Chọn ít nhất 1 người dùng để xóa!");
            return;
        }

        $.ajax({
            url: '${APIurl}',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify({ ids: ids }),
            success: function (response) {
                showMessage(response.alert, response.messageText);
                if (response.alert === 'success') {
                    setTimeout(function () {
                        location.reload();
                    }, 2500);
                }
            },
            error: function (xhr) {
                var res = xhr.responseJSON;
                if (res && res.messageText) {
                    showMessage(res.alert, res.messageText);
                } else {
                    showMessage("danger", "Lỗi hệ thống!");
                }
            }
        });
    });

    // SHOW MESSAGE FUNCTION
    function showMessage(type, message) {
        $('#messageArea').html(
            '<div class="alert alert-' + type + '">' +
            message +
            '</div>'
        );
    }
</script>
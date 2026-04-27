<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>

<div class="container mt-5 mb-5">
    <div class="row justify-content-start">
        <div class="col-md-4">

            <!-- LOGIN FORM -->
            <div class="card shadow mb-4" id="loginForm" style="margin-top: 80px;">
                <div class="card-header bg-primary text-white text-center">
                    <h4>Đăng nhập</h4>
                </div>
                <div class="card-body">

                    <!-- Hiển thị lỗi từ server -->
                    <c:if test="${not empty apiResponse}">
                        <div class="alert alert-${apiResponse.alert}">
                            ${apiResponse.messageText}
                        </div>
                    </c:if>

                    <form action="<c:url value='/dang-nhap?action=login'/>" method="post">
                        <div class="mb-3">
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="bi bi-person"></i>
                                </span>
                                <input type="text"
                                       class="form-control"
                                       id="loginUser"
                                       name="username"
                                       placeholder="Tên đăng nhập" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i class="bi bi-lock"></i>
                                </span>
                                <input type="password"
                                       class="form-control"
                                       id="loginPassword"
                                       name="password"
                                       placeholder="Mật khẩu" required>
                                <span class="input-group-text"
                                      onclick="togglePassword('loginPassword', this)"
                                      style="cursor:pointer;">
                                    <i class="bi bi-eye-slash"></i>
                                </span>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary w-100 mb-2">
                            Đăng nhập
                        </button>
                    </form>

                    <div class="text-center mt-3">
                        <small>Bạn chưa có tài khoản?
                            <a href="#" onclick="showRegister()">Đăng ký</a>
                        </small>
                    </div>
                </div>
            </div>

            <!-- REGISTER FORM -->
            <div class="card shadow" id="registerForm" style="display:none; margin-top: 40px;">
                <div class="card-header bg-success text-white text-center">
                    <h4>Đăng ký</h4>
                </div>
                <div class="card-body">
                    <form id="registerFormSubmit">
                        <div class="mb-3">
                            <input type="text" class="form-control"
                                   id="fullName"
                                   placeholder="Họ và tên" required>
                        </div>

                        <div class="mb-3">
                            <input type="text" class="form-control"
                                   id="registerUser"
                                   placeholder="Tên đăng nhập" required>
                        </div>

                        <div class="mb-3">
                            <div class="input-group">
                                <input type="password" class="form-control"
                                       id="registerPassword"
                                       placeholder="Mật khẩu" required>
                                <span class="input-group-text"
                                      onclick="togglePassword('registerPassword', this)">
                                    <i class="bi bi-eye-slash"></i>
                                </span>
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="input-group">
                                <input type="password" class="form-control"
                                       id="confirmPassword"
                                       placeholder="Xác nhận mật khẩu" required>
                                <span class="input-group-text"
                                      onclick="togglePassword('confirmPassword', this)">
                                    <i class="bi bi-eye-slash"></i>
                                </span>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success w-100">Đăng ký</button>
                    </form>

                    <div class="text-center mt-3">
                        <small>Đã có tài khoản?
                            <a href="#" onclick="showLogin()">Đăng nhập</a>
                        </small>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<script>
function showRegister() {
    document.getElementById("loginForm").style.display = "none";
    document.getElementById("registerForm").style.display = "block";
}
function showLogin() {
    document.getElementById("registerForm").style.display = "none";
    document.getElementById("loginForm").style.display = "block";
}

function togglePassword(inputId, el) {
    const input = document.getElementById(inputId);
    const icon = el.querySelector("i");
    if (input.type === "password") {
        input.type = "text";
        icon.classList.remove("bi-eye-slash");
        icon.classList.add("bi-eye");
    } else {
        input.type = "password";
        icon.classList.remove("bi-eye");
        icon.classList.add("bi-eye-slash");
    }
}

/* CALL API REGISTER */
document.getElementById("registerFormSubmit").addEventListener("submit", async function(e) {
    e.preventDefault();

    const password = document.getElementById("registerPassword").value;
    const confirm = document.getElementById("confirmPassword").value;

    if(password !== confirm){
        alert("Mật khẩu xác nhận không khớp!");
        return;
    }

    const data = {
        fullName: document.getElementById("fullName").value,
        username: document.getElementById("registerUser").value,
        password: password
    };

    try {
        const res = await fetch("<c:url value='/api-web-user'/>", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });

        const result = await res.json();
        alert(result.messageText);

        if(result.alert === "success"){
            document.getElementById("loginUser").value = data.username;
            document.getElementById("loginPassword").value = data.password;
            showLogin();
        }
    } catch (err) {
        alert("Lỗi gọi API đăng ký");
        console.error(err);
    }
});
</script>
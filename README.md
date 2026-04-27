# QL Máy Tính - JSP/Servlet Demo Project

## 📖 Giới thiệu

Đây là dự án nhỏ phục vụ cho **bài tập thực hành môn Phân tích thiết kế hệ thống** tại **Đại học Công nghiệp TP.HCM**.  
Mục tiêu chính:

- Ôn tập và củng cố kiến thức về **JSP & Servlet**
- Áp dụng mô hình **MVC (Model - View - Controller)**
- Làm quen với **DTO (Request/Response)** và **Repository pattern** (thay cho DAO)
- Tích hợp **Bootstrap + JavaScript** để xây dựng giao diện
- Sử dụng **Lombok** và **MapStruct** để code nhanh, dễ bảo trì
- Chuẩn bị nền tảng để tiếp cận **Spring Boot** thuận lợi hơn

---

## ⚙️ Công nghệ sử dụng

![Java](https://img.shields.io/badge/java-21-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JSP](https://img.shields.io/badge/jsp-%23007396.svg?style=for-the-badge&logo=java&logoColor=white)
![Servlet](https://img.shields.io/badge/servlet-3.1-%23F89820.svg?style=for-the-badge&logo=java&logoColor=white)
![Bootstrap](https://img.shields.io/badge/bootstrap-5-%23563D7C.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23F7DF1E.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![MySQL](https://img.shields.io/badge/mysql-8.0-%234479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%232496ED.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-API%20Testing-FFC637?style=for-the-badge&logo=postman&logoColor=white)
![Maven](https://img.shields.io/badge/maven-%23C71A36.svg?style=for-the-badge&logo=apache-maven&logoColor=white)


---

## 🏗️ Kiến trúc hệ thống

Dự án được xây dựng theo mô hình **MVC**:

- **Model**: xử lý dữ liệu, tương tác database qua **Repository**
- **DTO**: truyền dữ liệu giữa các tầng (Request/Response)
- **View (JSP + Bootstrap)**: hiển thị giao diện người dùng
- **Controller (Servlet)**: xử lý request/response

Ngoài ra có:

- 🔐 Phân quyền user (admin / user)
- 📄 Phân trang dữ liệu (paging)
- ⚡ Tích hợp **Lombok + MapStruct** để giảm boilerplate code

---

## 📂 Cấu trúc thư mục

```
src/main/
├── java/com/qlmaytinh/
│   ├── constant/        # Các hằng số dùng chung
│   ├── controller/      # Servlet xử lý request/response
│   ├── dto/             # DTO (Request/Response)
│   ├── entity/          # Các entity ánh xạ database
│   ├── filter/          # Bộ lọc request (ví dụ: auth filter)
│   ├── mapper/          # MapStruct mapper
│   ├── paging/          # Xử lý phân trang
│   ├── repository/      # Repository thay cho DAO
│   ├── service/         # Business logic
│   ├── sort/            # Sắp xếp dữ liệu
│   └── Util/            # Tiện ích dùng chung
│
├── resources/
│   └── db.properties    # Cấu hình database
│   
│
└── webapp/
    ├── WEB-INF/         # Cấu hình web.xml, view bảo mật
    ├── common/          # JSP chung (header, footer)
    ├── decorators/      # SiteMesh decorators
    ├── template/        # Template layout
    ├── views/           # Các trang JSP chính
    └── index.jsp        # Trang chủ

```

---

## 🚀 Cách chạy project

### 1. Yêu cầu môi trường

- JDK 21
- Apache Tomcat 8.5+
- MySQL 8.0 (chạy bằng Docker)
- Maven

---

### 2. Clone project

```bash
git clone https://github.com/your-repo/QL-may-tinh.git
```

---

### 3. Cấu hình database

#### Tạo database:

```sql
CREATE DATABASE qlmaytinh;
```

#### Import dữ liệu:

```text
create_table.sql   -> tạo bảng
insert_table.sql   -> dữ liệu ban đầu
```

---

### 4. Cấu hình kết nối database

Mở file `src/main/resources/db.properties` và cập nhật thông tin của bạn vào your_username && your_password
```properties
driverName = com.mysql.jdbc.Driver
url = jdbc:mysql://localhost:3306/qlmaytinh
user = your_username
password = your_password
```

---

### 5. Build project

```bash
mvn clean install
```

---

### 6. Deploy lên Tomcat

- Import project vào IntelliJ / Eclipse
- Add server **Tomcat v8.5 Server at localhost-config**
- Deploy file `.war`

---

### 7. Truy cập hệ thống

```text
http://localhost:8080/QL-may-tinh
```

---

## 🧪 Test API

Dùng **Postman** để test các API:

- Login / Logout
- CRUD dữ liệu
- Phân trang
- Thêm / sửa / xóa

---

## 📚 Mục tiêu học tập

- Hiểu rõ luồng hoạt động của **Servlet**
- Nắm vững cách tổ chức project theo **MVC**
- Làm quen với **DTO + Repository**
- Chuẩn bị nền tảng để học **Spring Boot**

---

## 📌 Ghi chú

Dự án mang tính **học tập và thực hành**, không phải sản phẩm thương mại.  
Nếu có sai sót hoặc thiếu sót, mong được góp ý để cải thiện.

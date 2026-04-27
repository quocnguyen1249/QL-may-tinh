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

    <%@ include file="/common/web/header.jsp" %>

    <div class="container">
        <dec:body/>
    </div>

    <%@ include file="/common/web/footer.jsp" %>
    <script src="<c:url value='/template/bootstrap/js/bootstrap.min.js' />"></script>
    <script src="<c:url value='/template/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>
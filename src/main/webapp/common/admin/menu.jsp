<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>

<div id="sidebar" class="sidebar responsive ace-save-state">
    <script type="text/javascript">
        try{ace.settings.loadState('sidebar')}catch(e){}
    </script>

```
<ul class="nav nav-list">

    <!-- DASHBOARD -->
    <li>
        <a href="<c:url value='/admin-home'/>">
            <i class="menu-icon fa fa-bar-chart"></i>
            <span class="menu-text"> Dashboard </span>
        </a>
        <b class="arrow"></b>
    </li>

    <!-- PRODUCT MANAGEMENT -->
    <li>
        <a href="#" class="dropdown-toggle">
            <i class="menu-icon fa fa-laptop"></i>
            <span class="menu-text"> Quản lý sản phẩm </span>
            <b class="arrow fa fa-angle-down"></b>
        </a>
        <b class="arrow"></b>

        <ul class="submenu">
            <li>
                <c:url var="ProductListURL" value="/admin-products">
                    <c:param name="type" value="list"/>
                    <c:param name="page" value="1"/>
                    <c:param name="maxPageItem" value="10"/>
                    <c:param name="sortName" value="name"/>
                    <c:param name="sortBy" value="asc"/>
                </c:url>
                <a href="${ProductListURL}">
                    <i class="menu-icon fa fa-caret-right"></i>
                    Danh sách sản phẩm
                </a>
            </li>
        </ul>
    </li>

    <!-- USER MANAGEMENT -->
    <li>
        <a href="#" class="dropdown-toggle">
            <i class="menu-icon fa fa-users"></i>
            <span class="menu-text"> Quản lý người dùng </span>
            <b class="arrow fa fa-angle-down"></b>
        </a>
        <b class="arrow"></b>

        <ul class="submenu">
            <li>
                <c:url var="UserListURL" value="/admin-users">
                    <c:param name="type" value="list"/>
                    <c:param name="page" value="1"/>
                    <c:param name="maxPageItem" value="10"/>
                    <c:param name="sortName" value="username"/>
                    <c:param name="sortBy" value="asc"/>
                </c:url>
                <a href="${UserListURL}">
                    <i class="menu-icon fa fa-caret-right"></i>
                    Danh sách user
                </a>
            </li>
        </ul>
    </li>

</ul>

<div class="sidebar-toggle sidebar-collapse">
    <i class="ace-icon fa fa-angle-double-left ace-save-state"
       data-icon1="ace-icon fa fa-angle-double-left"
       data-icon2="ace-icon fa fa-angle-double-right"></i>
</div>
```

</div>

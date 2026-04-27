<%@include file="/common/taglib.jsp" %>
<c:redirect url="/trang-chu">
    <c:param name="type" value="list"/>
    <c:param name="page" value="1"/>
    <c:param name="maxPageItem" value="12"/>
    <c:param name="sortName" value="name"/>
    <c:param name="sortBy" value="desc"/>
</c:redirect>
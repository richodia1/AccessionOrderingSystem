<%@ include file="/common/taglibs.jsp"%>

<a style="margin-right: 10px;" onclick="window.history.back();">Back</a>
<a style="margin-right: 10px;" href="<c:url value="/" />">Dashboard</a>
<!-- Other menus -->
<a style="margin-right: 10px;" href="<c:url value="/help/content/index.html" />" title="Help start page">Help index</a>

<security:authorize ifAnyGranted="ROLE_ADMIN">
	<a style="margin-right: 10px;" href="<c:url value="/admin/" />">Administration</a>
</security:authorize>
<security:authorize ifAnyGranted="ROLE_USER">
	<a style="margin-right: 10px;" href="<c:url value='/j_spring_security_logout' />" title="Log out">Log out</a>
</security:authorize>
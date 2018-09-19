<%@ include file="/common/taglibs.jsp"%>

<a style="margin-right: 10px;" href="<c:url value="/" />">Public site</a>
<a style="margin-right: 10px; background-image: url('<c:url value="/img/help.png" />'); background-repeat: no-repeat; background-position: 0px 0px; padding-left: 20px;" onclick="javascript: return IITAHELP.helpFrame();" title="Help!">Help</a>
<security:authorize ifAnyGranted="ROLE_USER">
	<a style="margin-right: 10px;" href="<c:url value='/j_spring_security_logout' />" title="Log out">Log out</a>
</security:authorize>

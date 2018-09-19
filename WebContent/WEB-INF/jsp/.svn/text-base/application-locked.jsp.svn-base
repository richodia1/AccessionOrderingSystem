<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Access to application is currently blocked</title>
<s:if test="!locked">
	<meta http-equiv="refresh" content="5;url=<c:url value="/" />" />
</s:if>
</head>
<body>
<s:if test="locked">
	<div><s:property value="message" escape="false" /></div>
	<security:authorize ifNotGranted="ROLE_ADMIN">
		<div style="margin: 0px auto 0px auto; width: 450px; padding-top: 50px;"><s:include value="/WEB-INF/jsp/user/login-form.jsp" /></div>
	</security:authorize>
	<security:authorize ifAllGranted="ROLE_ADMIN">
		<a href="<c:url value="/" />">You're admin, go to application!</a>
	</security:authorize>
</s:if>
<s:else>
	<p>This application is no longer locked. You will be automatically redirected to the <a href="<c:url value="/" />">dashboard</a> in 5 seconds.</p>
</s:else>
</body>
</html>
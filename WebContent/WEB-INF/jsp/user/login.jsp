<%@ page session="false" %>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Login</title>
</head>
<body>
<div style="margin: 0px auto 0px auto; width: 450px; padding-top: 20%;">
<h1 style="margin: 0 0 50px 0;"><fmt:message key="webapp.name" /></h1>
<p><b>Note:</b> Use your Domain Network account to log in.</p>
<s:include value="login-form.jsp" />
<%-- <p>Forgot your username? Click here to be <a href="<s:url action="remind/username" />">reminded</a>.</p>--%>
<p>Don't want to use your domain password or domain password does not work? You can <a href="<s:url action="remind/password" />">generate a password</a> to use only with this
application.</p>
<p>Contact <a href="mailto:software@iita.org" title="IITA Software Development Service">software@iita.org</a> for help.</p>


<c:if test="${not empty param.login_error}">
	<div id="errorMessages"><span class="errorMessage">Invalid user name or password! Do <b>NOT</b> use your email address as username!</span></div>
</c:if></div>
<script type="text/javascript">
	$('j_username').focus();
</script>
</body>
</html>
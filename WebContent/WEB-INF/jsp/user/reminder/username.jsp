<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>What's my username?</title>
</head>
<body>
<h1>Forgot your username?</h1>

<p>This application requires you to use the username and password you use to login to your work computer (Network Domain account). If you forgot what your
username is, enter your official email address in the box below and click "What's my username?" button.</p>
<s:form method="POST" action="remind/username">
	<table class="inputform" style="width: 400px;">
		<colgroup>
			<col width="180" />
			<col />
		</colgroup>
		<tr>
			<td class="tdLabel"><label>E-Mail:</label></td>
			<td><s:textfield name="email" /></td>
		</tr>
		<tr>
			<td />
			<td><s:submit value="What's my username?" method="tell" /></td>
		</tr>
	</table>
</s:form>

<s:if test="email!=null && username!=null">
	<div class="notice">
	<p>Email address <b><s:property value="email" /></b> is assigned to username: <b><s:property value="username" /></b></p>
	</div>
</s:if>
<s:elseif test="email!=null">
	<div class="notice">
	<p>Email address <b><s:property value="email" /></b> is not registered with any user! Contact <a href="mailto:software@iita.org">our staff</a> for help.
	Please provide your last name, first name and your official email address.</p>
	</div>
</s:elseif>

<p>Return to <a href="<c:url value="/" />">login screen</a> to log-in.</p>
</body>
</html>

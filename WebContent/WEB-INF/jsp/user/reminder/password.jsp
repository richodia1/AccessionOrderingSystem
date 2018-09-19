<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Request a new password</title>
</head>
<body>
<h1>Request a new password</h1>

<p>This application requires you to use the username and password you use to login to your work computer (Network Domain account). If your domain password
does not work or you don't have a domain account, enter your official email address in the box below and click "Request password" button.</p>
<p>An email with confirmation link will be sent to your email address (check Inbox and <b>Junk mail</b> folders). Your password will be generated when you
click the link.</p>
<s:form method="POST" action="remind/password">
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
			<td><s:submit value="Request password" method="requestPassword" /></td>
		</tr>
	</table>
</s:form>

<s:if test="email!=null && key!=null">
	<div class="actionMesage">
	The confirmation email was sent to <b><s:property value="email" /></b> (check Inbox and <b>Junk mail</b> folders).
	</div>
</s:if>
<s:elseif test="email!=null">
	<div class="actionError">
	Email address <b><s:property value="email" /></b> is not registered with any user! Contact <a href="mailto:software@iita.org">our staff</a> for help.
	Please provide your last name, first name and your official email address.
	</div>
</s:elseif>

<p>Return to <a href="<c:url value="/" />">login screen</a> to log-in.</p>
</body>
</html>
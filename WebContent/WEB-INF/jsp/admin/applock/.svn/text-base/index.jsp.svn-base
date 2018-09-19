<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Application lock</title>
</head>
<body>
<s:if test="locked">
	<p>The application is currently locked. Only administrators have access to the entire application, other users will see the notification message shown
	below. Click to <a href="<s:url action="application-locked" namespace="/" />">see the real-life message</a>.</p>
	<p style="color: Red"><s:property value="message" escape="false" /></p>
	<s:form method="post" action="applock!unlock">
		<s:submit value="Unlock application" />
	</s:form>
</s:if>
<s:else>
	<p>Use the form below to limit access to this application to all users (apart from administrators).</p>
	<s:form method="post" action="applock!lock">
		<table class="inputform">
			<col width="200px" />
			<col />
			<tr iita:helptoc="tools/block-access#applock_message">
				<td>Notification message:</td>
				<td><s:textarea name="message" value="%{message}" /></td>
			</tr>
			<tr>
				<td />
				<td><s:submit value="Lock application" /></td>
			</tr>
		</table>
	</s:form>
	<p>Click on pre-defined templates to use as notification message:</p>
	<ul id="notificationTemplates">
		<li><h1>Access to application is temporarily blocked</h1>
<p>Please try again later.</p></li>
		<li><h1>Maintenance in progress</h1>
<p>Sorry for the inconvenience, we'll be right back.</p></li>
	</ul>
	<script type="text/javascript">
	Event.observe($('notificationTemplates'), 'click', function(e) {
			var li=e.target;
			while (li!=null && li.tagName!="LI") li=li.parentNode;
			$('applock_message').value=li.innerHTML;
	});
	</script>
</s:else>
</body>
</html>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Notification: <s:property value="notification.title" /></title>
</head>
<body>
	<s:if test="notification != null">
		<h2><s:property value="notification.title" /></h2>
		<div style="margin: 10px 0;"><b>From:</b> <s:property value="notification.originAppName" /><br />
		<b>Date:</b> <iita:date name="notification.createdDate" format="dd/MM/yyyy HH:mm" /></div>
		<div style="margin: 10px 0 20px;"><s:property value="@org.iita.util.StringUtil@toHtml(notification.message)" escape="false" /></div>
	</s:if>
	<s:form action="user/notification" method="get"><s:submit value="Back" /></s:form>
</body>
</html>
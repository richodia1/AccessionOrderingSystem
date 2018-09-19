<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Application notification subscriptions</title>
</head>
<body>
<div class="actionMessage">Events you subscribe to will be delivered to you depending on type of delivery. E.g. "Within application" will drop notification messages in your <a href="<s:url action="user/notification" />">notifications
box</a>.</div>
<table>
<colgroup><col /><col width="200px" /></colgroup>
<tr>
<td>
<s:form method="post" action="user/notification/manage!update">
	<table class="inputform">
		<colgroup>
			<col width="300px" />
			<col />
		</colgroup>
		<tbody>
			<s:iterator value="availableNotifications" status="status">
				<tr>
					<td id="notification.<s:property value="top.name" />">
					<h3><s:text name="notification.%{name}" /> <s:checkbox name="subscriptions['%{top.name}'].subscribed" value="%{subscriptions[top.name].subscribed}" /></h3>
					<div><s:text name="notification.%{name}.description" /></div>
					</td>
					<td style="padding-bottom: 15px;">Delivery frequency: <s:select list="@org.iita.security.model.UserNotification$NotificationFrequency@values()"
						name="subscriptions['%{top.name}'].frequency" value="%{subscriptions[top.name].frequency}" listValue="%{getText('notification.frequency.' + toString())}" /> Delivery priority: <s:select
						list="@org.iita.security.model.UserNotification$NotificationPriority@values()" name="subscriptions['%{top.name}'].priority"
						value="%{subscriptions[top.name].priority}" />
					<div>Custom format string: <s:textfield name="subscriptions['%{top.name}'].formatString" value="%{subscriptions[top.name].formatString}" /></div>
					<s:hidden name="subscriptions['%{top.name}'].eventName" value="%{top.name}" />
					<div><small>Default format: <b><s:property value="notification(top).defaultFormat()" /></b></small></div>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td />
				<td><s:submit value="Update settings" /></td>
			</tr>
		</tbody>
	</table>
</s:form>
</td>
<td style="padding-left: 20px">
	<s:iterator value="availableNotifications" status="status">
		<div><a href="#notification.<s:property value="top.name" />"><s:text name="notification.%{top.name}" /></a></div>
	</s:iterator>
</td>
</tr>
</table>
</body>
</html>
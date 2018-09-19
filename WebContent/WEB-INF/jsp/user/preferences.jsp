<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>User preferences</title>
<s:head />
</head>
<body>
<h3>User preferences</h3>
<s:form method="POST" action="preferences!store">
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<s:iterator value="settings" status="status">
			<s:if test="%{top instanceof org.iita.security.model.BooleanPreference}">
				<tr>
					<td />
					<td><s:checkbox fieldValue="true" value="preferenceValue" name="settings[%{#status.index}].preferenceValueX" /> <b><s:property value="top.description" /></b></td>
				</tr>
			</s:if>
			<s:else>
				<tr>
					<td><s:property value="description" />:</td>
					<td><s:textfield value="%{preferenceValue}" name="settings[%{#status.index}].preferenceValueX" /></td>
				</tr>
			</s:else>
		</s:iterator>
	</table>
	<br />
	<s:submit value="Save" />
</s:form>
</body>
</html>

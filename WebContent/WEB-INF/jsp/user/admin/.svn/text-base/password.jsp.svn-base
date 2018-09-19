<%@ include file="/common/taglibs.jsp"%>


<html>
<head>
<title>Change password for <s:property value="user.lastName" />, <s:property value="user.firstName" /></title>
</head>
<body>

<s:form namespace="/admin" action="user/password" method="post">
	<s:hidden name="id" value="%{user.id}" />
	<s:hidden name="user.id" value="%{user.id}" />
	<!-- don't forget validation -->
	<table class="inputform">
		<colgroup>
			<col width="180" />
			<col />
		</colgroup>
		<tr>
			<td class="tdLabel"><label>New password:</label></td>
			<td><s:password name="passwd1" value="%{passwd1}" label="New password" /></td>
		</tr>
		<tr>
			<td class="tdLabel"><label>Repeat:</label></td>
			<td><s:password name="passwd2" value="%{passwd2}" label="Repeat" /></td>
		</tr>
	</table>

	<s:submit value="Set password" method="set" />
	<s:submit value="Use LDAP" method="toldap" />
	<s:submit value="Cancel" name="redirect-action:user/users" />

</s:form>
</body>
</html>
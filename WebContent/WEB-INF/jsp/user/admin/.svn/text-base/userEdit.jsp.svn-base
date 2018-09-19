<%@ include file="/common/taglibs.jsp"%>

<s:if test="user==null || user.id == null">
	<s:set name="title" value="%{'Add new user'}" />
</s:if>
<s:else>
	<s:set name="title" value="%{'Update user'}" />
</s:else>

<html>
<head>
<title><s:property value="#title" /></title>
</head>
<body>

<s:form action="userSave" method="post">
	<s:hidden name="id" value="%{user.id}" />
	<s:hidden name="user.id" value="%{user.id}" />

	<div class="button-bar">
		<s:submit value="Update" action="userSave!update" />
		<s:submit value="Submit & Close" />
		<s:submit value="Cancel" name="redirect-action:users" />
	
		<s:if test="user.id!=null">
			<input type="button" value="Impersonate" onClick="javascript: window.location='<s:url action='user/user!switchto' />?id=<s:property value="user.id" />';" />
			<input type="button" value="Change password" onClick="javascript: window.location='<s:url namespace="/admin" action="user/password?id=%{user.id}" />';" />
		</s:if>
	</div>
	
	<!-- don't forget validation -->
	<table class="inputform" iita:helptoc="users/user">
		<colgroup>
			<col width="180" />
			<col />
		</colgroup>
		<tr>
			<td class="tdLabel"><label>User Name:</label></td>
			<td><s:textfield name="user.userName" value="%{user.userName}" label="User name" /></td>
		</tr>
		<tr>
			<td class="tdLabel"><label>Login type:</label></td>
			<td><s:property value="user.authenticationType" /></td>
		</tr>
		<tr>
			<td class="tdLabel"><label>State:</label></td>
			<td><s:select name="user.status" value="%{user.status}" list="#{'DISABLED':'Disabled', 'ENABLED':'Active', 'DELETED':'Deleted'}" /></td>
		</tr>		
		<tr>
			<td class="tdLabel"><label>First name:</label></td>
			<td><s:textfield name="user.firstName" value="%{user.firstName}" label="User first name" /></td>
		</tr>

		<tr>
			<td class="tdLabel"><label>Last name:</label></td>
			<td><s:textfield name="user.lastName" value="%{user.lastName}" label="User last name" /></td>
		</tr>


		<tr>
			<td class="tdLabel"><label>Display name:</label></td>
			<td><s:textfield name="user.displayName" value="%{user.displayName}" label="User display name" /></td>
		</tr>

		<tr>
			<td class="tdLabel"><label>Email:</label></td>
			<td><s:textfield name="user.mail" value="%{user.mail}" label="User mail" /></td>
		</tr>


		<tr>
			<td class="tdLabel"><label>Description:</label></td>
			<td><s:textarea name="user.description" value="%{user.description}" label="User description" /></td>
		</tr>

		<tr>
			<td class="tdLabel"><label>Department:</label></td>
			<td><s:textfield name="user.department" value="%{user.department}" label="User department" /></td>
		</tr>

		<tr>
			<td class="tdLabel"><label>Last login:</label></td>
			<td><iita:date name="user.lastLogin" format="%{getText('date.format')} HH:mm:ss" /></td>
		</tr>

		<tr>
			<td class="tdLabel"><label>Last login failed:</label></td>
			<td><iita:date name="user.lastLoginFailed" format="%{getText('date.format')} HH:mm:ss" /></td>
		</tr>
	</table>


	<h2>Role</h2>
	<s:include value="include/role.jsp" />
	
	<h2>Tag based access</h2>
	<s:include value="include/tagaccess.jsp" />


	<h2>Lookup</h2>
	<s:include value="include/lookup.jsp" />
</s:form>
</body>
</html>
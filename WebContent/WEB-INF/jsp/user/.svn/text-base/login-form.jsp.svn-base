<%@ include file="/common/taglibs.jsp"%>
<form action="j_spring_security_check" method="post">
<table class="inputform">
	<colgroup>
		<col width="30%" />
		<col width="70%" />
	</colgroup>
	<tbody>
		<tr>
			<td>Username:</td>
			<td><s:textfield name="j_username" label="User" id="j_username" value="%{#request.appUser}" /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><s:password name="j_password" label="Password" id="j_password" /></td>
		</tr>
<%--
		<tr>
			<td />
			<td><s:checkbox name="_spring_security_remember_me" /> Remember me on this computer.</td>
		</tr>
--%>
		<tr>
			<td />
			<td><s:submit value="Login" /></td>
		</tr>
	</tbody>
</table>
</form>

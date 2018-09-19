<%@ include file="/common/taglibs.jsp"%>

<table class="data-listing" iita:helptoc="users/user#access-tags">
	<thead>
		<tr>
			<td><label>Grant access to objects with Tags:</label></td>
		</tr>
	</thead>
	<s:iterator value="user.accessTags" status="status">
		<tr>
			<td><s:textfield name="user.accessTags" value="%{top}" cssStyle="width: 100%" /></td>
		</tr>
	</s:iterator>
	<tr>
		<td><s:textfield name="user.accessTags" value="" label="Identifier" cssStyle="width: 100%" /></td>
	</tr>
</table>
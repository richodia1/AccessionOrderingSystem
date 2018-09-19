<%@ include file="/common/taglibs.jsp"%>


<table class="data-listing">
	<colgroup><col width="25%" /><col /></colgroup>
	<thead>
		<tr>
			<td><label>Role</label></td>
			<td><label>Description</label></td>
		</tr>
	</thead>
	<s:iterator value="allRoles" status="status">
		<tr>
			<td class="identifying"><s:checkbox name="roles" id="role_%{key}" value="value" fieldValue="%{key}" label="Role" /> <label for="role_<s:property value="key" />"><s:text name="user.role.%{key}" /></label></td>
			<td><s:text name="%{'user.role.desc.'+key}" /></td>
		</tr>
	</s:iterator>
</table>

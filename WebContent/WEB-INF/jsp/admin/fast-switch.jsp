<%@ include file="/common/taglibs.jsp"%>
<span iita:helptoc="users/switching">
<s:form method="post" action="user/user!switchto" namespace="/admin">
	<b>Switch to:</b>
	<s:url namespace="/admin/ajax" action="admin" id="xx" /> <iita:autocompleter name="id" id="assmem_username" listKey="id" listValue="fullName"
		url="%{xx}" method="autocompleteUser" />
</s:form></span>
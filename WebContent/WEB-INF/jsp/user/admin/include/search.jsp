<%@ include file="/common/taglibs.jsp"%>
<span iita:helptoc="users/index#quicksearch">
<s:form method="get" action="users!search" namespace="/admin">
	Search: <s:textfield name="filter" value="%{filter}" cssStyle="width: 100px;" /> <s:checkbox name="extensiveSearch" value="%{extensiveSearch}" label="LDAP?" /> <s:submit value="Search" />
</s:form>
</span>

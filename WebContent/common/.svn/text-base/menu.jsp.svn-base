<%@ include file="/common/taglibs.jsp"%>

<a style="margin-right: 10px;" href="<s:url namespace="/" action="index" />">Dashboard</a>
<!-- Other menus -->
<a style="margin-right: 10px;" href="<s:url action="collection/filter" namespace="/" />" title="Filters">Filters</a>
<a style="margin-right: 10px;" href="<s:url action="browse" namespace="/" />" title="Browse accessions">Browse</a>
<a style="margin-right: 10px;" href="<s:url action="selection" namespace="/" />" title="View selected accessions">Cart</a>

<!-- End -->
<security:authorize ifAnyGranted="ROLE_USER">
</security:authorize>
<security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_QUERY,ROLE_QUERYMANAGER">
	<a style="margin-right: 10px;" href="<s:url namespace='/' action='query/index' />">Query</a>
</security:authorize>
<security:authorize ifAnyGranted="ROLE_USER">
	<a style="margin-right: 10px;" href="<s:url namespace='/' action='user/notification' />" title="Manage application notifications">Notifications</a>
	<a style="margin-right: 10px;" href="<s:url namespace='/' action='user/delegate' />">Delegate</a>
	<a style="margin-right: 10px;" href="<s:url namespace='/' action='user/changepasswordsetting' />" title="Change password preferences">Password</a>
</security:authorize>
<security:authorize ifAnyGranted="ROLE_ADMIN">
	<a style="margin-right: 10px;" href="<s:url action="edit" namespace="/" />?experimentId=1" title="Register new accession"><b>Add accession</b></a>
	<a style="margin-right: 10px;" href="<c:url value="/admin/" />">Administration</a>
</security:authorize>
<a style="margin-right: 10px; background-image: url('<c:url value="/img/help.png" />'); background-repeat: no-repeat; background-position: 0px 0px; padding-left: 20px;" onclick="javascript: return IITAHELP.helpFrame();" title="Help!">Help</a>
<a style="margin-right: 10px;" href="mailto:software.support@iita.org?subject=Accessions DB bug report" title="Send a bug report">Bug</a>

<security:authorize ifAnyGranted="ROLE_USER">
	<a style="margin-right: 10px;" href="<c:url value='/j_spring_security_logout' />" title="Log out">Log out</a>
</security:authorize>
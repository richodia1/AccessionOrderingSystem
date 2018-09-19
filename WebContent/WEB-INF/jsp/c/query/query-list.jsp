<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Query list</title>
</head>
<body>
<iita:authorize ifAnyGranted="ROLE_ADMIN,ROLE_QUERYMANAGER">
	<div class="button-bar"><a href="<s:url action="query/edit!newQuery" />">Add new query</a></div>
</iita:authorize>
<ul>
	<s:iterator value="queries">
		<li><iita:authorize ifAnyGranted="ROLE_ADMIN,ROLE_QUERYMANAGER">
			<b><a href="<s:url action="query/edit" />?id=<s:property value="id" />">Edit query</a></b>
		</iita:authorize> <a href="<s:url action="query/run" />?id=<s:property value="id" />"><s:property value="title" /></a> <s:property value="description" /></li>
	</s:iterator>
</ul>
</body>
</html>
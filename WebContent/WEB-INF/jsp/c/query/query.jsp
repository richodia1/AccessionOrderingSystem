<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Query <s:property value="query.title" /></title>
</head>
<body>
<h1><s:property value="query.title" /></h1>

<s:push value="paged">
	<s:include value="/WEB-INF/jsp/paging.jsp">
		<s:param name="href" value="%{'id=' + [1].query.id}" />
	</s:include>
	<s:include value="query-results.jsp" />
</s:push>

<div class="button-bar">
	<a href="<s:url action="query/index" />">Back to all queries</a> 
	<iita:authorize ifAnyGranted="ROLE_ADMIN,ROLE_QUERYMANAGER">
		<b><a href="<s:url action="query/edit" />?id=<s:property value="query.id" />">Edit query</a></b>
	</iita:authorize>
	<a href="<s:url action="query/run!download" />?id=<s:property value="query.id" />">Download XLS</a>
</div>
</body>
</html>
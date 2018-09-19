<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search results</title>
</head>
<body>
<s:if test="paged.totalHits>0">
	<s:include value="/WEB-INF/jsp/paging.jsp">
		<s:param name="page" value="paged.page" />
		<s:param name="pages" value="paged.pages" />
		<s:param name="pageSize" value="paged.pageSize" />
		<s:param name="href" value="%{'q=' + searchString	}" />
	</s:include>

	<s:push value="paged.results">
		<s:include value="/WEB-INF/jsp/accession/accession-tabular.jsp" />
	</s:push>
</s:if>
<s:else>
No data.
</s:else>
</body>
</html>
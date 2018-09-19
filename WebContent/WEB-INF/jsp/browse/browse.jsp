<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Browse</title>
</head>
<body>
<s:if test="filters.filters.size>0">
	<div class="actionMessage">List of accessions below is filtered:
	<s:push value="filters"><s:include value="filters-small.jsp" /></s:push>.
	Click to <a href="<s:url action="collection/filter" />">Change data filters and displayed columns.</a>
	</div>
</s:if>
<s:else>
	<div class="actionMessage">List of accessions below is not filtered. Jump to <a href="<s:url action="index" />">Dashboard page</a> to select a collection and filter data.</div>
</s:else>

<s:if test="paged.totalHits>0">
	<s:push value="paged">
		<s:include value="/WEB-INF/jsp/paging.jsp">
			<s:param name="href" value="%{''}" />
		</s:include>
	</s:push>
	
	<s:push value="paged.results">
		<s:include value="accession-tabular.jsp" />
	</s:push>
	
	<div class="button-bar">
	You can download passport data of <b><s:property value="paged.totalHits" /></b> matching accessions:
	<s:form method="post" action="browse!download">
	<s:submit value="Download" />	
	</s:form>
	<security:authorize ifAnyGranted="ROLE_USER">
	<s:form method="post" action="browse!downloadSQL">
	<s:submit value="Download SQL" />	
	</s:form>
	</security:authorize>
	</div>
</s:if>
<s:else>
No data.
</s:else>
</body>
</html>
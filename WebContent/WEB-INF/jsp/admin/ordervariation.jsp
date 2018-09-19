<%@ include file="/common/taglibs.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accession Order Variation Status</title>
</head>
<body>

<s:if test="paged!=null && paged.totalHits>0">
	<s:include value="/WEB-INF/jsp/paging.jsp">
		<s:param name="page" value="paged.page" />
		<s:param name="pages" value="paged.pages" />
		<s:param name="pageSize" value="paged.pageSize" />
		<s:param name="href" value="%{''}" />
	</s:include>
	
	<s:push value="paged.results">
		<s:include value="/WEB-INF/jsp/accession/accession-tabular-variation.jsp" />
	</s:push>
	
</s:if>
<s:else>
No accession order variation data.
</s:else>

</body>
</html>
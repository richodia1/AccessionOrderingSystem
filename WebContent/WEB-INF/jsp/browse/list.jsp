<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Browse</title>
</head>
<body>
<s:if test="paged.totalHits>0">
	<s:push value="paged">
		<s:include value="/WEB-INF/jsp/paging.jsp">
			<s:param name="href" value="%{''}" />
		</s:include>
	</s:push>
	
	<s:push value="paged.results">
		<s:include value="/WEB-INF/jsp/accession/accession-tabular.jsp" />
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
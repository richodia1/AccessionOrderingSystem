<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Your selected accessions</title>
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
		<s:include value="/WEB-INF/jsp/accession/accession-tabular.jsp" />
	</s:push>

	<h2>Tools</h2>
	<s:form action="selection!clear" method="post">
		<s:submit value="Clear list" />
	</s:form>
	<s:form action="export" method="post">
		<s:submit value="Export to XLS" />
	</s:form>
	<s:if test="!needCaptcha">
		<s:form action="order" method="post">
			<s:submit value="Create order" />
		</s:form>
	</s:if>
	<s:else>
		<h2>Request for selected accessions</h2>
		<s:form action="order" method="post">
			<p>Before you can file your order, please complete Captcha challenge:</p>
			<s:if test="needCaptcha">
			<img src="<s:url action="captcha" namespace="/" />" /><br />
			(Can't see or read the image? <a href="<s:url />">Refresh this page</a><br />
			Please type the text you see in the image above:<br />
			<input type="text" value="" name="jcaptcha" /></s:if>
			<s:submit value="Create order" />
		</s:form>
	</s:else>


</s:if>
<s:else>
You have selected no accessions.
</s:else>
</body>
</html>
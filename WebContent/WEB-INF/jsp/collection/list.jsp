<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Collections</title>
</head>
<body>
<a href="<s:url action="collection/profile" />">Add</a>

<s:if test="paged.totalHits>0">
	<s:include value="/WEB-INF/jsp/paging.jsp">
		<s:param name="page" value="paged.page" />
		<s:param name="pages" value="paged.pages" />
		<s:param name="pageSize" value="paged.pageSize" />
		<s:param name="href" value="%{''}" />
	</s:include>

	<table class="data-listing">
		<colgroup>
			<col width="300px" />
			<col width="100px" />
			<col />
		</colgroup>
		<thead>
			<tr>
				<td>Name</td>
				<td>Short name</td>
				<td>Prefixes</td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="paged.results">
				<tr>
					<td><a href="<s:url action="collection/profile" />?id=<s:property value="id" />"><s:property value="name" /></a></td>
					<td><s:property value="shortName" /></td>
					<td><s:property value="prefixes" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>
<s:else>
No collections.
</s:else>
</body>
</html>
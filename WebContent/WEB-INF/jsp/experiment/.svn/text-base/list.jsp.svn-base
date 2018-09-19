<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update experiment data</title>
</head>
<body>
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
			<col width="60px" />
			<col width="100px" />
			<col />
		</colgroup>
		<thead>
			<tr>
				<td>Name</td>
				<td>Visibility</td>
				<td>Authors</td>
				<td>Description</td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="paged.results">
				<tr class="<s:if test="!visible">private-column</s:if>">
					<td><a href="<s:url action="experiment/profile" />?id=<s:property value="id" />"><s:if test="title.length==0"><em>[Not named]</em></s:if><s:else><s:property value="title" /></s:else></a></td>
					<td class="identifiying"><s:if test="visible">Public</s:if><s:else>Genebank</s:else></td>
					<td><s:property value="authors" /></td>
					<td><iita:text value="description" maxLength="60" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>
<s:else>
No experiments.
</s:else>
</body>
</html>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Experiment data: <s:property value="experiment.title" /></title>
</head>
<body>
<s:include value="navigation.jsp" />

<s:if test="data.totalHits>0">
	<s:include value="/WEB-INF/jsp/paging.jsp">
		<s:param name="page" value="data.page" />
		<s:param name="pages" value="data.pages" />
		<s:param name="pageSize" value="data.pageSize" />
		<s:param name="href" value="%{'id=' + experiment.id}" />
	</s:include>
</s:if>

<div style="width: 100%; overflow-x: scroll;">
<table class="data-listing data-table" style="" >
	<thead>
		<tr>
			<td>ID</td>
			<s:iterator value="columns">
				<td><div><s:property value="title" /></div></td>
			</s:iterator>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="data.results">
			<tr>
				<s:set name="row" value="top" />
				<td class="identifying"><s:property value="#row[0]" /></td>
				<s:iterator value="columns" status="status">
					<td><div><s:property value="#row[#status.index+1]" /></div></td>
				</s:iterator>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
</body>
</html>
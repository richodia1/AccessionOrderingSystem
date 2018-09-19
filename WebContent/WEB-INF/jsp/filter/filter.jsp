<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Filter configuration</title>
</head>
<body>
<s:if test="filters==null || filters.filters.size==0">
	<div class="actionMessage">You don't have any active filters</div>
</s:if>
<s:else>
<h2>Currently active filters</h2>
<ul>
<s:iterator value="filters.filters">
	<li>
	<s:set name="column" value="getColumn(tableName, column)" />
	<s:if test="#column!=null">
	<s:push value="#column">
		<a href="<s:url action="filter" />?experiment=<s:property value="experiment.tableName" />&field=<s:property value="column" />" title="Click to filter on this data"><s:property value="title" /></a>
		is
		<%-- <s:property value="tableName" />.<s:property value="column" /> is --%>
		<s:if test="coded">
			<s:iterator value="filterValues" status="status">
			<b><s:if test="top==null"><em>Not specified</em></s:if><s:else>
				<s:property value="[1].decode(top)" />
			</s:else></b>
			<s:if test="!#status.last"> or </s:if>
			</s:iterator>
		</s:if>
		<s:else>
			<s:iterator value="filterValues" status="status">
			<b><s:if test="top==null"><em>Not specified</em></s:if><s:else>		
				<s:if test="top instanceof org.iita.accessions2.service.Filters$FilterRangeValue">
					<s:property value="minValue" /> - <s:property value="maxValue" />
				</s:if>
				<s:else>
					<s:property value="top" />
				</s:else>
			</s:else></b>
			<s:if test="!#status.last"> or </s:if>
			</s:iterator>
		</s:else>
		<a href="<s:url action="filter!clear" />?experiment=<s:property value="experiment.tableName" />&field=<s:property value="column" />">Remove all</a>
	</s:push>
	</s:if>
	<s:else>
		Woow!
	</s:else>
	</li>
</s:iterator>
</ul>
<s:form method="post" action="filter!clear">
	<s:submit value="Remove all filters" />
</s:form>
<s:form method="get" action="browse">
	<s:submit value="Browse accessions" />
</s:form>
</s:else>

<s:if test="field!=null && experiment!=null">
<h2>Add filter on field "<s:property value="selectedColumn.title" />"</h2>
<s:push value="selectedColumn">
	<s:include value="column-filter.jsp" />
</s:push>
</s:if>

<h2>Available filters</h2>
<ul>
<s:iterator value="columns">
	<s:if test="visible">
	<li><s:property value="experiment.title" />: <a href="<s:url action="filter" />?experiment=<s:property value="experiment.tableName" />&field=<s:property value="column" />" title="Click to filter on this data"><s:property value="title" /></a></li>
	</s:if>
	<s:else>
		<iita:authorize ifAnyGranted="ROLE_USER">
			<li class="private-column"><s:property value="experiment.title" />: <a href="<s:url action="filter" />?experiment=<s:property value="experiment.tableName" />&field=<s:property value="column" />" title="Click to filter on this data"><s:property value="title" /></a></li>
		</iita:authorize>
	</s:else>
</s:iterator>
</ul>
</body>
</html>
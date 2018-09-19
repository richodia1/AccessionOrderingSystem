<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Collection: <s:property value="collection.name" /></title>
</head>
<body>

<table>
<colgroup><col width="50%" /><col width="50%" /></colgroup>
<tr><td>
<s:if test="filters!=null && filters.filters.size>0">
	<h2>Currently active filters match <s:property value="countMatching()" /> accessions</h2>
	<ul>
	<s:iterator value="filters.filters">
		<s:set name="column" value="getColumn(tableName, column)" />
		<s:if test="#column!=null">
		<s:push value="#column">
			<li><a href="<s:url action="collection/filter!remove" />?col=<s:property value="id" />" title="Remove this filter"><img src="<c:url value="/img/close.gif" />" style="height: 10px;" /></a> <a href="<s:url />?col=<s:property value="id" />" title="Click to filter on this data"><s:property value="description" /></a> is
				<s:if test="coded">
					<s:iterator value="filterValues" status="status">
					<b><s:if test="top==null"><em>Not specified</em></s:if><s:else>
						<s:property value="[1].decode(top)" />
					</s:else></b>
					<a href="<s:url action="collection/filter!remove1" />?col=<s:property value="id" />&amp;intValue=<s:property value="top"/>" title="Remove this filter"><img src="<c:url value="/img/close.gif" />" style="height: 10px;" /></a> 
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
			</li>
		</s:push>
		</s:if>
	</s:iterator>
	</ul>
	<s:form method="post" action="collection/filter!clear">
		<s:hidden name="id" value="%{collection.id}" />
		<s:submit value="Remove all filters" />
	</s:form>
	<s:if test="countMatching()>0">
		<s:form method="get" action="browse">
			<s:submit value="Browse matching accessions" />
		</s:form>
	</s:if>
</s:if>
</td><td>
<s:if test="selectedColumn!=null">
	<h2>Filter by <s:property value="selectedColumn.description" /></h2>
	<s:push value="selectedColumn">
		<s:include value="filter-descriptor.jsp" />
	</s:push>
</s:if>
<s:else>
<h2>No new filter selected</h2>
<p>Select one of the accession properties from the lists below to add as filter.</p>
</s:else>
</td></tr></table>

<s:if test="#accessionsUtil.getFilters().getDisplayColumns().size>0">
<h2>Displayed columns</h2>
<ul>
	<li>Accession name</li>
	<li>Taxonomy</li>
	<s:iterator value="#accessionsUtil.getExtraColumns()">
		<li><a href="<s:url action="collection/filter!removeDisplay" />?id=<s:property value="collection.id" />&amp;col=<s:property value="id" />"><img src="<c:url value="/img/close.gif" />" style="height: 10px;" /></a> <a href="<s:url />?col=<s:property value="id" />"><s:property value="title" /></a></li>
	</s:iterator>
</ul>
</s:if>

<h1>What should the data be filtered by?</h1>

<!-- For each experiment, display all experiment fields and allow filtering -->
<s:iterator value="experiments">
	<h3><s:property value="title" /></h3>
	<ul>
	<s:iterator value="columns">
		<s:if test="visible">
			<li><a href="<s:url action="collection/filter!addDisplay" />?id=<s:property value="collection.id" />&amp;col=<s:property value="id" />">SHOW</a> <a href="<s:url />?id=<s:property value="[2].collection.id" />&amp;col=<s:property value="id" />"><s:property value="description" /></a>
				<s:if test="coded">
					(<s:iterator value="coding" status="status"><s:if test="!(#status.first)">, </s:if><em><s:property value="actualValue" /></em></s:iterator>)
				</s:if>
			</li>
		</s:if>
	</s:iterator>
	</ul>
</s:iterator>
</body>
</html>
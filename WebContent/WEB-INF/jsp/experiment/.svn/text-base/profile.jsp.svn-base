<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Experiment: <s:property value="experiment.title" /></title>
</head>
<body>
<s:include value="navigation.jsp" />

<s:form method="post" action="experiment/profile!update">
	<s:hidden name="id" value="%{experiment.id}" />
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td>Title:</td>
			<td><s:textfield name="experiment.title" value="%{experiment.title}" /></td>
		</tr>
		<tr>
			<td>Authors:</td>
			<td><s:textfield name="experiment.authors" value="%{experiment.authors}" /></td>
		</tr>
		<tr>
			<td>Description:</td>
			<td><s:textarea cssClass="sizable-textarea" name="experiment.description" value="%{experiment.description}" /></td>
		</tr>
		<tr>
			<td>Visibility:</td>
			<td><s:select name="experiment.visible" list="#{false:'Visible only to genebank users',true:'Visible to public'}" value="%{experiment.visible}" /></td>
		</tr>
		<tr>
			<td>DB table:</td>
			<td><s:property value="experiment.tableName" /></td>
		</tr>
		<tr>
			<td />
			<td><s:submit value="Update" /> <s:submit value="Download XLS" action="experiment/profile!download" /> <s:submit value="Delete data" action="experiment/profile!clear" /> <s:if test="!experiment.systemTable"><s:submit value="Delete experiment" action="experiment/profile!delete" /></s:if></td>
		</tr>
	</table>
</s:form>

<%--
<h2>Data preview</h2>
<div style="width: 100%; overflow-x: scroll;">
<table class="data-listing data-table" style="">
	<thead>
		<tr>
			<td>ID</td>
			<s:iterator value="columns">
				<td>
				<div><s:property value="title" /></div>
				</td>
			</s:iterator>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="data.results">
			<tr>
				<s:set name="row" value="top" />
				<td class="identifying"><s:property value="#row[0]" /></td>
				<s:iterator value="columns" status="status">
					<td>
					<div><s:property value="#row[#status.index+1]" /></div>
					</td>
				</s:iterator>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>
<a href="<s:url action="experiment/data" />?id=<s:property value="experiment.id" />">View all experiment data</a>
--%>

<h2>Data columns</h2>
<table class="data-listing">
	<colgroup></colgroup>
	<thead>
		<tr>
			<td>Name</td>
			<td>Description</td>
			<td>Data</td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="columns">
			<tr class="<s:if test="!visible">private-column</s:if>">
				<td><a href="<s:url action="experiment/column" />?id=<s:property value="id" />"><s:property value="title" /></a></td>
				<td>
				<div style="white-space: nowrap; overflow: hidden;"><s:property value="description" /></div>
				</td>
				<td><b><s:property value="column" /></b> as <em><s:property value="dataType" /></em> <s:if test="coded">
					<b>The column is coded.</b>
					<%--<ul>
	<s:iterator value="coding">
		<li><s:property value="codedValue" /> = <s:property value="actualValue" /></li>
	</s:iterator>
	</ul>--%>
				</s:if></td>
			</tr>
		</s:iterator>
	</tbody>
</table>

<s:if test="unmappedColumns!=null">
<h3>Unmapped columns</h3>
<table class="data-listing">
	<colgroup></colgroup>
	<thead>
		<tr>
			<td>Name</td>
			<td>Description</td>
			<td>Data</td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="unmappedColumns">
			<tr>
				<td><a href="<s:url action="experiment/column!add" />?experiment=<s:property value="experiment.id" />&column.column=<s:property value="column" />"><s:property value="title" /></a></td>
				<td>
				<div style="white-space: nowrap; overflow: hidden;"><s:property value="description" /></div>
				</td>
				<td><b><s:property value="column" /></b> as <em><s:property value="dataType" /></em> <s:if test="coded">
					<b>The column is coded.</b>
				</s:if></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</s:if>
</body>
</html>
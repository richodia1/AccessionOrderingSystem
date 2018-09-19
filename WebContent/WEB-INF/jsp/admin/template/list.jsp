<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Templates</title>
</head>
<body>
<div class="button-bar"><a href="<s:url action="template/profile" />">Add template</a></div>
<table class="data-listing">
	<colgroup>
		<col width="100px" />
		<col width="200px" />
		<col />
	</colgroup>
	<thead>
		<tr>
			<td>Source</td>
			<td>Short name</td>
			<td>Title</td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="templates">
			<tr>
				<td><s:if test="id==null">File system</s:if><s:else>Database</s:else></td>
				<td class="identifying"><a href="<s:url action="template/profile" />?shortName=<s:property value="shortName" />"><s:if test="shortName==null">No name</s:if><s:else>
					<s:property value="shortName" />
				</s:else></a></td>
				<td><s:property value="title" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</body>
</html>
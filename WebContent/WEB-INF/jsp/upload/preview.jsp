<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Fix data</title>
</head>
<body>
<s:form method="get" action="experiment/upload!preview">
	<s:hidden name="experimentId" value="%{experiment.id}" />
	<s:submit value="Refresh" />
	<s:submit value="Mapping" action="experiment/upload!mapping" />
	<s:submit value="Clear mapping" action="experiment/upload!clearmapping">
		<s:param name="experimentId" value="%{experiment.id}" />
	</s:submit>
	<s:submit value="Import data" action="experiment/upload!insert" />
	<s:checkbox id="createMissing" name="createMissing" value="%{createMissing}" fieldValue="true" /> <label for="createMissing">Create missing accessions</label>
</s:form>
<p>Now you have to fix the data. <a href="<s:url action="experiment/upload!addallcoding" />?experimentId=<s:property value="experiment.id" />">Add all missing values</a></p>


<table class="data-listing">
	<colgroup>
		<col width="40%" />
		<col width="60%" />
	</colgroup>
	<thead>
		<tr>
			<td>XLS Column</td>
			<td>Mapping</td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="mappings.xlsColumns">
			<s:if test="mappings.mappings[columnNum]">
				<tr>
					<td><s:property value="columnNum" /> <b><s:property value="name" /></b>
					<div><s:property value="sampleData" /></div>
					</td>
					<td><s:if test="mappings.mappings[columnNum]">
						<s:push value="getColumnWithId(mappings.mappings[columnNum])">
							<b><s:property value="title" /></b>
							<s:property value="dataType" />
							<a href="<s:url action="experiment/column" />?id=<s:property value="id" />">Edit</a>
							<p><s:property value="description" /></p>
						</s:push>
						<s:if test="importErrors[columnNum]!=null">
							<div class="actionMessage"><s:property value="importErrors[columnNum].message" /> <s:if
								test="%{importErrors[columnNum].cause instanceof org.iita.accessions2.model.CodingException}">
								<a href="<s:url action="experiment/upload!addcoding" />?experimentId=<s:property value="experiment.id" />&columnNum=<s:property value="columnNum" />">Add
								missing values!</a>
							</s:if></div>
						</s:if>
					</s:if> <s:else>
						<em>Not mapped</em>
					</s:else></td>
				</tr>
			</s:if>
		</s:iterator>
	</tbody>
</table>
</body>
</html>
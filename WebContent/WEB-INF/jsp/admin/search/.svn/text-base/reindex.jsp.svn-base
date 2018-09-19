<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<s:if test="indexer.running">
	<meta http-equiv="Refresh" content="5;url=<s:url action="lucene/reindex" />" />
</s:if>
<title>Indexing</title>
<s:if test="indexer.running">
	<meta http-equiv="Refresh" content="5;url=<s:url action="lucene/reindex" />" />
</s:if>
</head>
<body>
<s:if test="indexer.running">
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tbody>
			<tr>
				<td>Indexer status:</td>
				<td><s:property value="indexer.status" /></td>
			</tr>
			<tr>
				<td>Table:</td>
				<td><s:property value="indexer.indexingTable" /></td>
			</tr>
			<tr>
				<td>Progress:</td>
				<td><s:property value="indexer.currentLot" />/<s:property value="indexer.totalLotCount" /></td>
			</tr>
			<tr>
				<td>Complete:</td>
				<td><b><s:property value="100*indexer.currentLot/indexer.totalLotCount" />%</b></td>
			</tr>
			<tr>
				<td />
				<td><s:form action="lucene/reindex!stop" method="POST">
					<s:submit value="Stop" />
				</s:form></td>
			</tr>
		</tbody>
	</table>
	<p>This status page will automatically refresh every 5 seconds.</p>
</s:if>
<s:else>
	<span iita:helptoc="tools/lucene">
	<s:form action="lucene/reindex!reindex" method="POST" theme="simple">
		<label>Scope:</label>
		<s:select list="#{'org.iita.accessions2.model.Accession':'Accession data','org.iita.accessions2.model.Experiment':'Experiment data'}" name="tableName"
			value="%{indexer.indexTable}" label="Scope" />
		<s:submit value="Reindex" />
	</s:form></span>
</s:else>
</body>
</html>
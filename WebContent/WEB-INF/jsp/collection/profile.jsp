<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Collection: <s:property value="collection.name" /></title>
</head>
<body>
<s:if test="collection!=null">
<h1><s:property value="collection.name" /></h1>
<table class="inputform">
	<colgroup>
		<col width="200px" />
		<col />
	</colgroup>
	<tr>
		<td />
		<td><img src="<c:url value="/img/p/${collection.shortName}.gif" />" /></td>
	</tr>
	<tr>
		<td>Description:</td>
		<td><s:property value="collection.description" /></td>
	</tr>
	<tr>
		<td>Prefixes:</td>
		<td><s:property value="collection.prefixes" /></td>
	</tr>
	<tr>
		<td>Collection size:</td>
		<td><s:property value="collectionSize" /> accessions</td>
	</tr>
	<tr>
		<td />
		<td><ul>
		<li><a href="<s:url action="collection/filter" />?id=<s:property value="collection.id" />">Filter and search for accessions</a></li>
		<li><a href="<s:url action="filter!browse" />?clear=true&experiment=Accession&field=collection_id&intValue=<s:property value="collection.id" />">Browse
		all accessions belonging to this collection</a></li>
		<li><a href="<s:url action="collection!download" />?id=<s:property value="collection.id" />">Download passport data</a>
			<ul>
			<li><a href="<s:url action="descriptors" />?id=1">View passport descriptors</a></li>
			</ul>
		</li>
		<s:iterator value="experiments">
		<li><s:property value="title" />
			<ul>
			<li><a href="<s:url action="descriptors" />?id=<s:property value="id" />">View "<s:property value="title" />" descriptors</a></li>
			<li><a href="<s:url action="collection!download" />?id=<s:property value="collection.id" />&amp;experimentId=<s:property value="id" />">Download "<s:property value="title" />" data</a>
				<s:if test="authors!=null">(<s:property value="authors" />)</s:if>
			</li>
			</ul>
		</li>
		</s:iterator>
		<iita:authorize ifAnyGranted="ROLE_USER">
			<li><a href="<s:url action="collection!geneSys" />?id=<s:property value="collection.id" />">GeneSys data</a></li>
		</iita:authorize>
		
		<li><a href="<s:url action="gallery" />?collection=<s:property value="collection.id" />">Browse collection images</a></li>
		</ul>
		
		<div class="actionMessage" style="margin-top: 20px;"><b>Note:</b> Please observe our <a href="<c:url value="/help/content/accession/copyright.html" />">Copyright and fair use</a> policy.</div>
		</td>
	</tr>
</table>
</s:if>
</body>
</html>
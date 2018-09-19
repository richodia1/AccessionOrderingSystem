<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Collection profile</title>
</head>
<body>
<h1><s:property value="collection.name" /></h1>
<s:form method="post" action="collection/profile!update">
	<s:hidden name="id" value="%{collection.id}" />
	<s:hidden name="collection.version" value="%{collection.version}" />
	<table class="inputform">
		<tr>
			<td>Name:</td>
			<td><s:textfield name="collection.name" value="%{collection.name}" /></td>
		</tr>
		<tr>
			<td>Short name:</td>
			<td><s:textfield name="collection.shortName" value="%{collection.shortName}" /></td>
		</tr>
		<tr>
			<td>Description:</td>
			<td><s:textarea cssClass="sizable-textarea" name="collection.description" value="%{collection.description}" /></td>
		</tr>
		<tr>
			<td>Prefixes:</td>
			<td><s:textarea cssClass="sizable-textarea" name="collection.prefixes" value="%{collection.prefixes}" /></td>
		</tr>
		<tr>
			<td>Visibility:</td>
			<td><s:select name="collection.visible" list="#{false:'Visible only to genebank users',true:'Visible to public'}" value="%{collection.visible}" /></td>
		</tr>
		<tr>
			<td />
			<td><s:submit value="Update" /> <s:submit value="Apply to matching accessions" action="collection/profile!applyPrefix" /> <s:submit value="Delete %{collection.name} data" action="collection/profile!clear" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>
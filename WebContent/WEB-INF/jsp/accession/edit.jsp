<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit accession <s:property value="accession.name" /></title>
</head>
<body>
<!-- Experiments -->
<div class="navigation-tabs clearfloat">
<a href="<s:url action="accession" />?id=<s:property value="accession.id" />">Profile</a>
<a href="<s:url action="edit" />?id=<s:property value="accession.id" />&experimentId=1">MCPD</a>
<s:iterator value="accession.experiments">
<a href="<s:url action="edit" />?id=<s:property value="accession.id" />&experimentId=<s:property value="id" />"><s:property value="title" /></a>
</s:iterator>
<s:if test="accession.id!=null">
	<a href="<s:url action="edit" />?id=<s:property value="accession.id" />">Other...</a>
</s:if>
</div>

<h2><s:property value="experiment.title" /> of <s:property value="accession.name" /></h2>

<!-- Columns of experiment -->
<s:form action="edit!update" method="post">
<s:hidden name="id" value="%{accession.id}" />
<s:hidden name="experimentId" value="%{experiment.id}" />
<table class="inputform accession-profile">
<colgroup>
	<col width="250px" />
	<col width="300px" />
	<col />
</colgroup>
<s:iterator value="experiment.columns">
	<tr class="<s:if test="!visible">private-column</s:if>">
		<td><s:property value="title" />:</td>
		<td class="identifying">
			<s:include value="edit-descriptor.jsp" />
		</td>
		<td style="padding-left: 30px"><em><s:property value="description" /></em></td>
	</tr>
</s:iterator>
</table>
<s:submit value="Update" />
</s:form>

</body>
</html>
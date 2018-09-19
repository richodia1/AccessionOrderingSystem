<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pick experiment for <s:property value="accession.name" /></title>
</head>
<body>
<p>Pick experiment you want to edit:</p>
<ul>
<s:iterator value="experiments">
	<li><a href="<s:url action="edit" />?id=<s:property value="accession.id" />&experimentId=<s:property value="id" />"><s:property value="title" /></a>
		<div><s:property value="description" /></div>
	</li>
</s:iterator>
</ul>
</body>
</html>


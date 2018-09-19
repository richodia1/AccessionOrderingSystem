<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Database schema for <fmt:message key="webapp.name" /><title>
</head>
<body>
<pre>
	<s:property value="schema" escape="false" />
</pre>

<s:if test="exceptions!=null && exceptions.size()>0">
	<h2>Exceptions</h2>
	<ul>
		<s:iterator value="exceptions">
			<li><s:property value="message" /></li>
		</s:iterator>
	</ul>
</s:if>
</body>
</html>
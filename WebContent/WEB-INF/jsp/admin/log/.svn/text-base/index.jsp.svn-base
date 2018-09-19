<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Log configuration</title>
</head>
<body>
<div iita:helptoc="tools/log"><s:form method="post" action="log!level">
	<s:select name="level" value="INFO" list="{'TRACE', 'DEBUG', 'INFO', 'WARN', 'ERROR', 'FATAL', 'ALL'}" />
	<s:textfield name="name" value="%{name}" cssStyle="width: 400px;"/>
	<s:submit value="Add logger" />
</s:form></div>

<div iita:helptoc="tools/log">
<s:iterator value="loggers.{? #this.level!=null}">
	<div><s:form method="post" action="log!level">
		<s:hidden name="name" value="%{name}" />
		<s:select name="level" value="%{level}" list="{'', 'TRACE', 'DEBUG', 'INFO', 'WARN', 'ERROR', 'FATAL', 'ALL'}" onchange="javascript: this.form.submit();" />
	</s:form><b><s:property value="name" /></b> <s:property value="level==null ? 'null' : level" /> <s:property value="parent.name" />
	</div>
</s:iterator>
</div>

<h3>Appenders</h3>
<s:iterator value="appenders">
	<b><s:property value="class.name" /></b>
</s:iterator>

<h3>All available loggers</h3>
<table class="data-listing">
<colgroup><col width="50" /><col width="150" /><col /></colgroup>
<thead>
<tr><td>Level</td><td>Group</td><td>Logger name</td></tr>
</thead>
<s:iterator value="loggers.{? #this.level==null }">
	<tr><td><s:property value="effectiveLevel" /></td><td><s:property value="parent.name" /></td><td class="identifying"><s:property value="name" /></td></tr>
</s:iterator>
</table>
</body>
</html>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Define new columns</title>
</head>
<body>
<p>Define new columns:</p>
<s:form action="experiment/upload!addcolumns" method="post">
	<s:hidden name="experimentId" value="%{experiment.id}" />
	<s:iterator value="newColumns" status="status">
		<h3><s:property value="xlsColumn.name" /></h3>
		<table class="inputform">
			<colgroup>
				<col width="200px" />
				<col />
			</colgroup>
			<tr>
				<td>Sample data:</td>
				<td><s:hidden name="newColumns[%{#status.index}].xlsColumnNum" value="%{xlsColumnNum}" /> <s:property value="xlsColumn.sampleData" /></td>
			</tr>
			<tr>
				<td>Title:</td>
				<td><s:textfield name="newColumns[%{#status.index}].title" value="%{title}" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><s:textarea cssClass="sizable-textarea" name="newColumns[%{#status.index}].description" value="%{description}" /></td>
			</tr>
			<tr>
				<td>Column:</td>
				<td><s:textfield name="newColumns[%{#status.index}].column" value="%{column}" /></td>
			</tr>
			<tr>
				<td>Data is:</td>
				<td><s:select name="newColumns[%{#status.index}].codedType" value="%{codedType}" list="#{0:'Actual values', 1:'Coded values',2:'Decoded values'}" /></td>
			</tr>
			<tr>
				<td>Data type:</td>
				<td><s:select name="newColumns[%{#status.index}].dataType" value="%{dataType}"
					list="#{'java.lang.String':'String values', 'java.lang.Long':'Big integer','java.lang.Double':'Floating point number', 'java.lang.Integer':'Integers and coded values', 'java.lang.Boolean':'Boolean, Yes/No values', 'java.util.Date':'Date'}" /></td>
			</tr>
		</table>
	</s:iterator>
	<s:submit value="Add columns" />
</s:form>
</body>
</html>
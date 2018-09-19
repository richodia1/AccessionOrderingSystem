<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Column profile</title>
</head>
<body>
<h1><s:property value="column.title" /></h1>
<s:form method="post" action="experiment/column!update">
	<s:hidden name="experiment" value="%{column.experiment.id}" />
	<s:hidden name="id" value="%{column.id}" />
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td>Title:</td>
			<td><s:textfield name="column.title" value="%{column.title}" /></td>
		</tr>
		<tr>
			<td>Description:</td>
			<td><s:textarea name="column.description" value="%{column.description}" /></td>
		</tr>
		<s:if test="column.id==null">
			<tr>
				<td>Column:</td>
				<td><s:textfield name="column.column" value="%{column.column}" /></td>
			</tr>
			<tr>
				<td>Column type:</td>
				<td><s:select name="column.columnType" list="@org.iita.accessions2.model.ColumnType@values()" value="%{column.columnType}"></s:select></td>
			</tr>
			<tr>
				<td>Data type:</td>
				<td><s:textfield name="column.dataType" value="%{column.dataType}" /></td>
			</tr>
		</s:if>
		<s:else>
			<tr>
				<td>Column:</td>
				<td><s:property value="column.column" /></td>
			</tr>
			<tr>
				<td>Column type:</td>
				<td><s:property value="column.columnType" /></td>
			</tr>
			<tr>
				<td>Data type:</td>
				<td><s:property value="column.dataType" /></td>
			</tr>
		</s:else>
		<tr>
			<td>Value prefix</td>
			<td><s:textfield name="column.prefix" value="%{column.prefix}" /></td>
		</tr>
		<tr>
			<td>Value postfix:</td>
			<td><s:textfield name="column.postfix" value="%{column.postfix}" /></td>
		</tr>
		<tr>
			<td>Displayed:</td>
			<td><s:select name="column.visible" value="%{column.visible}" list="#{'false':'Hidden', 'true':'Displayed'}" /></td>
		</tr>
	</table>


	<s:if test="column.coded">
		<h2>Coding</h2>
		<table class="data-listing">
			<colgroup>
				<col width="100px" />
				<col width="70px" />
				<col />
				<col width="100px" />
			</colgroup>
			<thead>
				<tr>
					<td>Coded</td>
					<td class="ar">Count</td>
					<td>Actual value</td>
					<td>Code</td>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="column.coding" status="status">
					<tr>
						<td><s:textfield name="column.coding[%{#status.index}].codedValue" value="%{codedValue}" cssClass="numeric-input" /></td>
						<td class="ar"><s:property value="dataHistogram.hashtable[codedValue]" /></td>
						<td><s:textfield name="column.coding[%{#status.index}].actualValue" value="%{actualValue}" cssStyle="width: 100%" /></td>
						<td><s:textfield name="column.coding[%{#status.index}].code" value="%{code}" /></td>
					</tr>
				</s:iterator>
				<tr>
					<td><s:textfield name="column.coding[%{column.coding.size()}].codedValue" value="" cssClass="numeric-input" /></td>
					<td></td>
					<td><s:textfield name="column.coding[%{column.coding.size()}].actualValue" value="" cssStyle="width: 100%" /></td>
					<td><s:textfield name="column.coding[%{column.coding.size()}].code" value="" /></td>
				</tr>
			</tbody>
		</table>
	</s:if>

	<div class="button-bar"><s:submit value="Update" /> <s:submit value="Delete column definition" action="experiment/column!clear"
		onclick="javascript: return window.confirm('Are you sure you want to delete column definition?');" /> <s:submit value="Delete column"
		action="experiment/column!drop" onclick="javascript: return window.confirm('Are you sure you want to COMPLETELY REMOVE column and ALL data?');" /></div>
</s:form>


<h2>Data distribution</h2>
<s:if test="100.0d * dataHistogram.size()/dataHistogram.total <= 10">
	<table class="data-listing">
		<colgroup>
			<col width="100px" />
			<col width="100px" />
			<col />
		</colgroup>
		<thead>
			<tr>
				<td class="ar">%</td>
				<td class="ar">Count</td>
				<td>Value</td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="dataHistogram.iterator()">
				<tr>
					<td class="ar"><s:text name="format.percent">
						<s:param name="value" value="100.0d * dataHistogram.hashtable[top]/dataHistogram.total" />
					</s:text></td>
					<td class="ar"><s:property value="dataHistogram.hashtable[top]" /></td>
					<td><s:property value="top" /> <s:if test="column.coded">
						<b><s:property value="column.decode(top)" /></b>
					</s:if></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

	<s:if test="!column.coded">

	</s:if>
	
</s:if>
<s:else>
	<p>Column probably contains actual values as more than 10% values are different. Will not display histogram.</p>
</s:else>
<div class="button-bar"><s:if test="column.coded">
	<s:form method="post" action="experiment/column!convertToDecoded">
		<s:hidden name="id" value="%{column.id}" />
		<s:submit value="Convert to decoded" />
	</s:form>
	<s:if test="column.coding.size==2">
		<s:form method="post" action="experiment/column!convertToBoolean">
			<s:hidden name="id" value="%{column.id}" />
			<s:submit value="Convert to Boolean" />
		</s:form>
	</s:if>
	
</s:if> <s:else>
	<s:form method="post" action="experiment/column!convertToCoded">
		<s:hidden name="id" value="%{column.id}" />
		<s:submit value="Convert to coded" />
	</s:form>
	<s:if test="column.dataType=='java.lang.Boolean'">
		<s:form method="post" action="experiment/column!convertToCoded">
			<s:hidden name="id" value="%{column.id}" />
			<s:submit value="Convert to coded" />
		</s:form>
	</s:if>
</s:else></div>
</body>
</html>
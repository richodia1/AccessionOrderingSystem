<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Germplasm request</title>
</head>
<body>
<h1>Order #<s:property value="order.id" /> <s:text name="order.state.%{order.state}" /></h1>

<s:if test="order.smtaAccepted">
	<p><b>Note:</b> SMTA is accepted and fully agreed with terms and conditions of germplasm distribution.</p>
</s:if>
<s:else>
	<div class="actionError">SMTA not accepted.</div>
</s:else>
<s:if test="order.pqpsDocumentFile==null">
	<p><b>Note:</b> PQPs import conditions file not provided yet.</p>
</s:if><s:elseif test="order.requiresImportPermit">
	<p><b>Note:</b> Requires import permit and file not provided yet.</p>
</s:elseif>


<s:form action="order/edit!update" method="post">
	<s:hidden name="id" value="%{order.id}" />
	<s:hidden name="order.version" value="%{order.version}" />
	<h2>Requestor</h2>
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td>Created:</td>
			<td><s:date name="order.createdDate" format="dd/MM/yyyy HH:mm" /> by <s:property value="order.createdBy" /></td>
		</tr>
		<tr>
			<td>Last modified:</td>
			<td><s:date name="order.lastUpdated" format="dd/MM/yyyy HH:mm" /> by <s:property value="order.lastUpdatedBy" /></td>
		</tr>
		<tr>
			<td>Last name:</td>
			<td><s:textfield name="order.requestor.lastName" value="%{order.requestor.lastName}" /></td>
		</tr>
		<tr>
			<td>First name:</td>
			<td><s:textfield name="order.requestor.firstName" value="%{order.requestor.firstName}" /></td>
		</tr>
		<tr>
			<td>Other names:</td>
			<td><s:textfield name="order.requestor.otherNames" value="%{order.requestor.otherNames}" /></td>
		</tr>
		<tr>
			<td>E-Mail address:</td>
			<td><s:textfield name="order.requestor.email" value="%{order.requestor.email}" /></td>
		</tr>
		<tr>
			<td>City:</td>
			<td><s:textfield name="order.requestor.city" value="%{order.requestor.city}" /></td>
		</tr>
		<tr>
			<td>Country:</td>
			<td><s:textfield name="order.requestor.country" value="%{order.requestor.country}" /></td>
		</tr>
	</table>


	<h2>Shipping address</h2>
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td>Last name:</td>
			<td><s:textfield name="order.shipTo.lastName" value="%{order.shipTo.lastName}" /></td>
		</tr>
		<tr>
			<td>First name:</td>
			<td><s:textfield name="order.shipTo.firstName" value="%{order.shipTo.firstName}" /></td>
		</tr>
		<tr>
			<td>Other names:</td>
			<td><s:textfield name="order.shipTo.otherNames" value="%{order.shipTo.otherNames}" /></td>
		</tr>
		<tr>
			<td>Address:</td>
			<td><s:textarea name="order.shipTo.address" value="%{order.shipTo.address}" /></td>
		</tr>
		<tr>
			<td>Postal code:</td>
			<td><s:textfield name="order.shipTo.postalCode" value="%{order.shipTo.postalCode}" /></td>
		</tr>
		<tr>
			<td>City:</td>
			<td><s:textfield name="order.shipTo.city" value="%{order.shipTo.city}" /></td>
		</tr>
		<tr>
			<td>Country:</td>
			<td><s:textfield name="order.shipTo.country" value="%{order.shipTo.country}" /></td>
		</tr>
	</table>


	<h2>Requested accessions</h2>
	<table class="data-listing">
		<colgroup>
			<col width="70px" />
			<col width="100px" />
			<col />
		</colgroup>
		<thead>
			<tr>
				<td>Quantity</td>
				<td>UOM</td>
				<td>Accession</td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="order.items" status="status">
				<tr>
					<td><s:textfield name="order.items[%{#status.index}].quantity" value="%{quantity}" /></td>
					<td><s:property value="uom" /></td>
					<td><s:property value="item.name" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

	<s:submit value="Update order" />
</s:form>
</body>
</html>
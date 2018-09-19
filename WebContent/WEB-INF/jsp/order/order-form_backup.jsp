<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Your request</title>
</head>
<body>
<h1>Request for IITA germplasm</h1>

<p>To order the selected germplasm please fill out the following form. You have already accepted the Standard Material Transfer Agreement (SMTA).</p>

<div style="width: 650px">
<s:form action="order!update" method="post">	
	<h2>About you</h2>
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td class="input-required">Last name:</td>
			<td><s:textfield name="order.requestor.lastName" value="%{order.requestor.lastName}" /></td>
		</tr>
		<tr>
			<td>First name:</td>
			<td><s:textfield name="order.requestor.firstName" value="%{order.requestor.firstName}" /></td>
		</tr>
		<tr>
			<td class="input-required">E-Mail address:</td>
			<td><s:textfield name="order.requestor.email" value="%{order.requestor.email}" />
			<div>Enter a valid e-mail address. A confirmation e-mail message will be sent to this address before we process your request.</div>
			</td>
		</tr>
		<tr>
			<td class="input-required">Organization:</td>
			<td><s:textfield name="order.requestor.organization" value="%{order.requestor.organization}" /></td>
		</tr>
		<tr>
			<td class="input-required">Organization type:</td>
			<td><s:select name="order.organizationType" value="%{order.organizationType}" list="{'CGIAR Centre','Commercial company','Farmer','Genebank','National agric. research (NARS)','Non-governmental organization (NGO)','Regional organization','University','Individual other than farmers'}" /></td>
		</tr>
		<tr>
			<td class="input-required">Shipping address:</td>
			<td><s:textarea name="order.requestor.address" value="%{order.requestor.address}" /></td>
		</tr>
		<tr>
			<td>Postal code:</td>
			<td><s:textfield name="order.requestor.postalCode" value="%{order.requestor.postalCode}" /></td>
		</tr>
		<tr>
			<td>City:</td>
			<td><s:textfield name="order.requestor.city" value="%{order.requestor.city}" /></td>
		</tr>
		<tr>
			<td class="input-required">Country:</td>
			<td><s:textfield name="order.requestor.country" value="%{order.requestor.country}" /></td>
		</tr>
	</table>

	<h2>Information on use of germplasm</h2>
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td class="input-required">Intended use:</td>
			<td><s:select name="order.intendedUse" value="%{order.intendedUse}" list="{'Research for food and agriculture','Farmer/NGO multiplication','Other uses (pharma, biofuels)'}" /></td>
		</tr>
		<tr>
			<td class="input-required">Project description:</td>
			<td><s:textarea name="order.description" value="%{order.description}" /></td>
		</tr>
	</table>

	<h2>Requested accessions</h2>
	<p><s:iterator value="items" status="status"><s:if test="#status.index>0">, </s:if><s:property value="name" /></s:iterator></p>
	<%--	<table class="data-listing">
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
	</table> --%>
	<s:if test="order.smtaAccepted">
		<p><b>Note:</b> You have accepted the SMTA and fully agree with terms and conditions of germplasm distribution.</p>
	</s:if>
	
	<s:submit value="Update order" />
	<s:submit value="Submit order" action="order!submit" />
	<s:submit value="Cancel order" action="order!cancel" />
</s:form>
<s:form method="get" action="order">
	<s:submit value="Reload page" />
</s:form>
</div>
</body>
</html>
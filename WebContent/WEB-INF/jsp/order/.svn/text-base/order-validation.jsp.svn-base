<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Request validation</title>
</head>
<body>
<h1>Request validation status</h1>

<s:if test="request==null">
	<s:if test="id!=null || key!=null">
		<p>The request could not be validated using the information you provided.</p>
	</s:if>
	<p>To validate your germplasm request, enter the request ID number and validation key.</p>
	<s:form method="post" action="validate">
		<table class="inputform">
			<colgroup>
				<col width="200" />
				<col />
			</colgroup>
			<tr>
				<td>Request ID:</td>
				<td><s:textfield name="id" value="%{id}" /></td>
			</tr>
			<tr>
				<td>Validation key:</td>
				<td><s:textfield name="key" value="%{key}" /></td>
			</tr>
			<tr>
				<td />
				<td><s:submit value="Validate request" /></td>
			</tr>
		</table>
	</s:form>
</s:if>
<s:else>
	<p>Your request has been validated. During the checkout process you have agreed to terms of Standard Material Transfer Agreement (SMTA).</p>

	<p>Contact the Plant Quarantine and Protection Services (PQPS) of <s:property value="request.shipTo.country" /> stipulating:</p>
	<ol>
		<li>Whether or not you need an import permit for the germplasm</li>
		<li>Phytosanitary certificate requirement</li>
	</ol>

	<p>For PQPS contact information use <a href="https://www.ippc.int/">https://www.ippc.int/</a> website.</p>

	<p>Please note that in case we do not have a valid Phytosanitary certificate for selected germplasm, we will only consider sending it to you upon receiving
	an official document endorsed by the Plant Quarantine and Protection Service (PQPS) of <s:property value="request.shipTo.country" /> stipulating that:</p>

	<ol>
		<li>PQPS of <s:property value="request.shipTo.country" /> allows you to import this germplasm.</li>
		<li>You are aware this germplasm is not certified free of disease.</li>
		<li>You take responsibility for introducing germplasm-associated pathogens to the country.</li>
	</ol>
	
	<p>Send all supporting documents as attachments to <b><a href="mailto:iita.genebank@iita.org?subject=Supporting document [Order #<s:property value="request.id" />]">iita.genebank@iita.org</a></b>
	with "Supporting documents for order #<s:property value="request.id" />" in email subject line.</p>
	
	<p>Upon receiving these documents, your request will be processed providing our stocks are compatible with your request.</p>
</s:else>
</body>
</html>
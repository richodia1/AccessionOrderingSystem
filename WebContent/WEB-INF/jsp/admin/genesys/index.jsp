<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>GeneSys-Accession2 Integration Interface</title>
</head>
<body>
<s:form action="genesys" method="post">
	<table>
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td>Access Token</td>
			<td><s:textfield name="accessToken" value="%{accessToken}" /></td>
		</tr>
		<tr>
			<td>Refresh Token</td>
			<td><s:textfield name="refreshToken" value="%{refreshToken}" /></td>
		</tr>
		<tr>
			<td>Expires On</td>
			<td><s:textfield name="expiresOn" value="%{expiresOn}" /></td>
		</tr>
		<tr>
			<td></td>
			<td><s:submit value="Get Access Token" action="genesys!checkTokens" onclick="javascript: return confirm('Are you sure you want to get access token?');" cssClass="button-submit" />
			<s:submit value="Refresh Access Token" onclick="javascript: return confirm('Are you sure you want to refresh access token?');" cssClass="button-submit" />
			</td>
		</tr>
	</table>
</s:form>
<fieldset>
	<legend>Prepare data for uploading to Genesys by following the steps</legend>
	<form action="">
		<fieldset style="width:95%;float:left;">
			<legend>1. Specify date/new accession range to update</legend>
			<fieldset style="width:40%;float:left;margin-right:10px;">
				<table>
					<tr>
						<td colspan="2"><s:checkbox fieldValue="all" name="all"></s:checkbox>All</td>
					</tr>
					<tr>
						<td>From</td>
						<td><iita:datepicker name="allfrom" value="%{allfrom}" /></td>
					</tr>
					<tr>
						<td>To</td>
						<td><iita:datepicker name="allto" value="%{allto}" /></td>
					</tr>
				</table>
			</fieldset>
			<fieldset style="width:40%;float:left;">
				<table>
					<tr>
						<td colspan="2"><s:checkbox fieldValue="new" name="new"></s:checkbox>New accessions</td>
					</tr>
					<tr>
						<td>From</td>
						<td><iita:datepicker name="newfrom" value="%{newfrom}" /></td>
					</tr>
					<tr>
						<td>To</td>
						<td><iita:datepicker name="newto" value="%{newto}" /></td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		
		<fieldset style="width:95%;float:left;">
			<legend>2. Select the fields to update</legend>
			<s:radio list="#{'ACQ_DATE':'ACQ_DATE', 'Collecting data':'Collecting data', 'Donor information':'Donor information'
			, 'Duplicate site':'Duplicate site', 'GIS data':'GID data', 'MLS status':'MLS status', 'ORI_COUNTRY':'ORI_COUNTRY'
			, 'SAMPLE_STAT':'SAMPLE_STAT', 'STATUS_ACC':'STATUS_ACC', 'Storage Type':'Storage Type', 'Taxonomy':'Taxonomy'}" 
			name="fields" value="%{fields}"></s:radio>
		</fieldset>
		
		<fieldset style="width:40%;float:left;margin-right:10px;">
			<legend>3. Provide the Limit and Offset</legend>
			<table>
					<tr>
						<td>Limit</td>
						<td><s:textfield name="limit" value="%{limit}" /></td>
					</tr>
					<tr>
						<td>Offset</td>
						<td><s:textfield name="offset" value="%{offset}" /></td>
					</tr>
				</table>
		</fieldset>
		
		<fieldset style="width:40%;float:left;">
			<legend>4. Click on appropriate button to process</legend>
			<table>
				<tr>
					<td></td>
					<td><s:submit value="Generate JSON" onclick="javascript: return confirm('Are you sure you want to generate JSON?');" cssClass="button-submit" />
					<s:submit value="Push to Genesys" action="genesys/uploadAll" onclick="javascript: return confirm('Are you sure you want to push generated data to Genesys?');" cssClass="button-submit" />
					</td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset style="width:95%;float:left;">
			<legend>Generated JSON Objects</legend>
			<s:textarea name="jsondata" cssStyle="width:100%" value="%{jsondata}"></s:textarea>
		</fieldset>
		
		<fieldset style="width:95%;float:left;">
			<legend>Status from Server</legend>
			<s:textarea name="servermonitor" cssStyle="width:100%" value="%{servermonitor}"></s:textarea>
		</fieldset>
	</form>
</fieldset>
</body>
</html>
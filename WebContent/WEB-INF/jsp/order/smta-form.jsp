<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Your request</title>
</head>
<body>
<h1>Request for IITA germplasm</h1>

<p>To order the selected germplasm, please enter your SMTA login credentials below. If NO SMTA profile, click this link <a href="http://mls.planttreaty.org/itt/index.php?r=user/register" target="_blank">NEW SMTA PROFILE</a> to create a profile.</p>

<div style="width: 650px">
<s:form action="order!getSmtaProfile" method="post">	
	<h2>SMTA Login Credentials</h2>
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td class="input-required">Username:</td>
			<td><s:textfield name="pid" value="" /></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><s:password name="password" value="" /></td>
		</tr>
	</table>
	
	<s:submit value="Submit" />
	<s:submit value="Cancel order" action="order!cancel" />
</s:form>
</div>
</body>
</html>
<%@ include file="/common/taglibs.jsp"%>

<title>Your Password settings</title>

<div id="actionMessages">This page allows you to configure your password settings to use IITA Network password or Custom generated password of your
choice.</div>

<h2>LDAP Password Setting</h2>
<s:if test='user.authenticationType.toString()=="DEFAULT" || user.authenticationType.toString()=="LDAP"'>
	<b>You are currently using your standard network domain username and password to login to this application.</b>
</s:if>
<s:else>
	<p>Choose this password option to use your standard network username and password to login to this application. Enter your network password to validate
	your settings.</p>
	<s:form action="user/changepasswordsetting!useLDAP" method="post">
		<table class="inputform">
			<colgroup>
				<col width="200" />
				<col />
			</colgroup>
			<tr>
				<td>Your IITA Network domain password:</td>
				<td><s:password name="ldapPassword" /></td>
			</tr>
			<tr>
				<td></td>
				<td><s:submit value="Use Network Password" /></td>
			</tr>
		</table>
	</s:form>
</s:else>



<h2>Custom Password Setting</h2>
<s:if test='%{user.authenticationType.toString()=="PASSWORD"}'>
	<b>You are currently using a custom password to login to this application.</b>
</s:if>
<s:else>
	<p>Choose this password option to use a custom password to login to this application.</p>
	<s:form action="user/changepasswordsetting!usePassword" method="post">
		<table class="inputform">
			<colgroup>
				<col width="200" />
				<col />
			</colgroup>
			<tr>
				<td>Password:</td>
				<td><s:password name="passwd1" /></td>
			</tr>
			<tr>
				<td>Retype Password:</td>
				<td><s:password name="passwd2" /></td>
			</tr>
			<tr>
				<td></td>
				<td><s:submit value="Use this Password" /></td>
			</tr>
		</table>
	</s:form>
</s:else>

<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title><s:property value="principal.lastName" />, <s:property value="principal.firstName" />: Delegated access</title>
</head>
<body>

<s:if test="user.id!=principal.id">
	<div class="notice">
	<p>You are currenly viewing data for: <b><s:property value="user.fullName" /></b>. Switch <a
		href="<s:url action="user/delegate!unswitch" />">back to yourself</a>.</p>
	</div>
</s:if>

<h1>Delegated to</h1>
<s:if test="delegatedTo!=null && delegatedTo.size>0">
	<p>You have granted access to your records to the users listed below:</p>
	<ul>
		<s:iterator value="delegatedTo">
			<li><a href="<s:url action="user/delegate!delete"><s:param name="email" value="%{mail}"/></s:url>">Delete</a> <s:property value="lastName" />, <s:property
				value="firstName" /></li>
		</s:iterator>
	</ul>
</s:if>
<s:else>
	<p>No other user is allowed to view and manage data on your behalf.</p>
</s:else>


<p>To allow another person to view and manage your records, please enter their e-mail address in the box below and click "Grant access" button.</p>
<s:form method="post" action="user/delegate!to">
	<table class="inputform" style="width: 400px;">
		<colgroup>
			<col width="180" />
			<col />
		</colgroup>
		<tr>
			<td class="tdLabel"><label>E-Mail:</label></td>
			<td><s:textfield name="email" /></td>
		</tr>
		<tr>
			<td />
			<td><s:submit value="Grant access" method="to" /></td>
		</tr>
	</table>
</s:form>


<h1>Delegated access from</h1>
<s:if test="delegatedFrom!=null && delegatedFrom.size()>0">
	<p>The following users have granted you access to their records.</p>
	<ul>
		<s:iterator value="delegatedFrom">
			<li><a href="<s:url action="user/delegate!switchuser"><s:param name="email" value="%{mail}"/></s:url>">Switch to</a> <s:property value="lastName" />, <s:property
				value="firstName" /></li>
		</s:iterator>
	</ul>
</s:if>
<s:else>
	<p>You are not allowed to manage other user's records.</p>
</s:else>
</body>
</html>
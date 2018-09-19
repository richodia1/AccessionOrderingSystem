<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>User roles</title>
</head>
<body>
<div style="margin: 5px 0px;">
	<b>All available roles: </b>
	<s:iterator value="allRoles">
		<a style="margin-left: 10px;" href="?role=<s:property />"><s:property value="top.substring(5)" /></a>
	</s:iterator>
</div>

<s:include value="include/userlist.jsp" />
</body>
</html>
<%@ include file="/common/taglibs.jsp"%>
<s:if test="user && user!=null">
	<s:if test="switched">
		<div><b><s:property value="principal.fullName" /></b> on behalf of <b><s:property value="user.fullName" /></b>. <a href="<s:url namespace="/" action="user/delegate!unswitch" />">Return to yourself</a>.</div>
	</s:if>
	<s:else>
		<div>Welcome, <b><s:property value="user.fullName" /></b></div>
	</s:else>
</s:if>
<s:else>
	<div>Click to <a href="<s:url namespace="/" action="login" />">log in</a>.</div>
</s:else>
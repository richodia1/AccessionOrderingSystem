<%@ include file="/common/taglibs.jsp"%>
<s:if test="locked">
	<div style="color: Red; font-weight: bold; float: right; font-size: 20px; position: static;"><a href="<s:url action="applock" namespace="/admin" />">Access blocked</a></div>
</s:if>
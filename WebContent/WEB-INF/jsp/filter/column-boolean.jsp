<%@ include file="/common/taglibs.jsp"%>
<ul>
	<li><a href="<s:url action="filter!add" />?experiment=<s:property value="[1].experiment" />&field=<s:property value="[1].field" />&boolValue=true">Yes</a></li>
	<li><a href="<s:url action="filter!add" />?experiment=<s:property value="[1].experiment" />&field=<s:property value="[1].field" />&boolValue=false">No</a></li>
	<li><a href="<s:url action="filter!add" />?experiment=<s:property value="[1].experiment" />&field=<s:property value="[1].field" />"><em>Not specified</em></a></li>
</ul>
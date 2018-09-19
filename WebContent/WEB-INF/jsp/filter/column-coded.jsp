<%@ include file="/common/taglibs.jsp"%>
<p>this <s:property value="title" /> column is coded. Showing list of options</p>
<ul>
<s:iterator value="coding">
	<li><a href="<s:url action="filter!add" />?experiment=<s:property value="[2].experiment" />&field=<s:property value="[2].field" />&intValue=<s:property value="codedValue" />"><s:property value="actualValue" /></a></li>
</s:iterator>
	<li><a href="<s:url action="filter!add" />?experiment=<s:property value="[1].experiment" />&field=<s:property value="[1].field" />"><em>Not specified</em></a></li>
</ul>
<%@ include file="/common/taglibs.jsp"%>
<s:form method="post" action="filter!add">
<s:hidden name="experiment" value="%{[1].experiment}" />
<s:hidden name="field" value="%{[1].field}" />
<ul>
	<li>Containing text: <input type="text" name="strValue" /></li>
</ul>
<s:submit value="Add text filter" />
</s:form>
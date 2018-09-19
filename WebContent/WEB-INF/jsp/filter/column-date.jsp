<%@ include file="/common/taglibs.jsp"%>
<s:form method="post" action="filter!addrange">
<s:hidden name="experiment" value="%{[1].experiment}" />
<s:hidden name="field" value="%{[1].field}" />
<ul>
	<li>Minimum date: <iita:datepicker name="dateValue" /></li>
	<li>Maximum date: <iita:datepicker name="dateValue2" /></li>
</ul>
<s:submit value="Add date filter" />
</s:form>
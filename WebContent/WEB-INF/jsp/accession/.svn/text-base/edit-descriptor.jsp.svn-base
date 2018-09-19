<%@ include file="/common/taglibs.jsp"%>

<s:if test="prefix!=null"><s:property value="prefix" /></s:if>

<s:if test="coded">
	<s:select value="%{experimentData[column]}" name="experimentData['%{column}']" list="coding" listKey="codedValue"  listValue="actualValue" emptyOption="true" />
</s:if>
<s:elseif test="dataType=='java.lang.Boolean'">
	<s:select value="%{experimentData[column]}" name="experimentData['%{column}']" list="#{'true':'Yes','false':'No'}" emptyOption="true" />
</s:elseif>
<s:else>
	<s:textfield name="experimentData['%{column}']" value="%{experimentData[column]}" />
</s:else>

<s:if test="[1].postfix!=null"><s:property value="[1].postfix" /></s:if>
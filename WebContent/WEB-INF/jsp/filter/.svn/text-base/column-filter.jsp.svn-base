<%@ include file="/common/taglibs.jsp"%>
<p>Please click on the value below to add it to filter (<s:property value="dataType" />)</p>
<s:if test="coded">
	<%@ include file="column-coded.jsp" %>
</s:if>
<s:elseif test="dataType=='java.lang.Boolean'">
	<%@ include file="column-boolean.jsp" %>
</s:elseif>
<s:elseif test="dataType=='java.lang.Double' || dataType=='java.lang.Integer' || dataType=='java.lang.Long'">
NUM
	<%@ include file="column-numeric.jsp" %>
</s:elseif>
<s:elseif test="dataType=='java.lang.String'">
	<%@ include file="column-string.jsp" %>
</s:elseif>
<s:elseif test="dataType=='java.util.Date' || dataType=='java.util.Calendar'">
	<%@ include file="column-date.jsp" %>
</s:elseif>
<s:else>
ERRO
	<div class="errorMessage">Sorry, don't know how to filter <b><s:property value="dataType" /></b>....</div>
</s:else>
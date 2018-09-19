<%@ include file="/common/taglibs.jsp"%>
<s:if test="[1].prefix!=null">
	<s:property value="[1].prefix" />
</s:if>

<s:if test="%{top instanceof java.lang.Boolean}">
	<s:if test="top">
		<b>Yes</b>
	</s:if>
	<s:else>
		<b>No</b>
	</s:else>
</s:if>
<s:elseif test="[1].coded">
<%--
	Removed on Dumet's request
	<s:if test="#accessionsUtil.descriptorHasImage([1].top, [1].findCoding(top).code)">
		<img class="descriptorImage" src="<c:url value="/img/descriptors" />/<s:property value="[1].experiment.tableName.toLowerCase()" />/<s:property value="[1].column" />_<s:property value="[1].findCoding(top).code" />.jpg" />
	</s:if>
	<s:else>
		<!-- No image <s:property value="[1].experiment.tableName.toLowerCase()" />/<s:property value="[1].column" />_<s:property value="[1].findCoding(top).code" />.jpg -->
	</s:else>
--%>
	<b><s:property value="[1].decode(top)" /></b>
</s:elseif>
<s:else>
	<b><s:property /></b>
</s:else>

<s:if test="[1].postfix!=null">
	<s:property value="[1].postfix" />
</s:if>
<%@ include file="/common/taglibs.jsp"%>

<s:if test="coded">
<p>Accessions that match your current filters have the following values for <s:property value="selectedColumn.title" />. Click on the 
	value to add it to your filters.</p>
	<div style="max-height: 300px; overflow-y: scroll;">
	<table>
	<!-- Null values -->
	<s:set name="matches" value="%{countMatching(null)}" />
	<tr><td><img src="<c:url value="/img/edit_add.png" />" style="height: 10px;" /> <a href="<s:url action="collection/filter!add" />?id=<s:property value="[1].collection.id" />&amp;col=<s:property value="id" />"><em>Not specified</em></a>:  <s:if test="#matches==null">No filtered accessions match this value.</s:if><s:else><b><s:property value="#matches" /></b> matches</s:else></td><td /></tr>
	<s:iterator value="codingSorted">
		<s:set name="matches" value="%{countMatching(top.codedValue)}" />
		<s:if test="#matches!=null">
			<tr>
				<td><img src="<c:url value="/img/edit_add.png" />" style="height: 10px;" /> <a href="<s:url action="collection/filter!add" />?id=<s:property value="[2].collection.id" />&amp;col=<s:property value="[1].id" />&amp;intValue=<s:property value="codedValue" />"><s:property value="actualValue" /></a>:  <s:if test="#matches==null">No filtered accessions match this value.</s:if><s:else><b><s:property value="#matches" /></b> matches</s:else></td>
				<td class="ar"><s:if test="#accessionsUtil.descriptorHasImage([1].top, code)">
					<img style="max-height: 100px;" src="<c:url value="/img/descriptors" />/<s:property value="[1].experiment.tableName.toLowerCase()" />/<s:property value="[1].column" />_<s:property value="code" />.jpg" />
				</s:if></td>
			</tr>
		</s:if>
	</s:iterator>
	</table>
	</div>
</s:if>
<s:elseif test="dataType=='java.lang.Boolean'">
<p>Accessions that match your current filters have the following values for <s:property value="selectedColumn.title" />. Click on the 
	value to add it to your filters.</p>
	<ul style="max-height: 200px; overflow-y: scroll;">
	<!-- Null values -->
	<s:set name="matches" value="%{countMatching(null)}" />
	<li><a href="<s:url action="collection/filter!add" />?id=<s:property value="[1].collection.id" />&amp;col=<s:property value="id" />"><em>Not specified</em></a>:  <s:if test="#matches==null">No filtered accessions match this value.</s:if><s:else><b><s:property value="#matches" /></b> matches</s:else></li>
	<s:iterator value="{true,false}">
		<s:set name="matches" value="%{countMatching(top)}" />
		<s:if test="#matches!=null">
		<li><a href="<s:url action="collection/filter!add" />?id=<s:property value="[2].collection.id" />&amp;col=<s:property value="[1].id" />&amp;boolValue=<s:property value="top" />"><s:property value="top ? 'Yes' : 'No'" /></a>:  <s:if test="#matches==null">No filtered accessions match this value.</s:if><s:else><b><s:property value="#matches" /></b> matches</s:else></li>
		</s:if>
	</s:iterator>
	</ul>
</s:elseif>
<s:else>
<p>This descriptor can only be added to the display list.</p>
<%--	<s:textfield name="experimentData['%{column}']" value="%{experimentData[column]}" />
	<s:property value="dataType" />
	--%>
</s:else>

<div><a href="<s:url action="collection/filter!addDisplay" />?id=<s:property value="collection.id" />&amp;col=<s:property value="id" />">Display <b><s:property value="title" /></b> column in accession list</a></div>
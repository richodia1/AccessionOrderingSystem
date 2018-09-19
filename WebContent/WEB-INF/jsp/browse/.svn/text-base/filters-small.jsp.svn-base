<%@ include file="/common/taglibs.jsp"%>
<s:iterator value="filters" status="status">
		<s:set name="column" value="#accessionsUtil.getColumn(tableName, column)" />
		<s:if test="#column!=null">
		<s:push value="#column">
			<s:if test="!#status.first">, </s:if>
			<b><s:property value="title" /></b> is
				<s:if test="coded">
					<s:iterator value="filterValues" status="status">
					<b><s:if test="top==null"><em>Not specified</em></s:if><s:else>
						<s:property value="[1].decode(top)" />
					</s:else></b>
					<s:if test="!#status.last"> or </s:if>
					</s:iterator>
				</s:if>
				<s:else>
					<s:iterator value="filterValues" status="status">
					<b><s:if test="top==null"><em>Not specified</em></s:if><s:else>		
						<s:if test="top instanceof org.iita.accessions2.service.Filters$FilterRangeValue">
							<s:property value="minValue" /> - <s:property value="maxValue" />
						</s:if>
						<s:else>
							<s:property value="top" />
						</s:else>
					</s:else></b>
					<s:if test="!#status.last"> or </s:if>
					</s:iterator>
				</s:else>
		</s:push>
		</s:if>
	</s:iterator>
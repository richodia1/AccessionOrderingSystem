<%@ include file="/common/taglibs.jsp"%>
<table class="data-listing">
	<s:if test="[1].query!=null">
		<thead>
			<tr>
				<td width="10">#</td>
				<s:iterator value="[1].query.headings">
					<td><s:property /></td>
				</s:iterator>
			</tr>
		</thead>
	</s:if>
	<tbody>
		<s:iterator value="results" status="status">
			<tr>
				<td width="10" class="identifying"><s:property value="startAt + #status.index+1" /></td>
				<s:iterator value="top">
					<td><s:if test="top instanceof java.lang.String">
						<s:if test="top.startsWith('http://')">
							<s:property />
						</s:if>
						<s:elseif test="top.startsWith('http:/')">
							<a href="<c:url value="/" /><s:property value="top.replace('http:/', '')" />">Link</a>
						</s:elseif>
						<s:else>
							<s:property />
						</s:else>
					</s:if> <s:else>
						<s:if test="top instanceof java.util.List">
							<s:iterator value="top">
								<s:property />
								<s:property value="class" />
							</s:iterator>
						</s:if>
						<s:else>
							<s:property />
						</s:else>
					</s:else></td>
				</s:iterator>
			</tr>
		</s:iterator>
	</tbody>
</table>
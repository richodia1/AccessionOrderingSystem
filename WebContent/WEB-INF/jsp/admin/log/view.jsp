<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Log browser</title>
</head>
<body>
<s:if test="memoryAppender!=null">
	<div class="actionMessage">Only last <s:form method="post" action="log/view!reconfigure">
		<s:select name="maxEvents" value="memoryAppender.maxEvents" list="{'10','50','100','200','500'}" onchange="javascript: this.form.submit();" />
	</s:form> logged events are kept in memory. <a href="<s:url action="log/view" />">Refresh this page</a> <a href="<s:url action="log/view!clear" />">Clear buffered
	messages</a></div>

	<table class="data-listing">
		<colgroup>
			<col width="200" />
			<col width="100" />
			<col />
		</colgroup>
		<thead>
			<tr>
				<td>Time</td>
				<td>Level</td>
				<%--<td>Logger</td> --%>
				<td>Message</td>
			</tr>
		</thead>
		<s:iterator value="memoryAppender.recentEventLog" status="status">
			<tr>
				<td><s:property value="@java.lang.String@format('%1$tF %1$tT,%1$tL', timeStamp)" /></td>
				<td class="identifying"><a href="<s:url action="/log" />?name=<s:property value="categoryName" />" title="Log category configuration"><s:property value="level" /></a></td>
				<%--<td><s:property value="categoryName" /></td> --%>
				<td><s:property value="[1].memoryAppender.layout.format(top)" /></td>
			</tr>
		</s:iterator>
	</table>
</s:if>
<s:else>
	<div class="errorMessage">MemoryAppender not configured in this application.</div>
</s:else>
</body>
</html>
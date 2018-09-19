<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Notifications</title>
</head>
<body>
<a href="<s:url action="user/notification/manage" />">Manage application notifications</a>
<iita:collapse id="sendmessage" closedHeading="Send message...">
		<s:include value="/WEB-INF/jsp/user/sendmail-form.jsp" />
	</iita:collapse>
<s:if test="paged.totalHits > 0">
	<s:include value="/WEB-INF/jsp/paging.jsp">
		<s:param name="page" value="paged.page" />
		<s:param name="pages" value="paged.pages" />
		<s:param name="pageSize" value="paged.pageSize" />
		<s:param name="href" value="%{''}" />
	</s:include>
	<table class="data-listing">
		<colgroup>
			<col />
			<col width="170px" />
			<col width="200px" />
		</colgroup>
		<thead>
			<tr>
				<td>Title</td>
				<td>Date/Time</td>
				<td class="ar"><a href="<s:url action="user/notification!delAll" />"><img title="Delete All" src="<c:url value="/img/remove.png" />"
					alt="Delete All" /></a></td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="paged.results">
				<tr>
					<td><a href="<s:url action="user/notification!read" />?id=<s:property value="id" />"> <s:if test="read">
						<iita:text value="%{title}" maxLength="60" />
					</s:if> <s:else>
						<b><iita:text value="%{title}" maxLength="60" /></b>
					</s:else> </a></td>
					<td><iita:date name="createdDate" format="dd/MM/yyyy HH:mm" /></td>
					<td class="ar"><a href="<s:url namespace="/" action="user/notification!toggle" />?id=<s:property value="id" />">Toggle read</a> <a
						href="<s:url action="user/notification!del" />?id=<s:property value="id" />"><img title="Delete" src="<c:url value="/img/remove.png" />" alt="Delete" /></a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>
<s:else>
	<p>No notifications!</p>
</s:else>
</body>
</html>
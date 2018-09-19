<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order list</title>
</head>
<body>
<s:if test="paged!=null && paged.totalHits>0">
	<s:include value="/WEB-INF/jsp/paging.jsp">
		<s:param name="page" value="paged.page" />
		<s:param name="pages" value="paged.pages" />
		<s:param name="pageSize" value="paged.pageSize" />
		<s:param name="href" value="%{''}" />
	</s:include>

	<table class="data-listing">
		<colgroup>
			<col width="140px" />
			<col />
			<col width="200px" />	
			<col width="100px" />			
		</colgroup>
		<thead>
			<tr>
				<td class="ar">Last modified</td>
				<td>Requestor</td>
				<td>State</td>
				<td class="ar">Items</td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="paged.results">
				<tr>
					<td class="ar"><s:date name="lastUpdated" format="dd/MM/yyyy" /></td>
					<td><a href="<s:url action="order/edit" />?id=<s:property value="id" />"><s:property value="requestor.lastName" />, <s:property value="requestor.firstName" /></a></td>
					<td><s:text name="order.state.%{state}" /></td>
					<td class="ar"><s:property value="items.size()" /> items</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>
<s:else>
No orders have been placed.
</s:else>
</body>
</html>
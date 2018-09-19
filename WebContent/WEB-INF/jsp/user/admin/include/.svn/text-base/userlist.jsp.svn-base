<%@ include file="/common/taglibs.jsp"%>
<s:if test="paged.totalHits>0">
	<s:include value="/WEB-INF/jsp/paging.jsp">
		<s:param name="page" value="paged.page" />
		<s:param name="pages" value="paged.pages" />
		<s:param name="pageSize" value="paged.pageSize" />
		<s:param name="href" value="%{''}" />
	</s:include>

	<table class="data-listing">
		<colgroup>
			<col width="20%" />
			<col width="20%" />
			<col />
			<col width="20%" />
		</colgroup>
		<thead>
			<tr>
				<td>Name</td>
				<td>Mail</td>
				<td>Login</td>
				<td>Failed</td>
				<td class="ar">Tools</td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="paged.results">
			<s:url id="editUrl" action="user/user!input"><s:param name="id" value="%{id}" /></s:url>
				<tr class="<s:if test="status==@org.iita.security.model.UserStatus@DISABLED">row-error</s:if>">
					<td><s:a href="%{editUrl}"><s:property value="lastName" />, <s:property value="firstName" /></s:a></td>
					<td><s:property value="mail" /></td>
					<td><iita:date name="lastLogin" format="%{getText('date.format')} HH:mm:ss" /></td>
					<td><iita:date name="lastLoginFailed" format="%{getText('date.format')} HH:mm:ss" /></td>
					<td class="ar">
					<s:if test="id==null">
						<a href="<s:url method="users!search" />?filter=<s:property value="mail" />">Import</a>
					</s:if>
					<s:else>
					<s:url id="switchUrl" action="user/user!switchto">
						<s:param name="id" value="%{id}" />
					</s:url> <s:a href="%{switchUrl}">Switch to</s:a> <s:url id="removeUrl" action="user/user!delete">
						<s:param name="id" value="%{id}" />
					</s:url> <s:a onclick="javascript: return window.confirm('Are you sure you want to delete user?');" href="%{removeUrl}">Remove</s:a>
					 <s:a href="%{editUrl}">Edit</s:a>
					</s:else>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>
<s:else>
	<p>No results!</p>
</s:else>
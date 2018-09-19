<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Templates</title>
</head>
<body>
<s:form method="post" action="template/profile!update">
	<s:hidden name="shortName" value="%{shortName}" />
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td class="label">Title</td>
			<td><s:textfield name="template.title" value="%{template.title}" /></td>
		</tr>
		<tr>
			<td class="label">Short name</td>
			<td><s:textfield name="template.shortName" value="%{template.shortName}" /></td>
		</tr>
		<tr>
			<td class="label">Header</td>
			<td><s:textarea name="template.header" value="%{template.header}" cssClass="sizable-textarea" /></td>
		</tr>
		<tr>
			<td class="label">Body</td>
			<td><s:textarea name="template.template" value="%{template.template}" cssClass="sizable-textarea" /></td>
		</tr>
		<tr>
			<td class="label">Footer</td>
			<td><s:textarea name="template.footer" value="%{template.footer}" cssClass="sizable-textarea" /></td>
		</tr>
		<tr>
			<td />
			<td>
			<div class="button-bar"><s:submit value="Update" /><s:submit action="template/profile!duplicate" value="Duplicate" /><s:if test="template.id!=null"><s:submit value="Remove" onclick="javascript: return confirm('Are you sure you want to delete the template?');" action="template/profile!remove" cssClass="button-delete" /></s:if><input type="button" onclick="javascript: window.location.href='<s:url action="template/index" />';" value="To list" /> <input type="reset" value="Reset" /></div>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>
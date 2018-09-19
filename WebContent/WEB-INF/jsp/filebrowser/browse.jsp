<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Browsing <s:property value="browserTitle" /> <s:property value="directory" /></title>
</head>
<body>

<s:form method="post" action="%{action}">
<s:hidden name="directory" value="%{directory}" />

<table class="data-listing">
<colgroup>
<col width="30px" /><col /><col width="150px" /><col width="150px" />
</colgroup>
<thead><tr><td /><td>Name</td><td class="ar">Size</td><td class="ar">Last modified</td></tr></thead>
<tbody>
	<tr>
	<td />
	<td>
		<a href="?id=<s:property value="id" />&directory=<s:property value="directory" />"><i>Refresh</i></a>
	<s:if test="directory!=null && directory.length()>0">
		<a href="?id=<s:property value="id" />"><i>Go to root folder</i></a>
		<a href="?id=<s:property value="id" />&directory=<s:property value="parentDirectory" />"><i>Go to parent folder</i></a>
	</s:if>
	</td><td /><td /></tr>
<s:if test="files.size>0">
	<s:set name="totalSize" value="0" />
	<s:iterator value="files">
		<tr>
			<td><input type="checkbox" name="selectedFiles" value="<s:property value="fileName" />" /></td>
			<td class="${file.directory?'directory':'file'}"><s:if test="file.directory">
				<a href="<s:url action="%{action}" />?id=<s:property value="id" />&directory=<s:property value="directory" />/<s:property value="fileName" />"><s:property value="fileName" /></a>
			</s:if>
			<s:else>
				<a href="<s:url action="%{action}!download" />?id=<s:property value="id" />&directory=<s:property value="directory" />&file=<s:property value="fileName" />"><s:property value="fileName" /></a>
			</s:else></td>
			<td class="ar"><s:if test="file.file"><iita:disksize value="file.length()" /><s:set name="totalSize" value="#totalSize + file.length()" /></s:if></td>
			<td class="ar"><iita:date name="new java.util.Date(file.lastModified())" format="dd/MM/yyyy HH:mm" /></td>
		</tr>
	</s:iterator>
</s:if>
<s:else>
	<tr><td colspan="4">No files found.</td></tr>
</s:else>
</tbody>
<tfoot>
<tr><td colspan="2">Files: <s:property value="files.size" /></td><td class="ar"><iita:disksize value="#totalSize" /></td><td /></tr>
</tfoot>
</table>
<s:submit action="%{action}!remove" value="Delete selected" onclick="javascript: return window.confirm('Are you sure you want to delete selected files?');" />
</s:form>
<s:form method="post" action="%{action}!mkdir">
	<s:hidden name="directory" value="%{directory}" />
	Create new directory: <s:textfield name="newDirectory" value="" /> <s:submit value="Create" />
</s:form>

<h3>Upload files to this directory</h3>
<iita:filedropzone action="%{action}!upload" queryParams="id=${id}&directory=${directory}">
	<p>You can attach files by dragging-and-dropping them right HERE! The <b>copy</b> icon will appear.</p>
</iita:filedropzone>

</body>
</html>
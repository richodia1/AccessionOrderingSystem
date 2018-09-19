<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Descriptors for <s:property value="experiment.title" /></title>
</head>
<body>
<h1>Descriptors <s:property value="experiment.title"  /></h1>
<div>Authors: <s:property value="experiment.authors" /></div>
<div><s:property value="experiment.description" /></div>

<s:set name="level" value="0" />
<s:iterator value="experiment.columns" status="status">
	<s:if test="visible">
		<s:set name="level" value="%{#level+1}" />
		<h3><s:property value="#level"/>. <s:property value="title" /></h3>
		<s:if test="description!=null && description.trim().length()>0"><p><s:property value="description" /></p></s:if>
		<p style="margin: 2px 0;"><small>
			<s:if test="prefix!=null && prefix.trim().length()>0">Prefix: <b><s:property value="prefix" /></b>.</s:if>
			<s:if test="postfix!=null && postfix.trim().length()>0">Unit of measure, suffix: <b><s:property value="postfix" /></b>.</s:if>
			Data type: <s:property value="dataType" />.
		</small></p>
		<s:if test="coded">
			<div style="max-height: 200px; overflow-y: auto;">
			<table style="margin-left: 50px; width: auto;">
				<s:iterator value="coding">
					<tr>
						<td><s:property value="codedValue" /> - <s:property value="actualValue" /></td>
						<td><s:if test="#accessionsUtil.descriptorHasImage([1].top, code)">
							<img style="max-height: 100px;" src="<c:url value="/img/descriptors" />/<s:property value="[1].experiment.tableName.toLowerCase()" />/<s:property value="[1].column" />_<s:property value="code" />.jpg" />
						</s:if></td>
					</tr>
				</s:iterator>
			</table>
			</div>
		</s:if>
	</s:if>
</s:iterator>

</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title><decorator:title default="Untitled page" /> | <fmt:message key="webapp.name" /> Administration</title>
<%@ include file="/common/meta.jsp"%>
<decorator:head />
<script type="text/javascript" src="<c:url value="/help/script/help.js" />"></script>
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/>>

<div style="" class="noprint">
<table style="width: 100%">
	<col width="150px" />
	<col />
	<tr>
		<td rowspan="2" style="vertical-align: top; padding-right: 10px;"><a href="<s:url action="index" namespace="/admin" />" title="Go to dashboard of <fmt:message key="webapp.name" />"><img src="<c:url value='/img/logo.gif'/>" alt="IITA"
			style="float: left;" /></a></td>
		<td style="vertical-align: top; padding: 0;">
			<s:action name="applock-status" namespace="/" executeResult="true" ignoreContextParams="true" />
			<div style="padding: 3px 6px; background-color: rgb(240, 240, 240);">
				<div style="float: right;"><s:include value="/WEB-INF/jsp/admin/fast-switch.jsp" /> <%-- Search: <form method="get" action="<s:url action='search' />"><input name="q" style="width: 200px;" value="" /> <input type="submit" value="Search" /></form> --%></div>
				<h1 style="margin: 0px 0px 3px 0px; padding: 0px; font-size: 1.5em;">Administration: <fmt:message key="webapp.name" /></h1>
			</div>			
		</td>
	</tr>
	<tr>
		<td style="vertical-align: bottom; padding: 5px 0 0 0;">
			<div style="margin: 1pt 0px 6px 0px;"><jsp:include page="/common/adminmenu.jsp" /></div>
		</td>
	</tr>
</table>
</div>

<div class="noprint" style="margin-top: 3px; border-top: solid 1px black; background-color: #EACE7C; min-height: 16px; padding: 3px 10px 2px 10px; font-weight: bold;"><div style="float: right"><s:action name="user-info" namespace="/" executeResult="true" ignoreContextParams="true" /></div><decorator:title
	default="Untitled page" /></div>

<table style="height: 100%;">
<colgroup><col width="240px" /><col /></colgroup>
<tr>
	<td id="admin-sidebar" style="background-color: #eaeaea; padding: 10px; border-right: solid 1px #a0a0a0">
		<%@ include file ="/common/adminmenu-side.jsp" %>
	</td>
	<td style="padding: 10px;">
		<div id="main">
			<h2><decorator:title default="Untitled page" /></h2>
			<%@ include file="/common/messages.jsp" %>
			<decorator:body />
		</div>
	</td>
</tr>
</table>

<jsp:include page="/common/footer.jsp" />
<jsp:include page="/common/sessionkeeper.jsp" />
<script type="text/javascript">
Event.observe(window, "load", function() {
	// provide base href to help content
	IITAHELP = new IITA.Help("<c:url value="/" />help/admin/");
});
</script>
</body>
</html>

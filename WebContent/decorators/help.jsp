<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title><decorator:title default="Help" /> | <fmt:message key="webapp.name" /></title>
<%@ include file="/common/meta.jsp"%>
<decorator:head />
</head>
<body <decorator:getProperty property="body.id" writeEntireProperty="true"/> <decorator:getProperty property="body.class" writeEntireProperty="true"/>>

<div style="" class="noprint">
<table style="width: 100%">
	<col width="150px" />
	<col />
	<tr>
		<td rowspan="2" style="vertical-align: top; padding-right: 10px;"><a href="<s:url action="index" namespace="/" />" title="Go to dashboard of <fmt:message key="webapp.name" />"><img src="<c:url value='/img/logo.gif'/>" alt="IITA"
			style="float: left;" /></a></td>
		<td style="vertical-align: top; padding: 0;">
			<s:action name="applock-status" namespace="/" executeResult="true" ignoreContextParams="true" />
			<div style="padding: 3px 6px; background-color: rgb(240, 240, 240);">
				<div style="float: right;">
					
				</div>
				<h1 style="margin: 0px 0px 3px 0px; padding: 0px; font-size: 1.5em;">Help: <fmt:message key="webapp.name" /></h1>
			</div>			
		</td>
	</tr>
	<tr>
		<td style="vertical-align: bottom; padding: 5px 0 0 0;">
			<div style="margin: 1pt 0px 6px 0px;"><jsp:include page="/common/help-menu.jsp" /></div>
		</td>
	</tr>
</table>
</div>

<div class="noprint" style="margin-top: 3px; border-top: solid 1px black; background-color: #EACE7C; min-height: 16px; padding: 3px 10px 2px 10px; font-weight: bold;"><div style="float: right"><s:action name="user-info" namespace="/" executeResult="true" ignoreContextParams="true" /></div><decorator:title
	default="Untitled page" /></div>

<div style="margin: 10px 10px 0px 10px;">
	<div id="main" style="width: 790px; padding: 0 10px;">
	<%@ include file="/common/messages.jsp"%>
	<decorator:body />
	</div>
</div>

<jsp:include page="/common/footer.jsp" />
<jsp:include page="/common/sessionkeeper.jsp" />
<script type="text/javascript" src="<s:url value="/script/gears/gears_init.js" />"></script>
<s:action name="google/analytics" namespace="/" executeResult="true" ignoreContextParams="true" />
</body>
</html>

<%@ page language="java" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>

<page:applyDecorator name="plain">
<html>
<head>
<title><fmt:message key="errorPage.title" /></title>
</head>

<body id="error">
<div id="page">
<div id="content" class="clearfix">
<div id="main">
<h1><fmt:message key="errorPage.heading" /></h1>
<p>We apologize for the inconvenience. Please copy-paste error details below and send to <a href="mailto:software.support@iita.org?subject=Error report">software.support@iita.org</a>.</p>
<p>Also you could navigate <a onClick="javascript: window.history.go(-1);">to previous page</a>.</p>

<h2>Error details</h2>
<%
	if (exception != null) {
%> <pre>
<%
	exception.printStackTrace(new java.io.PrintWriter(out));
%>
</pre> <%
 	} else if (request.getAttribute("javax.servlet.error.exception") != null) {
 %> <pre>
<%
	((Exception) request.getAttribute("javax.servlet.error.exception")).printStackTrace(new java.io.PrintWriter(out));
%>
</pre> <%
 	}
 %>
</div>
</div>
</div>
</body>
</html>
</page:applyDecorator>
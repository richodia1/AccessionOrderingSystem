<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>

<page:applyDecorator name="plain">
<html>
<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
</head>
<body>
<h1>404 Error: Requested URL does not exist</h1>
<p>
    <fmt:message key="404.message">
        <fmt:param><c:url value="/"/></fmt:param>
    </fmt:message>
</p>
<p>Also you could navigate <a onClick="javascript: window.history.go(-1);">to previous page</a>.</p>
</body>
</html>
</page:applyDecorator>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Experiment column sort: <s:property value="experiment.title" /></title>
<script type="text/javascript" src="<c:url value='/script/scriptaculous/dragdrop.js'/>"></script>
</head>
<body>
<s:include value="navigation.jsp" />
<div class="actionMessage">Drag and drop column names into order.</div>

<s:form method="post" action="experiment/sort!sort">
<s:hidden name="id" value="%{experiment.id}" />
<ul id="sort-order">
<s:iterator value="columns">
	<li style="margin: 5px 0"><b><s:property value="title" /></b>
		<s:hidden name="sortOrder" value="%{id}" />
		<s:property value="description" />
	</li>
</s:iterator>
</ul>
<s:submit value="Save sort order" />
</s:form>

<script type="text/javascript">
Sortable.create($('sort-order'), {ghosting:false, constraint:true, onChange: function(element) { }});
</script>
</body>
</html>

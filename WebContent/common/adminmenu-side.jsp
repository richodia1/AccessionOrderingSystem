<%@ include file="/common/taglibs.jsp"%>

<h2>Accession DB</h2>
<ul>
	<li><a href="<s:url namespace="/admin" action="experiment/index" />">Experiments</a></li>
	<li><a href="<s:url namespace="/admin" action="experiment/upload" />">Upload</a></li>
	<li><a href="<s:url namespace="/admin" action="collection/index" />">Collections</a></li>
	<li><a href="<s:url namespace="/admin" action="order/index" />">Orders</a></li>
	<li><a href="<s:url namespace="/admin" action="order-variation" />">Order variations</a></li>
</ul>

<h2>Application</h2>
<ul>
	<li><a href="<s:url namespace="/admin" action="users" />">Users</a></li>
	<li><a href="<s:url namespace="/admin" action="user/roles" />">Roles</a></li>
	<li><a href="<s:url namespace="/admin" action="user/user!input" />">Add user</a></li>
	<li><a href="<s:url namespace="/admin" action="browse" />">Browse files</a></li>
	<li><a href="<s:url namespace="/admin" action="template/index" />">Templates</a></li>
</ul>

<h2>Tools</h2>
<ul>
	<li><a href="<s:url namespace="/admin" action="log/view" />">Log browser</a></li>
	<li><a href="<s:url namespace="/admin" action="log" />">Log configuration</a></li>
	<li><a href="<s:url namespace="/admin" action="schedule/index" />">Scheduled jobs</a></li>
	<li><a href="<s:url namespace="/admin" action="lucene/reindex" />">Lucene reindex</a></li>
	<li><a href="<s:url namespace="/admin" action="schema" />">Schema</a></li>
</ul>

<h2>GeneSys Integration</h2>
<ul>
	<li><a href="<s:url namespace="/admin" action="genesys" />">Dashboard</a></li>
</ul>

<h2>Administrative</h2>
<ul>
	<li><a href="<s:url namespace="/admin" action="applock" />">Block access to application</a></li>
	<li><a href="<s:url namespace="/admin" action="java-status" />">JRE status</a></li>
</ul>

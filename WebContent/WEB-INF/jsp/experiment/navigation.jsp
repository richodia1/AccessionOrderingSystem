<%@ include file="/common/taglibs.jsp"%>
<div class="navigation-tabs clearfloat">
	<a href="<s:url action="experiment/profile" />?id=<s:property value="experiment.id" />">Profile</a>
	<a href="<s:url action="experiment/data" />?id=<s:property value="experiment.id" />">Data</a>
	<a href="<s:url action="experiment/sort" />?id=<s:property value="experiment.id" />">Column sort</a>
</div>
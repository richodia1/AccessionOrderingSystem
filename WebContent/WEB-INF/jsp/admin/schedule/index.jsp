<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Scheduled jobs</title>
</head>
<body>
<h1>Scheduler <s:property value="scheduler.schedulerName" /></h1>
<div><s:property value="scheduler.metaData.summary" /></div>

<p>
<s:if test="scheduler.started">Scheduler is started.</s:if>
<s:if test="scheduler.inStandbyMode">Scheduler is in standby mode.
	<s:form method="post" action="schedule/index!resume"><s:submit value="Resume" /> <s:submit action="schedule/index" value="Refresh" /></s:form>
</s:if>
<s:else>
	<s:form method="post" action="schedule/index!standby"><s:submit value="Pause" /> <s:submit action="schedule/index" value="Refresh" /></s:form>
</s:else>
</p>

<%-- 
<h2>Jobs</h2>
<div>Job groups: <s:property value="scheduler.jobGroupNames" /></div>
<div>Job groups: <s:property value="scheduler.jobNames(null)" /></div>
--%>

<%-- <div>Trigger groups: <s:property value="scheduler.getTriggerGroupNames" /></div> --%>
<s:iterator value="scheduler.getTriggerNames(null)">
	<h2><s:property /></h2>
	<s:set name="trig" value="scheduler.getTrigger(top, null)" />
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<%-- <div><s:property value="#trig" /></div> --%>
		<tr>
			<td>Description:</td>
			<td><s:property value="#trig.description" /></td>
		</tr>
		<%-- 	<div>Group: <s:property value="#trig.group" /></div> --%>
		<tr>
			<td>Last fired:</td>
			<td><s:if test="#trig.previousFireTime==null">Job was not yet fired</s:if><s:else>
				<iita:date format="dd/MM/yyyy HH:mm" name="#trig.previousFireTime" />, <iita:date format="dd/MM/yyyy HH:mm" name="#trig.previousFireTime" nice="true" />
			</s:else></td>
		</tr>
		<tr>
			<td>Next fired:</td>
			<td><iita:date format="dd/MM/yyyy HH:mm" name="#trig.nextFireTime" />, <iita:date format="dd/MM/yyyy HH:mm" name="#trig.nextFireTime" nice="true" /></td>
		</tr>
		<tr>
			<td />
			<td><s:form method="post" action="schedule/index!trigger">
				<s:hidden name="groupName" value="%{#trig.groupName}" />
				<s:hidden name="jobName" value="%{#trig.jobName}" />
				<s:submit value="Trigger job" />
			</s:form></td>
		</tr>
		<%-- <div>Job name:<s:property value="#trig.jobName" /></div> 
	<div><b>Job detail</b>: <s:property value="scheduler.getJobDetail(#trig.jobName, null)" /></div>
	<div><s:property value="#trig.fullName" /></div>
	<div><s:property value="#trig.fullJobName" /></div>
	--%>
	</table>
</s:iterator>

<h2>Currently executing</h2>
<s:if test="scheduler.currentlyExecutingJobs.size==0">
	<p>No jobs are currently running.</p>
</s:if>
<s:else>
	<div>Currently executing jobs:</div>
	<ul><s:iterator value="scheduler.currentlyExecutingJobs">
		<li><s:if test="name==null"><s:property value="top" /></s:if><s:else><s:property value="name" /></s:else><div><s:property value="description" /></div></li>
	</s:iterator></ul>
</s:else>
</body>
</html>
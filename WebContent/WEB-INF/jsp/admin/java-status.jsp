<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title>Java Runtime Environment status</title>
</head>
<body>
<h1>Memory</h1>
<table class="inputform">
	<colgroup>
		<col width="200px" />
		<col width="200px" />
		<col />
	</colgroup>
	<tr>
		<td>Total memory:</td>
		<td><iita:disksize value="totalMemory" /></td>
		<td><s:text name="format.number">
			<s:param name="value" value="totalMemory" />
		</s:text></td>
	</tr>
	<tr>
		<td>Maximum memory:</td>
		<td><iita:disksize value="maxMemory" /></td>
		<td><s:text name="format.number">
			<s:param name="value" value="maxMemory" />
		</s:text></td>
	</tr>
	<tr>
		<td>Free memory:</td>
		<td><iita:disksize value="freeMemory" /></td>
		<td><s:text name="format.number">
			<s:param name="value" value="freeMemory" />
		</s:text></td>
	</tr>
	<tr>
		<td>Used memory:</td>
		<td><iita:disksize value="totalMemory-freeMemory" /></td>
		<td><s:text name="format.number">
			<s:param name="value" value="totalMemory-freeMemory" />
		</s:text></td>
	</tr>
	<tr>
		<td />
		<td><s:form method="get" action="java-status!gc">
			<s:submit value="Run GC" />
		</s:form></td>
	</tr>
</table>

<h2><s:property value="threads.size" /> Threads 2</h2>
<table class="data-listing">
	<colgroup>
		<col width="50px" />
		<col />
		<col width="60px" />
		<col width="60px" />
		<col width="140px" />
		<col width="70px" />
		<col width="150px" />
		<col width="70px" />
		<col width="70px" />
	</colgroup>
	<thead>
		<tr>
			<td>ID</td>
			<td>Name</td>
			<td class="ar">CPU</td>
			<td class="ar">User</td>
			<td>Group</td>
			<td>Prio</td>
			<td>State</td>
			<td>Alive</td>
			<td>Daemon</td>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="threads">
			<tr>
				<td><s:property value="id" /></td>
				<td><s:property value="name" />
				<s:if test="cpuUsage[id].cpuTime>0">
					<iita:collapse id="stackTrace_%{id}" closedHeading="Show stack trace">
						<div style="margin-left: 20px; overflow-x: scroll;"><s:iterator value="cpuUsage[id].threadInfo.stackTrace">
						<code><s:property /></code><br />
						</s:iterator></div>
					</iita:collapse>
				</s:if>
				</td>
				<td class="ar"><s:text name="format.percent">
					<s:param name="value" value="cpuUsage[id].cpuTime / cpuUsage[id].time / 1000000.0 * 100.0" />
				</s:text></td>
				<td class="ar"><s:text name="format.percent">
					<s:param name="value" value="cpuUsage[id].userTime / cpuUsage[id].time / 1000000.0 * 100.0" />
				</s:text>
				<%-- <s:property value="cpuUsage[id].cpuTime" /> <s:property value="cpuUsage[id].time" /> --%>
				</td>
				<td><s:property value="threadGroup.name" /></td>
				<td><s:property value="priority" /></td>
				<td><s:property value="state" /></td>
				<td><s:property value="alive" /></td>
				<td><s:property value="daemon" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</body>
</html>
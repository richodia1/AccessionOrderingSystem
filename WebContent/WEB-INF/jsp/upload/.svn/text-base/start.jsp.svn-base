<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update experiment data</title>
</head>
<body>
<p>Select collection for which you wish to update or build the register:</p>
<s:form action="experiment/upload!upload" method="post" enctype="multipart/form-data">
	<table class="inputform">
		<colgroup>
			<col width="200px" />
			<col />
		</colgroup>
		<tr>
			<td>Experiment:</td>
			<td><s:select id="experimentId" name="experimentId" list="%{experiments}" listKey="id" listValue="title" headerKey="" headerValue="** New experiment" /></td>
		</tr>
		<tr id="newExperiment">
			<td />
			<td>
			<h4 style="margin: 10px 0px 4px 0px;">Define experiment</h4>
			<table class="inputform">
				<colgroup>
					<col width="200px" />
					<col />
				</colgroup>
				<tr>
					<td>Title:</td>
					<td><s:textfield name="newExperiment.title" /></td>
				</tr>
				<tr>
					<td>Authors:</td>
					<td><s:textfield name="newExperiment.authors" /></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><s:textarea cssClass="sizable-textarea" name="newExperiment.description" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>Excel file:</td>
			<td><s:file accept="xls" name="uploads" /></td>
		</tr>
		<tr>
			<td />
			<td><s:submit value="Continue" /></td>
		</tr>
	</table>
</s:form>
<script type="text/javascript">
	Event.observe($("experimentId"), "change", function(ev) {
		if ($("experimentId").value == "")
			$("newExperiment").show();
		else
			$("newExperiment").hide();
		Event.stop(ev);
	});
</script>
</body>
</html>
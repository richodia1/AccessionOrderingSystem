<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accession <s:property value="accession.name" /></title>
<script type="text/javascript" src="<c:url value="/script/builder.js" />"></script>
<script type="text/javascript">window.LightboxOptions={ fileLoadingImage: '<c:url value="/img/loading.gif" />', fileBottomNavCloseImage: '<c:url value="/img/closelabel.gif" />' };</script>
<script type="text/javascript" src="<c:url value="/script/lightbox.js" />"></script>
<link rel="stylesheet" href="<c:url value="/css/lightbox.css" />" type="text/css" media="screen" />
</head>
<body <s:if test="isSelected(accession)">class="accession-selected"</s:if>>
<table>
	<colgroup>
		<col width="300px" />
		<col />
	</colgroup>
	<tr>
	<td>
		<h1><s:property value="accession.name" /></h1>
		<div class="unselect-tool">
		<div class="actionMessage">Accession <s:property value="accession.name" /> is on <a href="<s:url action="selection" />">your selection list</a>.</div>
		<s:form method="get" action="selection!remove">
			<s:hidden name="accessionId" value="%{accession.id}" />
			<s:submit id="btn_unselect" value="Remove from list" />
		</s:form></div>
		<div class="select-tool">
		<div class="actionMessage">Accession <s:property value="accession.name" /> is not on <a href="<s:url action="selection" />">your selection list</a>.</div>
		<s:form method="get" action="selection!add">
			<s:hidden name="accessionId" value="%{accession.id}" />
			<s:submit id="btn_select" value="Add to list" />
		</s:form></div>

		<s:if test="accession.longitude!=null && accession.latitude!=null">
<%--			<h3>Location</h3>
			<img src="http://genebank.iita.org/gis/map.aspx?x=<s:property value="accession.longitude*1000000" />&y=<s:property value="accession.latitude*1000000" />" />
 --%>
 		</s:if>
		
		<!-- Images -->
		<s:if test="images.size>0">
		<h2>Accession images</h2>
			<s:iterator value="images">
				<s:property value="fileName" />:
				<div class="accessionImage"><a href="<c:url value="/img/accessions/" /><s:property value="accession.collection.shortName.toLowerCase()" />/<s:property value="accession.name" />/<s:property value="fileName" />" rel="lightbox[accession]"><img title="Accession <s:property value="accession.name" /> image" src="<c:url value="/img/accessions/" /><s:property value="accession.collection.shortName.toLowerCase()" />/<s:property value="accession.name" />/<s:property value="fileName" />" /></a></div>
			</s:iterator>
		</s:if>
		</td>
		<!--  THE Descriptors -->
		<td style="padding-left: 30px;">
		<h1>Passport data <iita:authorize ifAnyGranted="ROLE_ADMIN"><small><a href="<s:url action="edit" />?id=<s:property value="accession.id" />&experimentId=1">Edit</a></small></iita:authorize></h1>
		<table class="inputform accession-profile">
			<colgroup>
				<col width="250px" />
				<col width="300px" />
				<col />
			</colgroup>
			<tr>
				<td>Collection:</td>
				<td><b><a href="<s:url action="collection" />?id=<s:property value="accession.collection.id" />" title="View details of <s:property value="accession.collection.name" />"><s:property value="accession.collection.name" /></a></b></td>
				<td><em>Collection</em></td>
			</tr>
			<s:if test="accession.otherNames && accession.otherNames.size gt 0">
				<tr>
					<td>Other names:</td>
					<td><s:iterator value="accession.otherNames">
						<s:property value="name" />, <s:property value="usedBy" />
					</s:iterator></td>
				</tr>
			</s:if>
			<s:set name="mcpdValue" value="getMCPDData()" />
			<s:iterator value="mcpd">
				<s:if test="top.visible && #mcpdValue[column]!=null">
					<s:if test="title=='Accession name'">
						<tr>
							<td><s:property value="title" />:</td>
							<td class="identifying"><s:push value="top">
								<s:push value="#mcpdValue[column]">
									<s:include value="acce_descriptor.jsp" />
								</s:push>
							</s:push><!--<s:property value="#mcpdValue[column]" />--></td>
							<td><em><s:property value="description" /></em></td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<td><s:property value="title" />:</td>
							<td class="identifying"><s:push value="top">
								<s:push value="#mcpdValue[column]">
									<s:include value="descriptor.jsp" />
								</s:push>
							</s:push><!--<s:property value="#mcpdValue[column]" />--></td>
							<td><em><s:property value="description" /></em></td>
						</tr>
					</s:else>
				</s:if>
			</s:iterator>
		</table>
		
		<!-- Experiment data --> <s:iterator value="accession.experiments">
		<s:if test="visible || user.hasRole('ROLE_USER')">
			<h2><s:property value="title" /> <iita:authorize ifAnyGranted="ROLE_ADMIN"><small><a href="<s:url action="edit" />?id=<s:property value="accession.id" />&experimentId=<s:property value="id" />">Edit</a></small></iita:authorize></h2>
			<s:if test="authors!=null"><p><b>Authors:</b> <s:property value="authors" /></p></s:if>
			<s:if test="description!=null"><p><s:property value="description" /></p></s:if>
			
			<s:set name="experimentData" value="getExperimentData(top)" /> 

			<table class="inputform accession-profile" style="margin-top: 10px">
				<colgroup>
					<col width="250px" />
					<col width="300px" />
					<col />
				</colgroup>
				<s:iterator value="columns">
					<s:if test="(visible || user.hasRole('ROLE_USER')) && #experimentData[column]!=null">
						<tr class="<s:if test="!visible">private-column</s:if>">
							<td><s:property value="title" />:</td>
							<td class="identifying">
								<s:push value="top">
									<s:push value="#experimentData[column]">
										<s:include value="descriptor.jsp" />
									</s:push>
								</s:push><!--<s:property value="accession[column]" />--></td>
							<td><em><s:property value="description" /></em></td>
						</tr>						
					</s:if>
				</s:iterator>
			</table>
			</s:if>
		</s:iterator></td>
	</tr>
</table>
<script type="text/javascript">
IITA.Accession = Class.create();
IITA.Accession.AjaxService=new IITA.AjaxRPC("<s:url action="service" namespace="/ajax" />");
Event.observe($("btn_select"), "click", function(ev) {
	IITA.Accession.AjaxService.addToSelection(<s:property value="accession.id" />, function(x) { 
		if (x.responseJSON.result==true) document.body.addClassName("accession-selected");
	});
	Event.stop(ev);
});
Event.observe($("btn_unselect"), "click", function(ev) {
	IITA.Accession.AjaxService.removeFromSelection(<s:property value="accession.id" />, function(x) { 
		if (x.responseJSON.result==true) document.body.removeClassName("accession-selected");
	});
	Event.stop(ev);
});
</script>
</body>
</html>
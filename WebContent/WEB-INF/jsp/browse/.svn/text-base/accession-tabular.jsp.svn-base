<%@ include file="/common/taglibs.jsp"%>
<table class="data-listing">
	<colgroup>
		<col width="150px" />
		<col width="140px" />
		<col />
		<s:iterator value="#accessionsUtil.getExtraColumns()">
			<col />
		</s:iterator>
	</colgroup>
	<thead>
		<tr>
			<td>Name</td>
			<td>Selection</td>
			<td>Taxa</td>
			<s:iterator value="#accessionsUtil.getExtraColumns()">
				<td><s:property value="title" /></td>
			</s:iterator>
		</tr>
	</thead>
	<tbody id="accession-tabular">
		<s:iterator value="top">
			<tr class="<s:if test="[1].isSelected(top[0])">accession-selected</s:if>">
				<td><a href="<c:url value="/accession" />/<s:property value="top[1]" />"><s:property value="top[1]" /></a></td>
				<td>
				<div class="select-tool"><a x:id="<s:property value="top[0]" />" class="btn_select" href="#">Add to list</a></div>
				<div class="unselect-tool"><a x:id="<s:property value="top[0]" />" class="btn_unselect" href="#">Remove from list</a></div>
				</td>
				<td><em><s:property value="top[2]" /> <s:property value="top[3]" /></em></td>
				<s:iterator value="#accessionsUtil.getExtraColumns()" status="status">
					<td><s:if test="coded">
					<s:property value="decode([1].top[4 + #status.index])" />
					</s:if><s:else>
					<s:property value="[1].top[4 + #status.index]" /></s:else>
					</td>
				</s:iterator>
			</tr>
		</s:iterator>
	</tbody>
	<tfoot>
		<tr>
			<td />
			<td><a href="" onclick="javascript: IITA.Accession.addAll(); return false;">Add all</a></td>
			<td /><s:iterator value="#accessionsUtil.getExtraColumns()"><td /></s:iterator>
		</tr>
	</tfoot>
</table>

<script type="text/javascript">
IITA.Accession = Class.create();
IITA.Accession.AjaxService=new IITA.AjaxRPC("<s:url action="service" namespace="/ajax" />");
IITA.Accession.addToSelection = function(id, element) {
	var el=element;
	IITA.Accession.AjaxService.addToSelection(parseInt(id), function(x) { 
		if (x.responseJSON.result==true) el.addClassName("accession-selected");
	});
};
IITA.Accession.removeFromSelection = function(id, element) {
	var el=element;
	IITA.Accession.AjaxService.removeFromSelection(parseInt(id), function(x) { 
		if (x.responseJSON.result==true) el.removeClassName("accession-selected");
	});
};
IITA.Accession.addAll = function() {
	IITA.Accession.AjaxService.addManyToSelection([<s:iterator value="top" status="status"><s:property value="top[0]" /><s:if test="!#status.last">,</s:if></s:iterator>], function(x) { 
		if (x.responseJSON.result==true) {
			var rows=$("accession-tabular").children;
			for (var i=rows.length-1; i>=0; i--)
				rows[i].addClassName("accession-selected");
		}
	});
}
$A(document.getElementsByClassName("btn_select")).each(
		function(value, index) { 
			var ic=value; 
			Event.observe(ic, "click", function(ev) {
//				alert("add: " + ic + "\n" + ic.parentNode.parentNode);
				IITA.Accession.addToSelection(ic.getAttribute("x:id"), ic.parentNode.parentNode.parentNode);
				Event.stop(ev);
			});});

$A(document.getElementsByClassName("btn_unselect")).each(
		function(value, index) { 
			var ic=value; 
			Event.observe(ic, "click", function(ev) {
//				alert("remove: " + ic + "\n" + ic.parentNode.parentNode);
				IITA.Accession.removeFromSelection(ic.getAttribute("x:id"), ic.parentNode.parentNode.parentNode);
				Event.stop(ev);
			});});
</script>

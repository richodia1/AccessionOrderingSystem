<%@ include file="/common/taglibs.jsp"%>
<table class="data-listing">
	<colgroup>
		<col width="150px" />
		<col width="140px" />
		<col width="240px" />
		<col width="200px" />
		<col />
	</colgroup>
	<thead>
		<tr>
			<td>Name</td>
			<td>Collection</td>
			<td>Taxa</td>
			<td>Origin</td>
			<td>Total</td>
		</tr>
	</thead>
	<tbody id="accession-tabular">
		<s:iterator value="top">
			<tr>
				<td><a href="<c:url value="/accession" />/<s:property value="name" />"><s:property value="name" /></a></td>
				<td>
				<td><s:property value="collection.name" /></td>
				<td><em><s:property value="genus" /> <s:property value="species" /></em></td>
				<td><s:if test="origCty!=null"><s:property value="getMcpd('origCty').decode(origCty)" /></s:if></td>
				<td><em><s:property value="total" /></em></td>
			</tr>
		</s:iterator>
	</tbody>
	<tfoot>
		<tr>
			<td />
			<td><a href="" onclick="javascript: IITA.Accession.addAll(); return false;">Add all</a></td>
			<td /><td /><td />
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
	IITA.Accession.AjaxService.addManyToSelection([<s:iterator value="top" status="status"><s:property value="id" /><s:if test="!#status.last">,</s:if></s:iterator>], function(x) { 
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
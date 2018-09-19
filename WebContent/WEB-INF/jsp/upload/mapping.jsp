<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Map XLS columns</title>
<style>
.importColumn,.destinationColumn {
	margin: 4px 0px;
	padding: 2px 5px;
}

.selectedColumn {
	border: solid 1px black;
}

.destinationColumn .importColumn {
	color: #DD7025;
	margin-left: 10px;
	/*padding-left: 22px; background-image : url("<c:url value="/" />img/importhandle.png");*/
	background-repeat: no-repeat;
	background-position: top left;
	padding-left: 22px;
}

.destinationColumn .importColumn .extraData {
	display: none;
}

.nomoreColumn {
	color: Gray;
}
</style>
</head>
<body>
<h2><s:property value="mappings.xlsColumns.size" /> columns to map</h2>
<table style="width: 100%">
	<colgroup>
		<col width="60%" />
		<col width="40%" />
	</colgroup>
	<tr>
		<td><s:form action="experiment/upload!configure" method="POST">
		<s:hidden name="experimentId" value="%{experiment.id}" />
			<div style="height: 500px; overflow: auto;">
			<s:iterator status="status" value="mappings.xlsColumns">
			<div id="src${columnNum}" class="destinationColumn"><b><s:property value="name" /></b> <s:property value="dataType" />
			<div style="padding-left: 20px;" class="extraData"><small><s:property value="sampleData" escape="false" /></small></div>
			<s:hidden name="" value="%{columnNum}" /></div>
		</s:iterator>
		</div>
			<p style="margin-top: 20px;"><s:submit value="Continue" /> <input type="button" value="Map all unmapped" onclick="javascript: mapUnmapped(); return false;" />
			<input type="button" value="Clear mapping" onclick="javascript: manager.clearMapping(); return false;" />
			<s:submit value="Default mapping" action="experiment/upload!defaultmapping">
				<s:param name="experimentId" value="%{experiment.id}" />
			</s:submit>
			</p>
		</s:form></td>
		<td>
		<div style="height: 500px; overflow: auto;">
				<%--<div id="coreprefix" class="importColumn coreelement" style="margin-bottom: 10px;"><b>PREFIX <small>Optional</small></b><br />
		Accession name. <s:hidden name="colMap" value="name" /></div>
		<div id="corenumber" class="importColumn coreelement" style="margin-bottom: 10px;"><b>NUMBER <small>Optional</small></b><br />
		Numeric accession number. <s:hidden name="colMap" value="number" /></div>
		<div id="corefullname" class="importColumn coreelement" style="margin-bottom: 10px;"><b>FULL NAME <small style="color: Red;">Required!</small></b><br />
		Full accession name. <s:hidden name="colMap" value="fullname" /></div>--%>
		<s:iterator status="status" value="columns">
			<div id="dest${id}" class="importColumn" style="margin-bottom: 10px;">
				<b><s:property value="title" /></b> <s:property value="dataType" /> <input type="hidden" name="colMap" value="${id}" />
				<div style="padding-left: 20px;" class="extraData"><small><s:property value="description" /></small></div>
				<s:if test="coded"><div style="padding-left: 20px;" class="extraData"><small>
				<s:iterator value="coding">
				<s:property value="actualValue" /> </s:iterator></small></div></s:if>
			</div>
			</s:iterator>
			<div id="dest-1" class="importColumn coreelement" style="margin-bottom: 10px;">
				<b>New column</b> <input type="hidden" name="colMap" value="-1" />
				<div style="padding-left: 20px;" class="extraData"><small>Add a new column to existing table</small></div>
			</div>
		</div>		
		</td>
	</tr>
</table>

<script type="text/javascript">
	var manager = {
		selectDestination : function(dest) {
			if (dest.isUsed && !dest.hasClassName("nolimits"))
				return;

			if (this.destination && this.destination != dest) {
				this.destination.amSelected = false;
				this.destination.removeClassName("selectedColumn");
			}
			this.destination = null;

			!dest.amSelected ? dest.amSelected = true : dest.amSelected = false;
			if (dest.amSelected) {
				this.destination = $(dest);
				dest.addClassName("selectedColumn");
			} else
				dest.removeClassName("selectedColumn");

			if (this.source != null && this.destination != null
					&& confirm("Add mapping?"))
				this.addMapping();
		},
		selectSource : function(source) {
			if (this.source && this.source != source) {
				this.source.amSelected = false;
				this.source.removeClassName("selectedColumn");
			}
			this.source = null;
			!source.amSelected ? source.amSelected = true
					: source.amSelected = false;
			if (source.amSelected) {
				this.source = $(source);
				source.addClassName("selectedColumn");
			} else
				source.removeClassName("selectedColumn");

			if (this.source != null && this.destination != null
					&& confirm("Add mapping?"))
				this.addMapping();
		},
		addMapping : function() {
			if (this.source && this.destination) {
				this.source.removeClassName("selectedColumn");
				this.destination.removeClassName("selectedColumn");

				var hiddenDest = $(this.destination).getElementsBySelector('[type="hidden"]')[0];
				// clone and rename "name" attribute
				var hiddenDest2 = Element.clone(hiddenDest, false);
				hiddenDest2.name = "xlsMap";

				var copy = Element.clone(this.source, true);
				copy.id=null;
				copy.sourceNode = this.source;
				copy.appendChild(hiddenDest2);

				this.destination.isUsed = true;
				this.destination.appendChild(copy);
				this.destination.addClassName("nomoreColumn");
				Event.observe(copy, "dblclick", this.unmap);

				if (!this.source.hasClassName("coreelement")) {
					Element.setStyle(this.source, {
						display :"none"
					});
				}
				this.source = this.destination = null;
			}
		},
		map: function(source, dest) {
			this.source=source; 
			this.destination=dest;
			this.addMapping();
		},
		/** element - a TR element to be unmapped */
		unmap : function(event) {
			manager._unmap(this);
		},
		_unmap: function(src) {
			var dest = src.parentNode;
			dest.removeChild(src);
			dest.isUsed = false;
			if (!dest.hasClassName("coreelement")) {
				Element.setStyle(src.sourceNode, {
					display :"block"
				});
			}
			dest.removeClassName("nomoreColumn");
			Event.stopObserving(src, "dblclick");			
		},
		/** clearMapping removes all current mappings */
		clearMapping: function() {
			$$(".destinationColumn").each(
					function(dest, index) {
						dest=$(dest);
						x=$A(dest.getElementsBySelector(".importColumn"));
						x.each(function(el) { manager._unmap(el); });
					}
				);
		}
	};

	Event.observe(window, 'load', function() {					
		$$(".importColumn").each(
				function(value, index) { 
					var ic=value; 
					Event.observe(ic, "click", function() { manager.selectSource(ic); });
				}
			);
		$$(".destinationColumn").each(
				function(value, index) {
					var ic=value; 
					Event.observe(ic, "click", function() { manager.selectDestination(ic); });
				}
			);
		// need to generate calls for mapping!		
		<s:iterator value="mappings.mappings.keySet()">
		manager.map($("dest<s:property value="mappings.mappings[top]" />"), $("src<s:property />"));
		</s:iterator>
	});
function mapUnmapped() {
	// map all unmapped columns
	<s:iterator value="mappings.xlsColumns">
		<s:if test="mappings.mappings[columnNum]==null">
		manager.map($("dest-1"), $("src<s:property value="columnNum" />"));
		</s:if>
	</s:iterator>
}
</script>
</body>
</html>
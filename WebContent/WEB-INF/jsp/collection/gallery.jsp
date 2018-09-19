<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Collection images: <s:property value="collection.name" /></title>
<script type="text/javascript" src="<c:url value="/script/builder.js" />"></script>
<script type="text/javascript">window.LightboxOptions={ fileLoadingImage: '<c:url value="/img/loading.gif" />', fileBottomNavCloseImage: '<c:url value="/img/closelabel.gif" />' };</script>
<script type="text/javascript" src="<c:url value="/script/lightbox.js" />"></script>
<link rel="stylesheet" href="<c:url value="/css/lightbox.css" />" type="text/css" media="screen" />
</head>
<body>
<h1><s:property value="collection.name" /> images</h1>

<s:if test="images!=null">
<s:push value="images">
	<s:include value="/WEB-INF/jsp/paging.jsp">
		<s:param name="href" value="%{'collection=' + collection.id}" />
	</s:include>
</s:push>

<s:set name="accessionCount" value="0" />
<s:set name="imageCount" value="0" />
<s:iterator value="images.results">
	<s:if test="file.parentFile.name!=#currParent">
		<s:set name="accessionCount" value="%{#accessionCount+1}" />
		<s:if test="#accessionCount > 1">
			(<s:property value="#imageCount" /> images)
			</div>
		</s:if>
		<div class="accessionImage" style="float: left; margin: 10px">
		<s:set name="currParent" value="%{file.parentFile.name}" />
		<s:set name="imageCount" value="1" />
		<a href="<c:url value="/img/accessions/" /><s:property value="collection.shortName" />/<s:property value="#currParent" />/<s:property value="fileName" />" rel="lightbox[<s:property value="#currParent" />]"><img src="<c:url value="/img/accessions/" /><s:property value="collection.shortName" />/<s:property value="#currParent" />/<s:property value="fileName" />" /></a>
		<b><a href="<c:url value="/accession" />/<s:property value="#currParent" />"><s:property value="#currParent" /></a></b>
	</s:if>
	<s:else>
		<s:set name="imageCount" value="%{#imageCount + 1}" />
		<a href="<c:url value="/img/accessions/" /><s:property value="collection.shortName" />/<s:property value="#currParent" />/<s:property value="fileName" />" rel="lightbox[<s:property value="#currParent" />]"></a>
	</s:else>	
</s:iterator>
	(<s:property value="#imageCount" /> images)
	</div>

</s:if>
<s:else>
	<p>No images found.</p>
</s:else>
</body>
</html>
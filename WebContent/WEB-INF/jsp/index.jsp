<%@ page session="false" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Welcome</title>
</head>
<body>
<div style="float:right;"><h3><a href="/accession2/files/Users_guide4_online_ordering_of_Germplasm.docx" target="_blank">User Guide</a></h3></div>
<s:iterator value="collections">
<div style="width: 380px; float: left; margin: 5px 10px 5px 0; padding: 5px; <s:if test="!visible">background-color: #efefef;</s:if>">
	<h3><a href="<s:url action="collection" />?id=<s:property value="id" />"><s:property value="name" /></a></h3>
	<div style="float: left; width: 100%;"><p><img style="float:left; margin:0 10px 10px 0;" src="<c:url value="/img/p/${shortName}.gif" />" /><s:property value="description" /></p>
	<p>Number of accessions: <b><s:property value="getCollectionSize(top)" /></b></p> 
	</div>
</div>
</s:iterator>

<div style="clear: both; margin-top: 30px; padding-top: 30px;">
<h3>Copyright and fair use</h3>

<p>The International Institute of Tropical Agriculture (IITA) holds the copyright to its publications and Web pages but encourages duplication of these materials for noncommercial purposes. Proper citation is requested and prohibits modification of these materials. Permission to make digital or hard copies of part or all of this work for personal or classroom use is hereby granted without fee and without a formal request provided that copies are not made or distributed for profit or commercial advantage and that copies bear this notice and full citation on the first page. Copyright for components not owned by IITA must be honored and permission pursued with the owner of the information. To copy otherwise, to republish, to post on servers, or to redistribute to lists, requires prior specific permission. Request permission to publish by contacting IITA.</p>
<h4>Disclaimer</h4>

<p>There is always a risk attached to using or downloading from the internet. Please ensure that anything you download from our site is virus checked by up to date virus software. We do not exercise editorial control on the content of our site other than on official corporate documents. We do not endorse, approve or give any warranty on any other site (or the content of such site) to which we offer links. All liability for loss and damage arising from your use of our site is excluded (except where death or personal injury arises from our negligence or loss or damage arises from any fraud on our part). The term "use of our site" includes (but is not limited to):</p>
<ul>
<li>Reliance on information contained on our site</li>
<li>Downloading materials</li>
<li>Using links from one part of our site to another or to other sites on the internet.</li>
</ul>
</div>
</body>
</html>
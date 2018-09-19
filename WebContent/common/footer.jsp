<%@ include file="/common/taglibs.jsp"%>

<div id="versioned">IITA Accession - ver. 2&nbsp;&nbsp;</div>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-66282931-1', 'auto');
  ga('send', 'pageview');

</script>

<s:action name="user/notification-quick" namespace="/" executeResult="true" ignoreContextParams="true" />

<%-- Render footer notificaiton messages, usually success messages --%>
<s:if test="notificationMessages!=null && notificationMessages.size>0">
	<div id="notificationMessages"><s:iterator value="notificationMessages">
		<div class="notificationMessage"><s:property escape="false" /></div>
	</s:iterator></div>
</s:if>

<div id="ajax-indicator" style="display: none;">
	<b>Please wait...</b> <img src="<c:url value="/img/ajax-ind-1.gif" />" />
</div>
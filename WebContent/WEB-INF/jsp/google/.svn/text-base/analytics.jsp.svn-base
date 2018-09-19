<%@ include file="/common/taglibs.jsp"%>
<s:if test="trackers.size>0">
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
<s:iterator value="trackers">try { var pageTracker = _gat._getTracker("<s:property />"); pageTracker._trackPageview(); } catch(err) {}
</s:iterator>
</script>
</s:if>
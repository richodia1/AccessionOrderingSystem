<%@ include file="/common/taglibs.jsp"%>

<div id="sessionAlert" class="noprint">
<div class="warning">Warning! Your session may expire in <span class="timer">00:00</span>. Trying to automatically extend your session.</div>
<div class="expired">Could not extend your session. Save (copy+paste somewhere) any important data before clicking any links on this page.</div>
</div>

<script type="text/javascript">
Event.observe(window, 'load', function() {
	sessionKeeper=new IITA.SessionTimeout($("sessionAlert"), ${pageContext.session.maxInactiveInterval}, 90, "<c:url value="/" />");
//	sessionKeeper=new IITA.SessionTimeout($("sessionAlert"), 10, 4, "<c:url value="/" />");
});
</script>

<%@ include file="/common/taglibs.jsp"%>
<s:if test="unreadCount>0">
<div style="position: fixed; bottom: 0px; right: 0px; width: 200px; background-color: Yellow; border: solid 1px black; padding: 4px 6px; margin: 3px;">
	You have <s:property value="unreadCount" /> unread message(s). 
	Read <a href="<s:url action="user/notification" namespace="/" />">them now</a>.
</div>
</s:if>
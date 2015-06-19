<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<li class="first"><a
		href="${pageContext.request.contextPath}/admin"><spring:message
				code="admin.title.short" /></a></li>

	<li
		<c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/oauth2/manage.form"><spring:message
				code="oauth2.manage" /></a>
	</li>
	
	<!-- Add further links here -->
    <li
    <c:if test='<%= request.getRequestURI().contains("/registration") %>'>class="active"</c:if>>
    <a
            href="${pageContext.request.contextPath}/module/oauth2/registration.form"><spring:message
            code="oauth2.register"/></a>
    </li>
    <li
    <c:if test='<%= request.getRequestURI().contains("/application") %>'>class="active"</c:if>>
    <a
            href="${pageContext.request.contextPath}/module/oauth2/application.list"><spring:message
            code="oauth2.manage.registered"/></a>
    </li>
    <li
    <c:if test='<%= request.getRequestURI().contains("/authorized") %>'>class="active"</c:if>>
    <a
            href="${pageContext.request.contextPath}/module/oauth2/authorized.list"><spring:message
            code="oauth2.manage.authorized"/></a>
    </li>
</ul>
<h2>
	<spring:message code="oauth2.title" />
</h2>

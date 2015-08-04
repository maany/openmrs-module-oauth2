<%--<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>--%>
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>

<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>


<% if (session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY) != null && !(session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY) instanceof UnapprovedClientAuthenticationException)) { %>
<%--error--%>
<p>Access could not be granted</p>

<p><%= ((AuthenticationException) session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
</p>

<div class="error">
    <h2>Woops!</h2>

    <p>Access could not be granted.
        (<%= ((AuthenticationException) session.getAttribute(AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
        )</p>
</div>
<% } %>
<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
<%--<authz:authorize access="hasRole('ROLE_USER')">--%>
<form id="confirmationForm" name="confirmationForm" action="<%=request.getContextPath()%>/ws/oauth/authorize"
      method="post">
    <input name="user_oauth_approval" value="true" type="hidden"/>
    <label><input name="authorize" value="Authorize" type="submit"></label>
</form>
<form id="denialForm" name="denialForm" action="<%=request.getContextPath()%>/ws/oauth/authorize" method="post">
    <input name="user_oauth_approval" value="false" type="hidden"/>
    <label><input name="deny" value="Deny" type="submit"></label>
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>


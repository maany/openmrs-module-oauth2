<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>

<p>
    This is the Index page. </p>

<p>
    <a href="${pageContext.request.contextPath}/module/oauth2/client/registrationLink.form"><openmrs:message
            code="oauth2.client.register"/></a><br/><br/>
</p>

<p>It will display a list of registered clients for the current authenticated user here.</p>

<%@ include file="/WEB-INF/template/footer.jsp" %>
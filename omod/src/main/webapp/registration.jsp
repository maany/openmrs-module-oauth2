<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>

<p>Application Registration</p>

<form:form modelAttribute="client" action="register" method="post">
    Application Name <form:input path="name"/><br/>
    Application Desciption <form:input path="description"/><br/>
    Application Link <form:input path="website"/><br/>
    Redirection URI <form:input path="redirectionURI"/><br/>
    Client Type <br/>
    <ul>
        <li><form:radiobutton path="clientType" value="WEB_APPLICATION"/>Web Application</li>
        <li><form:radiobutton path="clientType" value="NATIVE_APPLICATION"/>Mobile Application</li>
        <li><form:radiobutton path="clientType" value="USER_AGENT_BASED_APPLICATION"/>User Agent Based Application</li>
    </ul>
    <input type="submit"/>
</form:form>

<%@ include file="/WEB-INF/template/footer.jsp" %>
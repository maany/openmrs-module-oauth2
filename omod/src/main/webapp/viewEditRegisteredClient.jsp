<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>

<h2><openmrs:message code="oauth2.client.registered.viewEdit"/></h2>
<form:form modelAttribute="client" method="post">
    <form:hidden path="clientId"/>
    <table>
        <tr>
            <td>Application Name</td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td>Application Description</td>
            <td><form:input path="description"/></td>
        </tr>
        <tr>
            <td>Website</td>
            <td><form:input path="website"/></td>
        </tr>
        <tr>
            <td>Redirection URI</td>
            <td><form:input path="redirectionURI"/></td>
        </tr>
        <tr>
            <td>Client Type</td>
            <td>
                <form:select path="clientType">
                    <form:option value="-" label="--Please Select"/>
                    <form:options items="${clientTypes}"/>
                </form:select>
            </td>
        </tr>
        <tr>
            <td>Client Identifier</td>
            <td>TODO</td>
        </tr>
        <tr>
            <td>Client Secret</td>
            <td>TODO</td>
        </tr>
        <tr>
    </table>
    <input type="submit" value="Save Changes"/>
</form:form>

<%@ include file="/WEB-INF/template/footer.jsp" %>
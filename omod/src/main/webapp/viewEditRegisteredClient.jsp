<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>
<openmrs:htmlInclude file="/moduleResources/oauth2/jquery-1.11.3.min.js"/>
<script>
    $(document).ready(function () {
        $("button").click(function () {
            var pageUrl = window.location.href;
            alert("Are you sure you want to unregister this application??");
            $.ajax({
                url: pageUrl,
                type: 'DELETE'
            });
        });
    });
</script>
<h2><openmrs:message code="oauth2.client.registered.viewEdit"/></h2>
<form:form modelAttribute="client">
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
            <td>${app_identifier}</td>
        </tr>
        <tr>
            <td>Client Secret</td>
            <td>${app_password}</td>
        </tr>
        <tr>
    </table>
    <input type="submit" value="Save Changes" formmethod="post"/>
    <button>Delete</button>
</form:form>
<%@ include file="/WEB-INF/template/footer.jsp" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>
<openmrs:htmlInclude file="/moduleResources/oauth2/jquery-1.11.3.min.js"/>
<style>
    .error {
        color: #ff0000;
    }

    .errorblock {
        color: #000;
        background-color: #ffEEEE;
        border: 3px solid #ff0000;
        padding: 8px;
        margin: 16px;
    }
</style>
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
    <form:errors path="*" cssClass="errorblock" element="div"/>
    <form:hidden path="clientId"/>
    <table>
        <tr>
            <td>Application Name</td>
            <td><form:input path="name"/></td>
            <td><form:errors path="name" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Application Description</td>
            <td><form:input path="description"/></td>
            <td><form:errors path="description" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Website</td>
            <td><form:input path="website"/></td>
            <td><form:errors path="website" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Redirection URI</td>
            <td><form:input path="redirectionURI"/></td>
            <td><form:errors path="redirectionURI" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Client Type</td>
            <td>
                <form:select path="clientType">
                    <form:option value="-" label="--Please Select"/>
                    <form:options items="${clientTypes}"/>
                </form:select>
            <td><form:errors path="clientType" cssClass="error"/></td>
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
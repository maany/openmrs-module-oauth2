<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>
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
<h2><openmrs:message code="oauth2.client.registered.register"/></h2>

<form:form modelAttribute="client" method="post">
    <form:errors path="*" cssClass="errorblock" element="div"/>
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
            <td><form:input path="registeredRedirectUris"/></td>
            <td><form:errors path="registeredRedirectUris" cssClass="error"/></td>
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
        <%--        <tr>
                    &lt;%&ndash;todo scope : read write checkbox&ndash;%&gt;
                    <td>Scope</td>
                    <td>
                        <form:checkbox path="${scope}" value="read"/>
                        <form:checkbox path="${scope}" value="write"/>
                    </td>
                    <td><form:errors path="Scopes" cssClass="error"/></td>
                    </td>
                </tr>
                <tr>
                    &lt;%&ndash;todo authorized grant types : read write checkbox&ndash;%&gt;
                        &lt;%&ndash;todo scope : read write checkbox&ndash;%&gt;
                        <td>Authorized Grant Types</td>
                        <td>
                            <form:checkbox path="${authorizedGrantTypes}" value="Authorization Code"/>
                            <form:checkbox path="${authorizedGrantTypes}" value="Implicit"/>
                            <form:checkbox path="${authorizedGrantTypes}" value="Resource Owner Password Credentials"/>
                            <form:checkbox path="${authorizedGrantTypes}" value="Client Credentials"/>
                            <form:checkbox path="${authorizedGrantTypes}" value="Refresh Token"/>
                        </td>
                        <td><form:errors path="Grant Types" cssClass="error"/></td>
                        </td>
                </tr>--%>
        <tr>
    </table>

    <input type="submit" value="Register Application"/>
</form:form>
<%@ include file="/WEB-INF/template/footer.jsp" %>
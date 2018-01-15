<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>
<openmrs:htmlInclude file="/moduleResources/oauth2/js/util.js"/>

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
<form method="post">
    <table>
        <tr>
            <td>Application name</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>Application Description</td>
            <td><input type="text" name="description"></td>
        </tr>
        <tr>
            <td>Website</td>
            <td><input type="text" name="website"></td>
        </tr>
        <tr>
            <td>Redirection URI</td>
            <td><input type="text" name="registeredRedirectURIs"></td>
        </tr>
        <tr>
            <td>Client Type</td>
            <td>
                <select name="clientType">
                    <c:forEach  items="${clientTypes}" var="clientType">
                        <option value="${clientType}">${clientType}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Scope</td>
            <c:forEach var="scope" items="${scopes}">
                <td><input type="checkbox" name="scope" value="${scope}">${scope}</td>
            </c:forEach>
        </tr>
        <tr>
            <c:forEach var="grantType" items="${grantTypes}">
                <td><input type="checkbox" name="grantType" value="${grantType}">${grantType}</td>
            </c:forEach>
        </tr>
    </table>
    <input type="submit" value="Register Application"/>
</form>
<%--

<form:form modelAttribute="client" method="post"
           onsubmit="processOutGoingData(this,['grantTypeCollection','scopeCollection'])">
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
            <td><form:input path="redirectUriCollection"/></td>
            <td><form:errors path="redirectUriCollection" cssClass="error"/></td>
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
            &lt;%&ndash;todo scope : read write checkbox&ndash;%&gt;
            <td>Scope</td>
            <td>
                <form:checkbox path="scopeCollection" value="read"/>Read
                <form:checkbox path="scopeCollection" value="write"/>Write
            </td>
            <td><form:errors path="scopeCollection" cssClass="error"/></td>
            </td>
        </tr>
        <tr>
            &lt;%&ndash;todo authorized grant types : read write checkbox&ndash;%&gt;
            &lt;%&ndash;todo scope : read write checkbox&ndash;%&gt;
            <td>Authorized Grant Types</td>
            <td>
                <form:checkbox path="grantTypeCollection" value="authorization_code"/>Authorization Code<br>
                <form:checkbox path="grantTypeCollection" value="implicit"/>Implicit<br>
                <form:checkbox path="grantTypeCollection" value="password"/> Resource Owner Password Credentials<br>
                <form:checkbox path="grantTypeCollection" value="client_credentials"/>Client Credentials<br>
                <form:checkbox path="grantTypeCollection" value="refresh_token"/>Refresh Token<br>
            </td>
            <td><form:errors path="grantTypeCollection" cssClass="error"/></td>
            </td>
        </tr>
    </table>

    <input type="submit" value="Register Application"/>
</form:form>
--%>

<%@ include file="/WEB-INF/template/footer.jsp" %>
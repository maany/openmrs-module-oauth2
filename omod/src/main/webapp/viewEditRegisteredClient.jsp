<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>
<openmrs:htmlInclude file="/moduleResources/oauth2/js/util.js"/>
<script>
    window.onload = function () {
        selectCheckBoxes(scopeCollection, ${client.scopeCollection});
        selectCheckBoxes(grantTypeCollection, ${client.grantTypeCollection});
    };
</script>
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
        $("")
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
<form method="post">
    <c:set var="client" value="${client}"/>
    <table>
        <tr>
            <td>Application name</td>
            <td><input type="text" name="name" value="${client.name}"></td>
        </tr>
        <tr>
            <td>Application Description</td>
            <td><input type="text" name="description" value="${client.description}"></td>
        </tr>
        <tr>
            <td>Website</td>
            <td><input type="text" name="website" value="${client.website}"></td>
        </tr>
        <tr>
            <td>Redirection URI</td>
            <td><input type="text" name="registeredRedirectURIs" value="${client.registeredRedirectUri.iterator.next}"></td>
        </tr>
        <tr>
            <td>Client Type</td>
            <td>
                <select name="clientType">
                    <c:forEach items="${clientTypes}" var="clientType">
                        <c:if test="${clientType==client.clientType}">
                            <option value="${clientType}" selected>${clientType}</option>
                        </c:if>
                        <c:if test="${clientType!=client.clientType}">
                            <option value="${clientType}">${clientType}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Scope</td>
            <c:forEach var="scopeEntry" items="${scope}">
                <c:if test="${scopeEntry.value}">
                    <td><input type="checkbox" name="scope" value="${scopeEntry.key}" selected>${scopeEntry.key}</td>
                </c:if>
                <c:if test="${!scopeEntry.value}">
                    <td><input type="checkbox" name="scope" value="${scopeEntry.key}">${scopeEntry.key}</td>
                </c:if>
            </c:forEach>
        </tr>
        <tr>
            <td>Grant Types</td>
            <c:forEach var="grantTypeEntry" items="${grantType}">
                <c:if test="${grantTypeEntry.value}">
                    <td><input type="checkbox" name="grantType" value="${grantTypeEntry.key}" selected/>${grantTypeEntry.key}</td>
                </c:if>
                <c:if test="${!grantTypeEntry.value}">
                    <td><input type="checkbox" name="grantType" value="${grantTypeEntry.key}"/>${grantTypeEntry.key}</td>
                </c:if>
            </c:forEach>
        </tr>
        <tr>
            <td>Client Identifier</td>
            <td>${app_identifier}</td>
        </tr>
        <tr>
            <td>Client Secret</td>
            <td>${app_password}</td>
        </tr>
    </table>
    <input type="submit" value="Save Changes" formmethod="post"/>
    <button>Delete</button>
</form>
<%--</form>
<form:form modelAttribute="client" onsubmit="processOutGoingData(this,['grantTypeCollection','scopeCollection'])">
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
            <td><form:input path="redirectUriCollection"/></td>
            <td><form:errors path="redirectUriCollection" cssClass="error"/></td>
        </tr>
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
            <td>Scope</td>
            <td>
                <form:checkbox path="scopeCollection" value="read"/>Read
                <form:checkbox path="scopeCollection" value="write"/>Write
            </td>
            <td><form:errors path="scopeCollection" cssClass="error"/></td>
            </td>
        </tr>
        <tr>
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
        <tr>
        <td>Client Identifier</td>
            <td>${app_identifier}</td>
        </tr>
        <tr>
            <td>Client Secret</td>
            <td>${app_password}</td>
        </tr>

    </table>
    <input type="submit" value="Save Changes" formmethod="post" />
    <button>Delete</button>
</form:form>--%>
<%@ include file="/WEB-INF/template/footer.jsp" %>
<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>

<table border="1">
    <tr>
        <th>S.no</th>
        <th>Name</th>
        <th>Description</th>
        <th>Website</th>
        <th>Redirection URI</th>
        <th>Client Type</th>
        <th>Client ID</th>
        <th>Client Secret</th>
        <th>Number of users</th>
    </tr>
    <%! int sNo = 0; %>
    <c:forEach items="${registeredApplications}" var="client" varStatus="i">
        <% sNo++;%>
        <tr>
            <td><c:out value="<%= sNo%>"/></td>
            <td><c:out value="${client.name}"/></td>
            <td><c:out value="${client.description}"/></td>
            <td><c:out value="${client.website}"/></td>
            <td><c:out value="${client.redirectionURI}"/></td>
            <td><c:out value="${client.clientType}"/></td>
            <td><c:out value="${client.clientIdentifier}"/></td>
            <td><c:out value="TODO"/></td>
            <td><c:out value="TODO"/></td>
        </tr>
    </c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>
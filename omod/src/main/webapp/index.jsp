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

<div>
    <b class="boxHeader"><openmrs:message code="Person.find"/></b>

    <div class="box">
        <div class="searchWidgetContainer" id="findPersons">
            <table>
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
                <% int sNo = 0; %>
                <c:forEach items="${clients}" var="client" varStatus="i">
                    <% sNo++;%>
                    <tr>
                        <td><c:out value="<%= sNo%>"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/module/oauth2/client/registered/view/${client.id}.form"><c:out
                                    value="${client.name}"/></a></td>
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
        </div>
    </div>
</div>
<p>
    <a href="${pageContext.request.contextPath}/module/oauth2/client/registered/viewEdit.form">Clicking on a name</a>
    will open up viewEdit form
</p>
<%@ include file="/WEB-INF/template/footer.jsp" %>
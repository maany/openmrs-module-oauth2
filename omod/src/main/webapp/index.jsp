<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="template/localHeader.jsp" %>

<h2>
    <openmrs:message code="oauth2.manage.registered"/>
</h2>
<a href="${pageContext.request.contextPath}/module/oauth2/client/registrationLink.form"><openmrs:message
        code="oauth2.client.registered.new"/></a><br/><br/>
</p>


<div>
    <b class="boxHeader"><openmrs:message code="oauth2.client.registered.list"/></b>

    <div class="box">
        <div class="searchWidgetContainer" id="findPersons">
            <table>
                <tr>
                    <th>S.no</th>
                    <th>Name</th>
                    <th>Website</th>
                    <th>Redirection URI</th>
                    <th>Client Type</th>
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
                        <td><c:out value="${client.registeredRedirectUri}"/></td>
                        <td><c:out value="${client.clientType}"/></td>
                        <td><c:out value="TODO"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>
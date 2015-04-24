<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<p>Application Registration</p>
<form action="" method="get">
    Application Name <input type="text" name="name"/><br />
    Application Desciption <input type="text" name="description"/><br />
    Application Link <input type="text" name="application_link"/><br />
    Redirection URI <input type="text" name="redirection_uri"/><br />
    Scope<br/>
    <ul>
        <li><input type="checkbox" name="scope" value="fhir_read">Read FHIR Resources</li>
    </ul>
    Client Type <br/>
    <ul>
        <li><input type="checkbox" name="client_type" value="web">Web Application</li>
        <li><input type="checkbox" name="client_type" value="mobile">Mobile Application</li>
        <li><input type="checkbox" name="client_type" value="user_agent">User Agent Based Application</li>
    </ul>
    <input type="submit"/>
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>
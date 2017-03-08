<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException" %>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>OAUTH2_SERVER Server</title>
    <link type="text/css" rel="stylesheet"
          href="../webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <script type="text/javascript"
            src="../webjars/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="../webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>

<div class="container">
    <h1>OAUTH2_SERVER Server</h1>
   
    <sec:authorize access="hasRole('ROLE_USER')">
        <p>
            El cliente: "<c:out value="${client.clientId}"/>" tiene acceso protegido a los recursos.
        </p>

        <form id="confirmationForm" name="confirmationForm"
              action="<%=request.getContextPath()%>/oauth/authorize" method="post">
            <input name="user_oauth_approval" value="true" type="hidden"/>
            <ul class="list-unstyled">
                <c:forEach items="${scopes}" var="scope">
                    <c:set var="approved">
                        <c:if test="${scope.value}"> checked</c:if>
                    </c:set>
                    <c:set var="denied">
                        <c:if test="${!scope.value}"> checked</c:if>
                    </c:set>
                    <li>
                        <div class="form-group">
                                ${scope.key}: <input type="radio" name="${scope.key}"
                                                     value="true" ${approved}>Approve</input> <input type="radio"
                                                                                                     name="${scope.key}"
                                                                                                     value="false" ${denied}>Deny</input>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <button class="btn btn-primary" type="submit">Submit</button>
        </form>
    </sec:authorize>

</div>

</body>
</html>

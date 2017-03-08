
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>OAUTH2_SERVER</title>
<link type="text/css" rel="stylesheet"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<script type="text/javascript" src="webjars/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript"
	src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>


</head>
<body>

	<div class="container">

		<h1>OAUTH2_SERVER</h1>

		
		<sec:authorize access="hasRole('ROLE_USER')">
			<div class="form-horizontal">
				<form action="<c:url value="/logout.do"/>" role="form">
					<button class="btn btn-primary" type="submit">Logout</button>
				</form>
			</div>
		</sec:authorize>

	</div>

</body>
</html>

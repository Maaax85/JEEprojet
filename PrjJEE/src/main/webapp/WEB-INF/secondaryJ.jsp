<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.octest.servlets.SecondaryPage"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Seconde page</title>

<style>
.left {
	float: left;
}

.right {
	float: right;
}

#submit-print {
	clear: both;
}
</style>

</head>
<body>

	<c:if test="${ !empty erreur }">
		<p style="color: red;">
			<c:out value="${ erreur }" />
		</p>
	</c:if>
	<form>
		<p>
			<a href="Primary">Premiere page</a>
		</p>
	</form>

	<ul>
		<c:forEach var="equipes" items="${ equipes }">
			<li><c:out value="${ equipes.nom }" /></li>
		</c:forEach>
	</ul>


	<form method="post" action="Secondary">
		<input type="hidden" name="action"
			value="boutonCompositionAutomatique" /> <input type="submit"
			value="Composition des équipes automatique" />
	</form>

</body>
</html>
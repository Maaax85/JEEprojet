<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.octest.servlets.SecondaryPage"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Second Page</title>
<style>
body {
	max-width: 800px;
	margin: 0 auto;
	text-align: center;
}

h1 {
	font-weight: bold;
	margin: 10px
}

ul {
	list-style: none;
	padding: 0;
	margin: 0;
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
}

li {
	margin: 10px;
}

.liste-etudiants-sans-equipe-container {
	margin: 20px
}

.equipe-container {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
}

.equipe {
	margin: 20px;
	text-align: center;
}

.nom-equipe, label {
	font-weight: bold;
	margin-bottom: 10px;
}

.liste-etudiants {
	list-style: none;
	padding: 0;
	margin: 0;
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
}

.etudiant {
	margin: 10px;
}

.btn-supprimer, .btn-ajouter, .btn-compo {
	margin: 10px;
}
</style>
</head>
<body>

	<c:if test="${ !empty erreur }">
		<p style="color: red;">
			<c:out value="${ erreur }" />
		</p>
	</c:if>

	<h1>
		<a href="Primary">Premiere page</a>
	</h1>


	<label for="nombreEquipes">Nombre d'équipes :</label>
	<input type="number" id="nombreEquipes" name="nombreEquipes" min="1"
		max="10" />
	<button class="btn-creer-equipes">Créer les équipes</button>

	<div class="liste-etudiants-sans-equipe-container">
		<label for="liste-etudiants-sans-equipe">Liste étudiants sans
			équipe : </label>
		<ul id="liste-etudiants-sans-equipe">
			<c:forEach var="etudiant" items="${ etudiantsSansEquipe }">
				<li><c:out value="${ etudiant.nom }" /></li>
			</c:forEach>
		</ul>
	</div>
	<div class="equipe-container">
		<c:forEach var="equipe" items="${ equipes }">
			<div class="equipe">
				<div class="nom-equipe">${ equipe.nom }</div>
				<%-- <ul class="liste-etudiants">
					<c:forEach var="etudiant" items="${ equipe.etudiants }">
						<li class="etudiant">${ etudiant.nom }
							<button class="btn-supprimer">Supprimer</button>
						</li>
					</c:forEach>
				</ul> --%>
				<button class="btn-ajouter-etudiant">Ajouter un étudiant</button>
				<button class="btn-modifier-nom-equipe">Modifier le nom de
					l'équipe</button>
			</div>
		</c:forEach>
	</div>

	<form method="post" action="Secondary">
		<input type="hidden" name="action"
			value="boutonCompositionAutomatique" /> <input type="submit"
			value="Composition des équipes automatique" />
	</form>

</body>

</html>
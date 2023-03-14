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

.btn-supprimer, .btn-ajouter, .btn-compo, .btn-modifier-nom-equipe {
	margin: 10px;
}
</style>
</head>
<body>

	<c:out value="${ critere }" />
	<c:out value="${ nomEtudiant }" />
	<c:out value="${ nouveauNomEquipe }" />

	<c:if test="${ !empty erreur }">
		<p style="color: red;">
			<c:out value="${ erreur }" />
		</p>
	</c:if>
	<h1>
		<a href="Primary">Premiere page</a>
	</h1>
	<form method="post" action="Secondary">
		<label for="nombreEquipes">Nombre d'équipes à rajouter :</label> <input
			type="number" id="nombreEquipes" name="nombreEquipes" min="1"
			max="10" />
		<button type="submit">Valider le nombre d'équipe</button>
	</form>

	<form method="post" action="Secondary">
		<input type="hidden" name="boutonExport" value="boutonExport" /> <input
			type="submit" value="Exporter les équipes" />
	</form>

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
				<ul class="liste-etudiants">
					<c:set var="etudiantsEquipe"
						value="${ equipeDao.listerEtudiantsEquipe(equipe.nom) }" />
					<c:forEach var="etudiant" items="${ etudiantsEquipe }">
						<li class="etudiant">${ etudiant.nom }
							<form method="post" action="Secondary">
								<input type="hidden" name="etudiantRemove"
									value="${etudiant.nom}" /> <input type="hidden"
									name="equipeRemove" value="${equipe.nom}" />
								<button type="submit">Retirer</button>
							</form>
						</li>
					</c:forEach>
				</ul>
				<form method="post" action="Secondary">
					<input type="hidden" name="equipeAdd" value="${equipe.nom}" /> <label
						for="nomEtudiant">Nom de l'étudiant : </label> <input type="text"
						name="nomEtudiant" id="nomEtudiant" />
					<button type="submit">Ajouter l'étudiant</button>
				</form>
				<form method="post" action="Secondary">
					<input type="hidden" name="equipeModifyName" value="${equipe.nom}" />
					<label for="nouveauNomEquipe">Nouveau nom de l'équipe : </label> <input
						type="text" name="nouveauNomEquipe" id="nouveauNomEquipe" />
					<button type="submit">Modifier le nom de l'équipe</button>
				</form>
			</div>
		</c:forEach>
	</div>

	<form method="post" action="Secondary">

		<p>
			<label for="critere">Critère de génération automatique : </label> <select
				name="critere" id="critere">
				<option value=Random>Aléatoire</option>
				<option value=Alphabetique>Alphabétique</option>
			</select>
		</p>

		<input type="hidden" name="boutonCompositionAutomatique"
			value="boutonCompositionAutomatique" /> <input type="submit"
			value="Composition des équipes automatique" />
	</form>
</body>

</html>
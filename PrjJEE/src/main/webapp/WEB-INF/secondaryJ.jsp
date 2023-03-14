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
#erreur-message {
    color: red;
  }
  
  .liste-etudiants-sans-equipe-container label {
    font-weight: bold;
    mrgin-top:15px;
  }
  
  .equipe {
    border: 1px solid black;
    margin-bottom: 10px;
    padding: 10px;
  }
  
  .nom-equipe {
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .liste-etudiants {
    list-style-type: none;
  }
  
  .liste-etudiants li {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 5px;
  }
  
  .etudiant {
    flex: 1;
    margin-right: 10px;
  }
  
  button {
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
			<div class="equipe" id="equipe-${equipe.nom}">
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
								<button type="submit" class="remove-button">Retirer</button>
							</form>
						</li>
					</c:forEach>
				</ul>
				<form method="post" action="Secondary">
					<input type="hidden" name="equipeAdd" value="${equipe.nom}" /> <label
						for="nomEtudiant">Nom de l'étudiant : </label> <input type="text"
						name="nomEtudiant" id="nomEtudiant" />
					<button type="submit" class="add-button">Ajouter
						l'étudiant</button>
				</form>
				<form method="post" action="Secondary">
					<input type="hidden" name="equipeModifyName" value="${equipe.nom}" />
					<label for="nouveauNomEquipe">Nouveau nom de l'équipe : </label> <input
						type="text" name="nouveauNomEquipe" id="nouveauNomEquipe" />

					<button type="submit" class="modifier-nom-equipe-button">Modifier
						le nom de l'équipe</button>

				</form>
			</div>
		</c:forEach>
	</div>
	<form method="post" action="Secondary"
		class="composition-automatique-form">
		<p>
			<label for="critere">Critère de génération automatique : </label> <select
				name="critere" id="critere">
				<option value="Random">Aléatoire</option>
				<option value="Alphabetique">Alphabétique</option>
			</select>
		</p>
		<input type="hidden" name="boutonCompositionAutomatique"
			value="boutonCompositionAutomatique" />
		<button type="submit" class="composition-automatique-button">Composition
			des équipes automatique</button>
	</form>
	<form method="post" action="Secondary" class="export-form">
		<input type="hidden" name="boutonExport" value="boutonExport" />
		<button type="submit" class="export-button">Exporter les
			équipes</button>
	</form>
</body>


</html>
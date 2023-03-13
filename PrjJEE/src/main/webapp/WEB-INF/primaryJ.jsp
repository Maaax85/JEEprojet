<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Première page</title>
<style>
body {
	max-width: 800px;
	margin: 0 auto;
	text-align: center;
}

h1 {
	font-weight: bold;
	margin: 10px;
}

p {
	margin: 10px 0;
	padding: 0;
	font-size: 16px;
}

label {
	font-weight: bold;
	margin-bottom: 10px;
}
</style>

</head>
<body>
	<c:if test="${ !empty erreur }">
		<p style="color: red;">
			<c:out value="${ erreur }" />
		</p>
	</c:if>
	<c:out value="${ nom }" />
	<c:out value="${ prenom }" />
	<c:out value="${ genre }" />
	<c:out value="${ previousSite }" />
	<c:out value="${ previousFormation }" />


	<h1>
		<a href="Secondary">Seconde page</a>
	</h1>


	<form method="post" action="Primary">

		<p>
			<label for="nom">Nom : </label> <input type="text" name="nom"
				id="nom" />
		</p>
		<p>
			<label for="prenom">Prénom : </label> <input type="text"
				name="prenom" id="prenom" />
		</p>
		<p>
			<label for="genre">Genre : </label> <select name="genre" id="genre">
				<option value=homme>Homme</option>
				<option value=femme>Femme</option>
			</select>
		</p>
		<p>
			<label for="previousSite">Site précédent : </label> <input
				type="text" name="previousSite" id="previousSite" />
		</p>
		<p>
			<label for="previousFormation">Formation précédente : </label> <input
				type="text" name="previousFormation" id="previousFormation" />
		</p>

		<input type="hidden" name="action" value="boutonAddEtu" /> <input
			type="submit" value="Ajouter l'étudiant renseigné" />

	</form>

	<c:if test="${ !empty fichier }">
		<p>
			<c:out value="Le fichier ${ fichier } a été uploadé !" />
		</p>
	</c:if>
	<form method="post" action=Primary enctype="multipart/form-data">
		<p>
			<label for="fichier">Fichier à envoyer : </label> <input type="file"
				name="fichier" id="fichier" />
		</p>

		<input type="hidden" name="action" value="boutonLoadEtus" /> <input
			type="submit" value="Importer une liste d'étudiants" />

	</form>


</body>
</html>
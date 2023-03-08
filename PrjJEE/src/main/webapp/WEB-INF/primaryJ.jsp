<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Première page</title>

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

    <c:out value="${ nom }" />
    <c:out value="${ prenom }" />
    <c:out value="${ genre }" />
    <c:out value="${ previousSite }" />
    <c:out value="${ previousFormation }" />
    
    <form>
    	<p><a href="Secondary">Seconde page</a></p>
    </form>
    
    <form method="post" action="primaryJ">
    	
	        <p>
	            <label for="nom">Nom : </label>
	            <input type="text" name="nom" id="nom" />
	        </p>
	        <p>
	            <label for="prenom">Prénom : </label>
	            <input type="text" name="prenom" id="prenom" />
	        </p>
	        <p>
	            <label for="genre">Genre : </label>
	            <input type="text" name="genre" id="genre" />
	        </p>
	        <p>
	        	<label for="previousSite">Site précédent : </label>
	        	<input type="text" name="previousSite" id="previousSite" />
	        </p>
	        <p>
	            <label for="previousFormation">Formation précédente : </label>
	            <input type="text" name="previousFormation" id="previousFormation" />
	        </p>
	        
	        <input type="hidden" name="action" value="boutonAddEtu" />
	        <input type="submit" value="Ajouter l'étudiant renseigné"/>
        
    </form>
    
    <c:if test="${ !empty fichier }"><p><c:out value="Le fichier ${ fichier } a été uploadé !" /></p></c:if>
    <form method="post" action="primaryJ" enctype="multipart/form-data">
        <p>
            <label for="fichier">Fichier à envoyer : </label>
            <input type="file" name="fichier" id="fichier" />
        </p>
        
        <input type="hidden" name="action" value="boutonLoadEtus" />
        <input type="submit" value="Importer une liste d'étudiants"/>
        
    </form>
    
</body>
</html>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    
    <form>
    	<p><a href="Primary">Premiere page</a></p>
    </form>
    
    <form method="post" action="seondaryJ">
    	
	        <p>
	            <label for="nom">Nom : </label>
	        </p>
	        <p>
	            <label for="prenom">Prénom : </label>
	        </p>
	        <p>
	            <label for="genre">Genre : </label>
	        </p>
	        <p>
	        	<label for="previousSite">Site précédent : </label>
	        </p>
	        <p>
	            <label for="previousFormation">Formation précédente : </label>
	        </p>
	        <p>
	            <label for="Test">Test 3: </label>
	        </p>
        
    </form>
    
    <form method="post" action="seondaryJ">
	        <input type="hidden" name="action" value="boutonCompositionAutomatique" />
	        <input type="submit" value="Composition des équipes automatique"/>
    </form>
    
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<%-- Inclusion de l'en-tete --%>
<%@ include file="../inclusion/en_tete.jsp" %>


<body>

<%-- Inclusion de la barre du menu avec les formulaire de connexion et d'inscription --%>
<%@ include file="../inclusion/barre_menu.jsp" %>


<div class="container">
    <div class="row">
        <div class="col-lg-offset-2 col-lg-8">
            <form class="form-horizontal" action="<c:url value="/mps/creer_trajet" />">

                <div>
                    <h3 style="text-align: center;">Ville départ</h3>

                    <!-- Select Basic -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="horaire_depart">Horaires de départ</label>
                        <div class="col-md-4">
                            <input id="horaire_depart" name="horaire_depart" type="text"
                                   placeholder="Date ex : 21/07/2017"
                                   class="form-control input-md">
                        </div>

                        <div class="col-md-2">
                            <input id="heure_depart" name="heure_depart" type="text" placeholder="Heures"
                                   class="form-control input-md">
                        </div>
                        <div class="col-md-2">
                            <input id="minute_depart" name="minute_depart" type="text" placeholder="Minutes"
                                   class="form-control input-md">
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="nbPlaces">Nombres de places</label>
                        <div class="col-md-4">
                            <input id="nbPlaces" name="nbPlaces" type="text" placeholder="Nombres de places"
                                   class="form-control input-md">
                        </div>
                    </div>

                    <!-- Select Basic -->
                    <div class="form-group">
                        <label class="col-md-4 control-label" for="villeDepart">Ville de départ</label>
                        <div class="col-md-4">
                            <select id="villeDepart" name="villeDepart" class="form-control">
                                <c:forEach var="uneVille" items="${listeVille}">
                                        <option value="<c:out value="${uneVille.id}"></c:out>">
                                            <c:out value="${uneVille.nom}"></c:out>
                                        </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                </div>


                <div id="ville_intermediaire">

                </div>

                <button id="add_ville" type="button" class="btn btn-primary" onclick="addVille()">Ajouter une ville
                    intermédiaires
                </button>

                <h3>Ville arrivé</h3>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="horaire_final">Heure d'arrivé</label>
                    <div class="col-md-4">
                        <input id="horaire_final" name="horaire_final" type="text" placeholder="Date ex : 21/07/2017"
                               class="form-control input-md">

                    </div>

                    <div class="col-md-2">
                        <input id="heure_final" name="heure_final" type="text" placeholder="Heures"
                               class="form-control input-md">
                    </div>
                    <div class="col-md-2">
                        <input id="minute_final" name="minute_final" type="text" placeholder="Minutes"
                               class="form-control input-md">
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="prix_final">Prix</label>
                    <div class="col-md-4">
                        <input id="prix_final" name="prix_final" type="text" placeholder="Prix"
                               class="form-control input-md">

                    </div>
                </div>

                <!-- Select Basic -->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="ville_final">Ville de fin</label>
                    <div class="col-md-4">
                        <select id="ville_final" name="ville_final" class="form-control">
                            <c:forEach var="uneVille" items="${listeVille}">
                                <option value="<c:out value="${uneVille.id}"></c:out>">
                                    <c:out value="${uneVille.nom}"></c:out>
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                </div>

                <center>
                    <button id="submit" type="submit" class="btn btn-primary" name="action" value="ok">
                        Valider création
                    </button>
                </center>

            </form>
        </div>
    </div>
</div>

<script>

    var nbVilleIntermediaire = 0;

    function addVille() {
        nbVilleIntermediaire = nbVilleIntermediaire + 1;

        var ajoutBlock = '<h3>Ville intermédiaire ' + nbVilleIntermediaire + '</h3>'
                + '<div class="form-group"> '
                + '<label class="col-md-4 control-label" for="textinput">Heure d\'arrivé</label>'
                + ' <div class="col-md-4">'
                + '     <input id="date_ville_inte_' + nbVilleIntermediaire + '" name="date_ville_inte_' + nbVilleIntermediaire + '" type="text" placeholder="Horaire" class="form-control input-md" required="">'
                + ' </div>'
                + '<div class="col-md-2">'
                + '<input id="heure_inte_' + nbVilleIntermediaire + '" name="heure_inte_' + nbVilleIntermediaire + '" type="text" placeholder="Heures" class="form-control input-md">'
                + '</div>'
                + '<div class="col-md-2">'
                + '<input id="minute_inte_' + nbVilleIntermediaire + '" name="minute_inte_' + nbVilleIntermediaire + '" type="text" placeholder="Minutes" class="form-control input-md">'
                + '</div>'
                + '</div>'
                + '<div class="form-group"> '
                + '<label class="col-md-4 control-label" for="textinput">Prix</label>'
                + ' <div class="col-md-4">'
                + '     <input id="prix_inte_' + nbVilleIntermediaire + '" name="prix_inte_' + nbVilleIntermediaire + '" type="text" placeholder="Horaire" class="form-control input-md" required="">'
                + ' </div>'
                + '</div>'
                + '<div class="form-group"> '
                + '<label class="col-md-4 control-label" for="textinput">Ville</label>'
                + ' <div class="col-md-4">'
                + '<select id="ville_inte_' + nbVilleIntermediaire + '" name="ville_inte_' + nbVilleIntermediaire + '" class="form-control">'
                + '<c:forEach var="uneVille" items="${listeVille}"><option value="<c:out value="${uneVille.id}"></c:out>"><c:out value="${uneVille.nom}"></c:out></option></c:forEach>'
                + '</select>'
                + ' </div>'
                + '</div>';

        $('#ville_intermediaire').append(ajoutBlock);

        // document.getElementById('ville_intermediaire').innerHTML = ajoutBlock;
    }

    /*
     $(document).ready(function () {
     $(function () {
     $('#datetimepicker6').datetimepicker({
     format: 'D/M/YYYY  h:mm',
     locale: 'fr'
     });
     });
     });

     */

</script>

<%@ include file="../inclusion/pied_de_page.jsp" %>


</body>
</html>
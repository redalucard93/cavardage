<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<%-- Inclusion de l'en-tete --%>
<%@ include file="../inclusion/en_tete.jsp" %>


<body>

<%-- Inclusion de la barre du menu avec les formulaire de connexion et d'inscription --%>
<%@ include file="../inclusion/barre_menu.jsp" %>

<p class="error">${messageAvisDejaLaisse}</p>
<div style="margin-top: 10px; margin-bottom: 10px;">

    <div class="container">
        <div class="row">
            <div class="col-lg-3">
            </div>
            <div class="col-lg-6">
                <div>

                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#profil" aria-controls="profil" role="tab"
                                                                  data-toggle="tab">Profil</a></li>
                        <li role="presentation"><a href="#mesResa" id="reserver" aria-controls="mesResa" role="tab"
                                                   data-toggle="tab">Mes réservations</a></li>
                        <li role="presentation"><a href="#mesTrajet" aria-controls="mesTrajet" role="tab"
                                                   data-toggle="tab">Mes trajets proposé</a></li>
                    </ul>

                    <!-- Tab panes -->
                </div>

            </div>

        </div>
    </div>
</div>

<div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="profil">
        <div class="container" style="margin-bottom: 20px;">
            <div class="row">
                <div class="col-lg-5" style="margin-left: -10px; margin-right: 10px;">
                    <div class="row">
                        <div class="col-lg-2">
                        </div>
                        <div class="col-lg-10" style="background-color: #f2f2f2;">
                            <div>
                                <center style="border-bottom: 2px solid black; margin-bottom: 20px;">
                                    <img src="../assets/images/icon_profil.png" width="180" height="180"/>
                                </center>

                                <h3 style="text-align: center;">Informations</h3>
                                </br>
                                <p>Nom : <c:out value="${personne.nom}"/></p>
                                <p>Prénom : <c:out value="${personne.prenom}"/></p>
                                <p>Email : <c:out value="${personne.login}"/></p>
                            </div>
                            <div>
                                <button type="button" class="btn btn-warning" data-toggle="modal"
                                        data-target="#modificationInformation">Mettre à jour son profil
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top: 10px;">
                        <div class="col-lg-2">
                        </div>
                        <div class="col-lg-10" style="background-color: #f2f2f2;">
                            <h3 style="text-align: center;">Informations Véhicule</h3>

                            <c:if test="${personne.voiture == null}">
                                <button type="button" class="btn btn-info" data-toggle="modal"
                                        data-target="#ajoutVoiture">Ajouter voiture
                                </button>
                            </c:if>

                            <c:if test="${personne.voiture != null}">
                                <p>Modele : <c:out value="${personne.voiture.modele}"/></p>
                                <p>Nombre de places : <c:out value="${personne.voiture.nbrPlaces}"/></p>
                                <p>Gabarit : <c:out value="${personne.voiture.gabarit.libelle}"/></p>

                                <button style="margin-top: 3px;" type="button" class="btn btn-warning"
                                        data-toggle="modal"
                                        data-target="#modifierVoiture">Modifier véhicule
                                </button>
                            </c:if>

                            <%-- Formulaire d'ajout d'une voiture --%>
                            <div class="modal fade" id="ajoutVoiture">
                                <div class="modal-dialog">
                                    <div class="modal-content">

                                        <form class="form-horizontal" action="<c:url value="/mps/gestion_voiture" />">

                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <h3 style="text-align:center;">Ajout d'une voiture</h3>
                                                    </div>
                                                    <div class="col-lg-12" style="margin-top: 20px;">

                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <input name="modele" type="text" class="form-control"
                                                                       id="modele" placeholder="Modèle du véhicule">
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <input name="nbrPlaces" type="text" class="form-control"
                                                                       id="nbrPlaces" placeholder="Nombre de places">
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <div class="col-md-4">
                                                                <select id="gabarit" name="gabarit"
                                                                        class="form-control">
                                                                    <c:forEach var="unGabarit" items="${listeGabarit}">
                                                                        <option value="<c:out
                                                                                value="${unGabarit.id}"></c:out>"><c:out
                                                                                value="${unGabarit.libelle}"></c:out></option>

                                                                    </c:forEach>

                                                                </select>
                                                            </div>
                                                        </div>


                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                    Fermer
                                                </button>
                                                <button name="action" id="submit" value="ajout" class="btn btn-primary">
                                                    Ajouter
                                                </button>
                                            </div>
                                        </form>


                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="modifierVoiture">
                                <div class="modal-dialog">
                                    <div class="modal-content">

                                        <form class="form-horizontal" action="<c:url value="/mps/gestion_voiture" />">

                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <h3 style="text-align:center;">Modification d'une voiture</h3>
                                                    </div>
                                                    <div class="col-lg-12" style="margin-top: 20px;">

                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <input name="modeleModif" type="text"
                                                                       class="form-control"
                                                                       id="modeleModif" placeholder="Modèle du véhicule"
                                                                       value="<c:out
                                                                                value="${personne.voiture.modele}"></c:out>">
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <div class="col-md-12">
                                                                <input name="nbrPlacesModif" type="text"
                                                                       class="form-control"
                                                                       id="nbrPlacesModif"
                                                                       placeholder="Nombre de places"
                                                                       value="<c:out
                                                                                value="${personne.voiture.nbrPlaces}"></c:out>">
                                                            </div>
                                                        </div>

                                                        <div class="form-group">
                                                            <div class="col-md-4">
                                                                <select id="gabaritModif" name="gabaritModif"
                                                                        class="form-control">
                                                                    <option value="<c:out
                                                                                value="${personne.voiture.gabarit.id}"></c:out>">
                                                                        <c:out
                                                                                value="${personne.voiture.gabarit.libelle}"></c:out></option>

                                                                    <c:forEach var="unGabarit" items="${listeGabarit}">
                                                                        <c:if test="${unGabarit.id != personne.voiture.gabarit.id}">
                                                                            <option value="<c:out
                                                                                value="${unGabarit.id}"></c:out>"><c:out
                                                                                    value="${unGabarit.libelle}"></c:out></option>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                </select>
                                                            </div>
                                                        </div>


                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                    Fermer
                                                </button>
                                                <button name="action" id="submitModif" value="modif"
                                                        class="btn btn-warning">
                                                    Modifier
                                                </button>
                                            </div>
                                        </form>


                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="col-lg-7">
                    <div class="row">
                        <div class="col-lg-10" style="background-color: #e7e7e7;">

                            <h3 style="text-align: center;">Avis</h3>

                            <p>Moyenne des avis : ${moyenne} / 5</p>

                            <div class="row">
                                <div class="col-lg-1">
                                </div>
                            <c:if test="${not empty avis}">
                            <c:forEach items="${avis}" var="a">
                                <div class="col-lg-10 bloc_avis">
                                    <div style="border-bottom: 1px solid black;">
                                        <div class="row">
                                            <div class="col-lg-8">
                                                <p>${a.emetteur.nom}</p>
                                            </div>
                                            <div class="col-lg-4">
                                                <p style="text-align: right;">
                                                    <img src="../assets/images/star.png" width="18" height="18"/> ${a.note}/5
                                                </p>
                                            </div>

                                        </div>
                                    </div>
                                    <div style="margin-top: 10px;">
                                        <p>${a.message}</p>
                                    </div>
                                </div>
                            </c:forEach>
                            </c:if>
                            </div>

                        </div>
                    </div>

                </div>


            </div>
        </div>

        <%-- Formulaire de modification d'information du profil utilisateur --%>
        <div class="modal fade" id="modificationInformation">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="<c:url value="/mps/modification_profil" />" method="post">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <h3 style="text-align:center;">Modification Profil</h3>
                                </div>
                                <div class="col-lg-12" style="margin-top: 20px;">

                                    <div class="form-group">
                                        <label for="nom" class="col-sm-2 col-form-label">Nom</label>
                                        <input name="nom" type="text" class="form-control" id="nom"
                                               placeholder="nom" value="<c:out value="${personne.nom}"/>">
                                    </div>

                                    <div class="form-group">
                                        <label for="prenom" class="col-sm-2 col-form-label">Prénom</label>
                                        <input name="prenom" type="text" class="form-control" id="prenom"
                                               placeholder="prénom" value="<c:out value="${personne.prenom}"/>">
                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                            <button name="action" value="modifierProfil" type="submit" class="btn btn-primary">
                                Modifier
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>

    <%-- Onglet qui gere la listes des réservation éffectué --%>
    <div role="tabpanel" class="tab-pane" id="mesResa">
        <h3 style="text-align: center;">Liste de mes réservations</h3>
        <div class="container">
            <div class="row" style="margin-top: 20px;">
                <div class="col-lg-offset-3 col-lg-6">

                    <c:if test="${listeReservation.isEmpty()}">
                        <p style="text-align: center;">Aucune réservation</p>
                    </c:if>

                   <div id="result"></div>


                </div>
            </div>
        </div>
    </div>

    <%-- Onglet qui gère les trajets créés par l'utilisateurs --%>
    <div role="tabpanel" class="tab-pane" id="mesTrajet">
        <h3 style="text-align: center;">Liste de mes trajets créés</h3>

        <p style="text-align: center;">
            <a href="<c:url value="/mps/creer_trajet" />">
                <button type="submit" class="btn btn-success" style="width: 150px;">Ajouter un trajet</button>
            </a>
        </p>

        <c:if test="${trajetsCree.size() == 0}">
            <p style="text-align: center;">Aucun trajet crée</p>
        </c:if>

        <c:forEach var="unTrajetCree" items="${trajetsCree}" varStatus="it">
            <div class="container">
                <div class="row" style="margin-top: 20px;">
                    <div class="col-lg-offset-3 col-lg-6">
                        <div class="row bloc_trajet_cree">
                            <div class="col-lg-6 info_trajet">
                                <h3>Départ le <c:out value="${unTrajetCree.heureDepart.getDayOfMonth()}"/> /
                                    <c:out value="${unTrajetCree.heureDepart.getMonthValue()}"/> /
                                    <c:out value="${unTrajetCree.heureDepart.getYear()}"/></h3>
                                <p>
                                    <img src="../assets/images/start_flag.png" width="18" height="18"/> Départ :
                                    <c:out value="${unTrajetCree.villeDepart.nom}"/>
                                </p>
                                <p>
                                    <img src="../assets/images/end_flag.png" width="18" height="18"/> Arrivé :
                                    <c:out value="${unTrajetCree.getEtapeFinal().villeArrivee.nom}"/>
                                </p>

                                <p>
                                    Prix moyen : <c:out value="${listeMoyenne.get(it.index)}"/>
                                </p>
                            </div>

                            <div class="col-lg-6" style="text-align: right; margin-top: 10px;">
                                <a href="<c:url value="/mps/liste_reservation" />?idTrajet=<c:out value="${unTrajetCree.id}"/>">
                                    <button type="submit" class="btn btn-info"
                                            style="width: 150px; margin-bottom: 5px;">
                                        Voir les demandes
                                    </button>
                                </a>
                                <button type="submit" class="btn btn-warning"  style="width: 150px; margin-bottom: 5px;">
                                    Voir en détail
                                </button>
                                <button type="submit" class="btn btn-danger" style="width: 150px;">Supprimer</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>


    </div>
</div>


<%@ include file="../inclusion/pied_de_page.jsp" %>

</body>
<script>

    $(document).on("click", "#reserver", function () {

        $.ajax({
            method: "GET",
            url: "/webCavardages/mps/ConsulterReservationsDemandees",
            success: function (results) {
                $("#result").load(results);
            },
            error: function (response) {
            }
        });
    });
</script>
</html>
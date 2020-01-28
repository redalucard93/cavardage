<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%-- Inclusion de l'en-tete --%>
<%@ include file="../inclusion/en_tete.jsp" %>
<body>
<%-- Inclusion de la barre du menu avec les formulaire de connexion et d'inscription --%>
<%@ include file="../inclusion/barre_menu.jsp" %>
<div style="margin-top: 10px; margin-bottom: 10px;">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <h3 style="text-align: center;">Informations</h3>
                <div style="text-align: center;">
                    <p>Vous avez la possibilité de:
                        </br>
                        Modifier, Ajouter ou Supprimer des Gabarits de voiture
                        </br>
                        Modifier, Ajouter ou Supprimer des Villes</p>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
            </div>
            <div class="col-lg-6">
                <div>
                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active">
                            <a href="#gabarit" aria-controls="gabarit" role="tab" data-toggle="tab">Gestion des
                                Gabarits</a>
                        </li>
                        <li role="presentation">
                            <a href="#ville" aria-controls="ville" role="tab"
                               data-toggle="tab">Gestion des Villes</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- Onglet permettant de gérer les Gabarits --%>
<div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="gabarit">
        <h3 style="text-align: center;">Gabarits</h3>
        <div class="container">
            <div class="row" style="margin-top: 20px;">
                <div class="col-lg-offset-3 col-lg-6">
                    <div class="row bloc_Gabarit">
                        <%-- Onglet permettant d'ajouter un Gabarit --%>
                        <div class="container">
                            <div class="row" style="margin-top: 20px;">
                                <div class="col-lg-6 info_trajet">
                                    <div class="col-lg-12" style="background-color: #f2f2f2;">
                                        <h3 style="text-align:center;">Ajout d'un Gabarit</h3>
                                        <form class="form-horizontal"
                                              action="<c:url value="/mps/GestionAdministrateur" />">
                                            <div class="form-group">
                                                <div class="col-md-6">
                                                    <input name="add_libelleGabarit" type="text" class="form-control"
                                                           id="add_libelleGabarit" placeholder="Libelle du gabarit">
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <button type="submit" name="action" value="addGabarit"
                                                        class="btn btn-primary">
                                                    Ajouter
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%-- Onglet permettant de modifier un Gabarit --%>
                        <div class="container">
                            <div class="row" style="margin-top: 20px;">
                                <div class="col-lg-6 info_trajet">
                                    <div class="col-lg-12" style="background-color: #f2f2f2;">
                                        <h3 style="text-align:center;">Modifier un Gabarit</h3>
                                        <form class="form-horizontal"
                                              action="<c:url value="/mps/GestionAdministrateur" />">
                                            <div class="form-group">
                                                <div class="col-md-4">
                                                    <select id="modifGabarit" name="modifGabarit" class="form-control">
                                                        <c:forEach var="unGabarit" items="${listeGabarit}">
                                                            <option value="<c:out value="${unGabarit.id}"/>"><c:out
                                                                    value="${unGabarit.libelle}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-md-4">
                                                    <p>remplacer par </p>
                                                    </br>
                                                    <input name="modifier_libelleGabarit" type="text"
                                                           class="form-control"
                                                           id="modifier_libelleGabarit"
                                                           placeholder="Libelle du gabarit">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <button type="submit" name="action" value="modifierGabarit"
                                                        class="btn btn-info">
                                                    Modifier
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%-- Onglet permettant de supprimer un Gabarit --%>
                        <div class="container">
                            <div class="row" style="margin-top: 20px;">
                                <div class="col-lg-6 info_trajet">
                                    <div class="col-lg-12" style="background-color: #f2f2f2;">
                                        <h3 style="text-align:center;">Supprimer un Gabarit</h3>
                                    </div>
                                    <form class="form-horizontal" action="<c:url value="/mps/GestionAdministrateur" />">
                                        <div class="form-group">
                                            <div class="col-md-4">
                                                <select id="delGabarit" name="delGabarit" class="form-control">
                                                    <c:forEach var="unGabarit" items="${listeGabarit}">
                                                        <option value="<c:out value="${unGabarit.id}"/>"><c:out
                                                                value="${unGabarit.libelle}"/></option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <button type="submit" name="action" value="supprimerGabarit"
                                                    class="btn btn-danger">
                                                Supprimer
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%-- Onglet permettant de gérer les Villes --%>
    <div role="tabpanel" class="tab-pane" id="ville">
        <h3 style="text-align: center;">Villes</h3>
        <div class="container">
            <div class="row" style="margin-top: 20px;">
                <div class="col-lg-offset-3 col-lg-6">
                    <div class="row bloc_Ville">
                        <%-- Onglet permettant d'ajouter une Ville --%>
                        <div class="container">
                            <div class="row" style="margin-top: 20px;">
                                <div class="col-lg-6 info_trajet">
                                    <div class="col-lg-12" style="background-color: #f2f2f2;">
                                        <h3 style="text-align:center;">Ajout d'une Ville</h3>
                                        <form class="form-horizontal"
                                              action="<c:url value="/mps/GestionAdministrateur" />">
                                            <div class="form-group">
                                                <div class="col-md-4">
                                                    <input name="add_nomVille" type="text" class="form-control"
                                                           id="add_nomVille" placeholder="Nom de la ville">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <button type="submit" name="action" value="addVille"
                                                        class="btn btn-primary">
                                                    Ajouter
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%-- Onglet permettant de modifier une Ville --%>
                        <div class="container">
                            <div class="row" style="margin-top: 20px;">
                                <div class="col-lg-6 info_trajet">
                                    <div class="col-lg-12" style="background-color: #f2f2f2;">
                                        <h3 style="text-align:center;">Modifier une Ville</h3>
                                        <form class="form-horizontal"
                                              action="<c:url value="/mps/GestionAdministrateur" />">
                                            <div class="form-group">
                                                <div class="col-md-4">
                                                    <select id="modifVille" name="modifVille" class="form-control">
                                                        <c:forEach var="uneVille" items="${listeVille}">
                                                            <option value="<c:out value="${uneVille.id}"/>"><c:out
                                                                    value="${uneVille.nom}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-md-4">
                                                    <p>remplacer par </p>
                                                    </br>
                                                    <input name="modifier_nomVille" type="text" class="form-control"
                                                           id="modifier_nomVille" placeholder="Nom de la ville">
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <button type="submit" name="action" value="modifierVille"
                                                        class="btn btn-info">
                                                    Modifier
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%-- Onglet permettant de supprimer une Ville --%>
                        <div class="container">
                            <div class="row" style="margin-top: 20px;">
                                <div class="col-lg-6 info_trajet">
                                    <div class="col-lg-12" style="background-color: #f2f2f2;">
                                        <h3 style="text-align:center;">Supprimer une Ville</h3>
                                        <form class="form-horizontal"
                                              action="<c:url value="/mps/GestionAdministrateur" />">
                                            <div class="form-group">
                                                <div class="col-md-4">
                                                    <select id="delVille" name="delVille" class="form-control">
                                                        <c:forEach var="uneVille" items="${listeVille}">
                                                            <option value="<c:out value="${uneVille.id}"/>"><c:out
                                                                    value="${uneVille.nom}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <button type="submit" name="action" value="supprimerVille"
                                                        class="btn btn-danger">
                                                    Supprimer
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="../inclusion/pied_de_page.jsp" %>
</body>
</html>
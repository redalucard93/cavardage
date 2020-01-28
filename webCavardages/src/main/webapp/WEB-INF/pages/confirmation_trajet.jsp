<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%-- Inclusion de l'en-tete --%>
<%@ include file="../inclusion/en_tete.jsp" %>
<body>
<%-- Inclusion de la barre du menu avec les formulaire de connexion et d'inscription --%>
<%@ include file="../inclusion/barre_menu.jsp" %>
<%-- Contenu de la page "confirmer un trajet" --%>
<div>
    <div class="container">
        <div class="row" style="margin-bottom: 20px;">
            <form class="form-horizontal" action="<c:url value="/mps/ReserverTrajet"/>">
                <c:set var="villeDepart" value="${villeDepart}"/>
                <c:set var="heureDepart" value="${heureDepart}"/>
                <c:set var="villeArrivee" value="${villeArrivee}"/>
                <c:set var="heureArrivee" value="${heureArrivee}"/>
                <c:set var="nbPassagers" value="${nbPassagers}"/>
                <c:set var="prix" value="${prix}"/>
                <div class="col-lg-8 bloc_confirmation">
                    <h3>Confirmez votre Réservation</h3>
                    <p>Ville Départ: <c:out value="${villeDepart}"/></p>
                    <p>Heure Départ: <c:out value="${heureDepart}"/></p>
                    <p>Ville Arrivée: <c:out value="${villeArrivee}"/></p>
                    <p>Heure Arrivée: <c:out value="${heureArrivee}"/></p>
                    <p>Nombre de voyageur: <c:out value="${nbPassagers}"/></p>
                    <p>Prix: <c:out value="${prix}"/></p>
                    <%-- Confirmer --%>
                    <button name="action" value="confirmerReservation" type="submit" class="btn btn-primary">
                        <a href="<c:url value="/mps/ReserverTrajet" />">Confirmer la Réservation</a>
                    </button>
                    <%-- Annuler --%>
                    <button type="button" class="btn btn-primary">
                        <a class="navbar-brand" href="<c:url value="/mps/RedirigerAccueilServlet" />">Retour à
                            l'accueil</a>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<%-- Inclusion du pied de page --%>
<%@ include file="../inclusion/pied_de_page.jsp" %>
</body>
</html>

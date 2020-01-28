<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%-- Inclusion de l'en-tete --%>
<%@ include file="../inclusion/en_tete.jsp" %>
<body>
<%-- Inclusion de la barre du menu avec les formulaire de connexion et d'inscription --%>
<%@ include file="../inclusion/barre_menu.jsp" %>
<%-- Contenu de la page "récapitulatif de reservation" --%>
<div>
    <div class="container">
        <div class="row" style="margin-bottom: 20px;">
            <div class="col-lg-2">
            </div>
            <c:set var="villeDepart" value="${villeDepart}"/>
            <c:set var="heureDepart" value="${heureDepart}"/>
            <c:set var="villeArrivee" value="${villeArrivee}"/>
            <c:set var="heureArrivee" value="${heureArrivee}"/>
            <c:set var="nbPassagers" value="${nbPassagers}"/>
            <c:set var="prix" value="${prix}"/>
            <div class="col-lg-8 bloc_recap">
                <h3>Récapitulatif de Réservation</h3>
                <p>Départ de ${villeDepart} le [Date] à ${heureDepart}</p>
                <p>Arrivée à ${villeArrivee} prévue vers ${heureArrivee}</p>
                <p>Nb de passagers: ${nbPassagers}</p>
                <p>Prix: ${prix}</p>
                <%--<p>Places restantes: [nbPlacesRestantes]${trajet}</p>--%>
                <button type="button" class="btn btn-secondary">
                    <a class="navbar-brand" href="<c:url value="/mps/RedirigerAccueilServlet" />">Retour à l'accueil</a>
                </button>
            </div>
        </div>
    </div>
</div>
<%-- Inclusion du pied de page --%>
<%@ include file="../inclusion/pied_de_page.jsp" %>
</body>
</html>

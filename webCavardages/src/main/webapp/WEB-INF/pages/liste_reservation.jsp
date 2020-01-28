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

            <c:forEach var="uneResa" items="${listeReservation}">
                <c:if test="${uneResa.etat == 'ATTENTE'}">
                    Réservation de <c:out value="${uneResa.demandeur.nom}"/> <c:out
                        value="${uneResa.demandeur.prenom}" />

                    <a href="<c:url value="/mps/gestion_reservation" />?idReservation=<c:out value="${uneResa.id}"/>&action=accepte">
                        <button class="btn btn-success" style="width: 150px; margin-bottom: 5px;">Accepter</button>
                    </a>
                    <a href="<c:url value="/mps/gestion_reservation" />?idReservation=<c:out value="${uneResa.id}"/>&action=refuse">
                        <button class="btn btn-danger" style="width: 150px; margin-bottom: 5px;">Refuser</button>
                    </a>
                </c:if>
            </c:forEach>

        </div>
    </div>
</div>

<%@ include file="../inclusion/pied_de_page.jsp" %>


</body>
</html>
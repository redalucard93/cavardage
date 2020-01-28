<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<%-- Inclusion de l'en-tete --%>
<%@ include file="../inclusion/en_tete.jsp" %>


<body>

<%-- Inclusion de la barre du menu avec les formulaire de connexion et d'inscription --%>
<%@ include file="../inclusion/barre_menu.jsp" %>


<%-- Contenu de la page "Détails d'un trajet" --%>
<div>
    <div class="container">

        <div class="row" style="margin-bottom: 20px;">
            <div class="col-lg-2">
            </div>

            <div class="col-lg-8 bloc_detail">
                <h3>Détails conducteur</h3>
                <p>Pilotes de F1, roule en porsche et aime les bonne jantes chromé.</p>
                <p>Toto T</p>
                <p>25 ans</p>
                <p><img src="assets/images/star.png" width="18" height="18"/> 4/5</p>
            </div>
        </div>

        <div class="row" style="margin-bottom: 20px;">
            <div class="col-lg-2">
            </div>

            <div class="col-lg-8 bloc_detail" >
                <h3>Détails trajet</h3>
                <p>10 euros par personnes</p>
                <p>2 places restantes</p>
                <button type="button" class="btn btn-primary">Réserver</button>

            </div>
        </div>

        <div class="row" style="margin-bottom: 20px;">
            <div class="col-lg-2">
            </div>

            <div class="col-lg-8 bloc_detail">
                <h3>Détails des étapes</h3>


            </div>
        </div>



    </div>
</div>


<%-- Inclusion du pied de page --%>
<%@ include file="../inclusion/pied_de_page.jsp" %>

</body>
</html>

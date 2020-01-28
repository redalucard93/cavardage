<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer>

    <div class="container">
        <div class="row">
            <div class="col-lg-3">

            </div>
            <div class="col-lg-6" style="text-align: center;">

                <%-- On affiche se pied de page lorsque l'utilisateur n'est pas connecté --%>
                <c:if test="${loginSession != null}">
                    <a href="<c:url value="/mps/ConsulterProfilUtilisateur" />" class="aFooter">Profil</a> |
                    <a href="<c:url value="/mps/deconnexion" />" class="aFooter">Déconnexion</a>
                </c:if>

                <%-- On affiche se pied de page lorsque l'utilisateur est pas connecté --%>
                <c:if test="${loginSession == null}">
                    <a data-toggle="modal" data-target="#connexion" class="aFooter">Connexion</a> |
                    <a data-toggle="modal" data-target="#inscription" class="aFooter">Inscription</a>
                </c:if>

            </div>
            <div class="col-lg-3">

            </div>
        </div>


    </div>


    <div style="margin-top: 25px;">
        <p style="text-align: center;">© 2017 cavardages.fr</p>
    </div>
</footer>

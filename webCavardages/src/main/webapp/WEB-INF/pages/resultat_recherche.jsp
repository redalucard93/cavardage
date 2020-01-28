<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<p class="error"><c:out value="${messageErreur}"/></p>

<div class="row" style=" margin-top: 15px; margin-bottom: 20px;">
    <div class="col-lg-2">
    </div>

<c:set var="trajetsPrix" value="${trajetsPrix}"/>

<c:forEach items="${trajetsNotes}" var="entry">
    <c:if test="${entry.key.nbrPLacesDisponibles > 0}">

        <div class="col-lg-8 bloc_trajet">
        <div>
            <div class="col-lg-3 profil_trajet">
                <center>
                    <img src="../assets/images/icon_profil.png" width="80" height="80" />
                </center>
                <p style="text-align: center;">${entry.key.conducteur.nom} ${entry.key.conducteur.prenom}</p>
                <!--    <p style="text-align: center;">25 ans</p>-->
                <p style="text-align: center;">
                    <img src="../assets/images/star.png" width="18" height="18" /> <em>${entry.value} - </em>
                </p>
                </div>
                <div class="col-lg-5 info_trajet">
                <h4> <c:out value="${entry.key.heureDepart.getDayOfMonth()}"/> /
                    <c:out value="${entry.key.heureDepart.getMonthValue()}"/> /
                    <c:out value="${entry.key.heureDepart.getYear()}"/> à
                    <c:out value="${entry.key.heureDepart.getHour()}"/>h
                    <c:out value="${entry.key.heureDepart.getMinute()}"/>
                </h4>
                <p>
                    <img src="../assets/images/start_flag.png" width="18" height="18" /> ${entry.key.villeDepart.nom}
                </p>
                <p>
                    <img src="../assets/images/end_flag.png" width="18" height="18" />${villeArrive}
                </p>
               <c:if test="${entry.key.etapes[fn:length(entry.key.etapes)-1].villeArrivee.nom != villeArrive}">
                <p>
                    <img src="../assets/images/end_flag.png" width="18" height="18" />${entry.key.etapes[fn:length(entry.key.etapes)-1].villeArrivee.nom}
                </p>
               </c:if>
                <p>Prix : ${trajetsPrix[entry.key]} € par place</p>
                <p>Nombre de place : ${entry.key.nbrPLacesDisponibles}</p>

                    </div>

                    <div class="col-lg-4" style="text-align: right; margin-top: 135px;">
                        <button name="action" data-toggle="modal" data-target="#reservation" class="btn btn-primary">Réserver</button>

                        <button class="btn btn-warning" value="" onclick="chercherDetaille('${entry.key.conducteur.login}')" width="100">Plus de détails</button>
                        <div id="detailles"></div>
                        <br>

                    </div>
                </div>
            </div>

        <div class="modal fade" id="reservation">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action = "<c:url value="/mps/ReserverTrajet" />" method="POST">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <h3 style="text-align:center;">Reserver</h3>
                                </div>
                                <div class="col-lg-12" style="margin-top: 20px;">

                                    <input type="hidden" name="idTrajet" value="${entry.key.id}">
                                    <input type="hidden" name="idVilleArrive" value="${idVilleArrive}">
                                    <div class="form-group">
                                        <label>Nombre de place à réserver : </label>
                                        <select class="form-control mb-2 mr-sm-2 mb-sm-0" id="nbplace" name="nbrPLacesDisponibles" placeholder="places">
                                            <c:forEach begin="0" end="${entry.key.nbrPLacesDisponibles-1}" step="1" varStatus="loop">
                                                <option value="${loop.count}">
                                                   <c:out value="${loop.count}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <textarea id="message" name="message"  cols="30" rows="5"
                                        placeholder="Message pour votre réservation"></textarea>
                                    </div>


                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                            <button name="action" value="reserver" class="btn btn-primary">Reserver</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </c:if>
        </c:forEach>

</div>

<script>
    function chercherDetaille(conducteurLogin) {

        $.ajax({
            method: "GET",
            url: "/webCavardages/mps/RechercherAvis",
            data: {
                login : conducteurLogin

            },
            success: function (results) {
                $(this).next().load(results);
            },

            error: function (response) {


            }
        });
    }
</script>

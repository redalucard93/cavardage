<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:forEach items="${listeReservation}" var="reservation">
    <div class="row bloc_trajet_reserver">
        <div class="col-lg-6 info_trajet">
            <h3></h3>
            <p>
                <img src="../assets/images/start_flag.png" width="18" height="18"/> Départ : ${reservation.etape.trajet.villeDepart.nom}
            </p>
            <p>
                <img src="../assets/images/end_flag.png" width="18" height="18"/> Arrivé : ${reservation.etape.villeArrivee.nom}
            </p>
            <p>prix : ${reservation.etape.prix}</p>
            <p>${reservation.etape.trajet.nbrPLacesDisponibles} places restantes</p>
            <p>Date : <c:out value="${reservation.etape.trajet.heureDepart.getDayOfMonth()}"/> /
                <c:out value="${reservation.etape.trajet.heureDepart.getMonthValue()}"/> /
                <c:out value="${reservation.etape.trajet.heureDepart.getYear()}"/> à
                <c:out value="${reservation.etape.trajet.heureDepart.getHour()}"/> :
                <c:out value="${reservation.etape.trajet.heureDepart.getMinute()}"/>
            </p>
            <p>
                Etat :
                <c:if test="${reservation.etat =='ATTENTE'}">
                    en attente
                </c:if>
                <c:if test="${reservation.etat =='ACCEPTEE'}">
                    acceptée
                </c:if>
            </p>

        </div>

            <div class="col-lg-6" style="text-align: right; margin-top: 10px;">
            <button type="submit" class="btn btn-info" style="width: 150px; margin-bottom: 5px;">Plus de
                détails
            </button>
            <c:if test="${reservation.etat != 'ACCEPTEE'}">
                <button type="submit" class="annuler btn btn-danger" value="${reservation.id}" style="width: 150px;">
                    Annuler réservation
                </button>
            </c:if>

            <c:if test="${reservation.etat == 'ACCEPTEE'}">
            <button type="submit" class="btn btn-info" style="width: 150px; margin-bottom: 5px;"  data-toggle="modal" data-target="#avis">
                Laisser un avis
            </button>
            </c:if>

        </div>
    </div>



    <div class="modal fade" id="avis">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action = "<c:url value="/mps/LaisserAvis" />" method="POST">
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <h3 style="text-align:center;">Laisser avis</h3>
                            </div>
                            <div class="col-lg-12" style="margin-top: 20px;">

                                <div class="form-group">
                                    <input type="hidden" name="idReservation" value="${reservation.id}">
                                    <label>Notes : </label>
                                    <select class="form-control mb-2 mr-sm-2 mb-sm-0" id="note" name="note" placeholder="places">
                                        <option value="1">
                                            1
                                        </option>
                                        <option value="2">
                                            2
                                        </option>
                                        <option value="3">
                                            3
                                        </option>
                                        <option value="4">
                                            4
                                        </option>
                                        <option value="5">
                                            5
                                        </option>
                                    </select>
                                    <label> Votre avis :</label><br>
                                    <textarea name="message" cols="30" rows="10"></textarea>
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

</c:forEach>
<script>
    $(".annuler").click(function () {
        $(this).parents(':eq(2)').css("display","none");

        $.ajax({
            method: "GET",
            url: "/webCavardages/mps/AnnulerReservation",
            data: {
                idReservation: $(this).val()
            },
            success: function (results) {

            },

            error: function (response) {


            }
        });


    });



</script>
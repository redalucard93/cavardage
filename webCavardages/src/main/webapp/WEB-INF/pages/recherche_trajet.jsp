<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<%-- Inclusion de l'en-tete --%>
<%@ include file="../inclusion/en_tete.jsp" %>


<body>

<%-- Inclusion de la barre du menu avec les formulaire de connexion et d'inscription --%>
<%@ include file="../inclusion/barre_menu.jsp" %>

<div>
    <div class="container">

        <div class="row">

                <div class="col-lg-2">
                </div>

                <div class="col-lg-9">


                    <label class="sr-only" for="depart">Départ</label>
                    <input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0 suggest" id="depart" name="depart"
                           placeholder="Départ">

                    <label class="sr-only" for="destination">Destination</label>
                    <input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0 suggest" id="destination"
                           name="destination" placeholder="Destination">

                    <label class="sr-only" for="dateTrajet">Date</label>
                    <input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" id="dateTrajet" name="date"
                           placeholder="07/12/2017" required>

                    <label class="sr-only" for="heureTrajet">Heure</label>
                    <select class="form-control mb-2 mr-sm-2 mb-sm-0" id="heureTrajet" name="heure">
                        <c:forEach begin="0" end="23" step="1" varStatus="loop">

                            <option value="loop.index">
                                <c:if test="${loop.index < 9}">0</c:if><c:out value="${loop.count}"/>:00
                            </option>
                            <c:if test="${loop.index == 24}">
                                <option value="6.00">00</option>
                            </c:if>
                        </c:forEach>
                    </select>


                    <button type="submit" name="action" value="rechercher" onclick="rechercher()" class="btn btn-success">Lancer une nouvelle recherche
                    </button>

                </div>

        </div>
        <p class="error"><c:out value ="${messageErreurReservation}"/></p>

        <div class="row" style=" margin-top: 15px;">
            <div class="col-lg-12">
                <h4 style="text-align: center;">Résultat de votre recherche ...</h4>
            </div>

        </div>
        <div id="result"></div>
    </div>
</div>

<%@ include file="../inclusion/pied_de_page.jsp" %>

</body>
<script>

    $(document).ready($(function () {
            var availableTags = [
                <c:forEach items="${villes}" var="v" varStatus="status">
                '${v}'
                <c:if test="${!status.last}">
                ,
                </c:if>
                </c:forEach>
            ];
            var NoResultsLabel = "No Results";

            $(".suggest").autocomplete({
                source: function (request, response) {
                    var results = $.ui.autocomplete.filter(availableTags, request.term);

                    if (!results.length) {
                        results = [NoResultsLabel];
                    }

                    response(results);
                },
                select: function (event, ui) {
                    if (ui.item.label === NoResultsLabel) {
                        event.preventDefault();
                    }
                },
                focus: function (event, ui) {
                    if (ui.item.label === NoResultsLabel) {
                        event.preventDefault();
                    }
                }
            });
        }
    ));

    function rechercher() {
        var villDep = $("#depart").val(),
            villeDest = $("#destination").val(),
            date = $("#dateTrajet").val(),
            heure = $("#heureTrajet option:selected").text();

        console.log(date);
        $.ajax({
            method: "GET",
            url: "/webCavardages/mps/RechercheTrajet",
            data: {
                villeDepart: villDep,
                villeDestination: villeDest,
                dateTrajet: date,
                heureTrajet: heure
            },
            success: function (results) {
               $("#result").load(results);
            },

            error: function (response) {


            }
        });
    }


</script>
<style>
    .error{
        color: red;
    }
    .ui-helper-hidden-accessible {
        border: 0;
        clip: rect(0 0 0 0);
        height: 1px;
        margin: -1px;
        overflow: hidden;
        padding: 0;
        position: absolute;
        width: 1px;
    }
</style>
</html>
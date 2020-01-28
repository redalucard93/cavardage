<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<%-- Inclusion de l'en-tete --%>
<%@ include file="../inclusion/en_tete.jsp" %>


<body>


<%-- Inclusion de la barre du menu avec les formulaire de connexion et d'inscription --%>
<%@ include file="../inclusion/barre_menu.jsp" %>



<p class="error">${messageErreur}</p>
<div class="gray_color">
    <center>
        <img style="margin-top: 20px; margin-bottom: 20px;" src="../assets/images/voiture.png">
    </center>
</div>


<div class="presentation">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <h2 style="text-align: center;">Rechercher un trajet</h2>
            </div>
        </div>
        <div class="row" style="margin-top: 15px;">

            <div class="form-group">
                <label for="villeDepart">Ville de départ</label>
                <select id="villeDepart" name="villeDepart" class="form-control">
                    <c:forEach var="uneVille" items="${requestScope.villes}">
                        <option value="<c:out value="${uneVille.nom}"></c:out>">
                            <c:out value="${uneVille.nom}"></c:out>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label class="control-label" for="villeDestination">Ville de déstination</label>
                <select id="villeDestination" name="villeDestination" class="form-control">
                    <c:forEach var="uneVille" items="${requestScope.villes}">
                        <option value="<c:out value="${uneVille.nom}"></c:out>">
                            <c:out value="${uneVille.nom}"></c:out>
                        </option>
                    </c:forEach>
                </select>
            </div>


            <div class="form-group">
                <label class="control-label" for="dateTrajet"> Date de départ </label>
                <div class='input-group date' id='dateTrajetDiv'>
                    <input type='text' class="form-control" name="dateTrajet" id="dateTrajet"
                    >
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
                <p class="error" id="erreurDateTrajet"></p>
            </div>


            <div class="form-group">
                <label class="control-label" for="heureTrajet">Heure de départ</label>
                <select class="form-control" id="heureTrajet" name="heureTrajet">
                    <c:forEach begin="0" end="23" step="1" varStatus="loop">
                        <option value="loop.index">
                            <c:if test="${loop.index <= 9}">0</c:if><c:out value="${loop.index}"/>:00
                        </option>
                        <c:if test="${loop.index == 24}">
                            <option value="6.00">00</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>

            <div class="col-md-12 text-center">
                <button type="submit" onclick="rechercher()" class="btn btn-success text-center" name="pages" value="recherche_trajet">Lancer la recherche</button>
            </div>
        </div>
        <div id="result"></div>
    </div>
</div>




<%@ include file="../inclusion/pied_de_page.jsp" %>


</body>
<script>
    function rechercher() {
        var villDep = $("#villeDepart").val(),
            villeDest = $("#villeDestination").val(),
            dateTrajet = $("#dateTrajet").val(),
            heureTrajet = $("#heureTrajet option:selected").text();
        console.log(villDep);
        console.log(villeDest);
        console.log(dateTrajet);
        console.log(heureTrajet);

        $.ajax({
            method: "GET",
            url: "/webCavardages/mps/RechercheTrajet",
            data: {
                villeDepart: villDep,
                villeDestination: villeDest,
                dateTrajet: dateTrajet,
                heureTrajet: heureTrajet
            },
            success: function (results) {
                $("#result").load(results);
            },

            error: function (response) {


            }
        });
    }

    $(function () {
        var bindDatePicker = function() {
            $(".dateTrajet").datetimepicker({
                format:'DD/MM/YYYY',
                icons: {
                    date: "fa fa-calendar",
                    up: "fa fa-arrow-up",
                    down: "fa fa-arrow-down"
                },
                locale: "fr"
            }).find('input:first').on("blur",function () {

                var date = parseDate($(this).val());
                $(this).val(date);
            });
        }



        var parseDate = function(value) {
            var m = value.match(/^(\d{1,2})(\/|-)?(\d{1,2})(\/|-)?(\d{4})$/);
            if (m)
                value = ("00" + m[1]).slice(-2) + '/' + ("00" + m[3]).slice(-2) + '/' + m[5];
            console.log(value);
            return value;
        }

        bindDatePicker();
    });

</script>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${loginSession != null}">

    <%-- Barre de menu se trouvant en haut du site web --%>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand" href="<c:url value="/mps/RedirigerAccueilServlet" />">Cavardages</a>
            </div>

            <ul class="nav navbar-nav navbar-right" style="margin-top: 6px; margin-right: 5px;">
                <c:if test="${isAdmin == true}">
                    <a href="<c:url value="/mps/ConsulterProfilAdministrateur" />">
                        <button class="btn btn-default">Admin</button>
                    </a>
                </c:if>
                <a href="<c:url value="/mps/ConsulterProfilUtilisateur" />">
                    <button class="btn btn-default">Espace</button>
                </a>
                <a href="<c:url value="/mps/deconnexion" />">
                    <button class="btn btn-danger">Déconnexion</button>
                </a>
                <a href="<c:url value="/mps/RedirigerRechercheTrajet" />">
                    <button class="btn btn-danger">Rechercher trajet</button>
                </a>
            </ul>
        </div>
    </nav>

</c:if>

<c:if test="${loginSession == null}">

    <%-- Barre de menu se trouvant en haut du site web --%>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">

            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand" href="<c:url value="/mps/RedirigerAccueilServlet" />">Cavardages</a>
            </div>


            <ul class="nav navbar-nav navbar-right" style="margin-top: 6px; margin-right: 5px;">
                <button class="btn btn-primary" data-toggle="modal" data-target="#connexion">Connexion</button>
                <button class="btn btn-primary" data-toggle="modal" data-target="#inscription">Inscription</button>
            </ul>

        </div>
    </nav>


    <%-- Formulaire de connexion --%>
    <div class="modal fade" id="connexion">
        <div class="modal-dialog">
            <div class="modal-content">


                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <h3 style="text-align:center;">Connexion</h3>
                        </div>
                        <div class="col-lg-12" style="margin-top: 20px;">

                            <div class="form-group">
                                <input name="login" type="email" class="form-control" id="email"
                                       placeholder="Email" required>
                                <p class="error"></p>
                            </div>
                            <div class="form-group">
                                <input name="password" type="password" class="form-control"
                                       id="exampleInputPassword1"
                                       placeholder="Mot de passe" required>
                                <p class="error"></p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                    <button name="action" onclick="validation('ConnectionServlet')" value="connexion"
                            class="btn btn-primary">Connexion
                    </button>
                </div>
            </div>
        </div>
    </div>


    <%-- Formulaire d'inscription --%>
    <div class="modal fade" id="inscription">
        <div class="modal-dialog">
            <div class="modal-content">


                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <h3 style="text-align:center;">Inscription</h3>
                        </div>
                        <div class="col-lg-12" style="margin-top: 20px;">

                            <div class="form-group">
                                <input name="lastName" type="text" class="form-control" id="lastName"
                                       placeholder="Nom">
                                <p class="error"></p>
                            </div>

                            <div class="form-group">
                                <input name="firstName" type="text" class="form-control" id="firstName"
                                       placeholder="Prénom">
                                <p class="error"></p>
                            </div>
                            <div class="form-group">
                                <div class='input-group date' id='dateDiv'>
                                    <input type='text' class="form-control" name="date" id="date"
                                        placeholder="Date de naissance (jj/mm/aaaa)"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                  </span>
                                </div>
                                <p class="error" id="erreurDate"></p>
                            </div>
                            <div class="form-group">
                                <input name="login" type="email" class="form-control" id="mail" placeholder="Email">
                                <p class="error"></p>
                            </div>

                            <div class="form-group">
                                <input name="password" type="password" class="form-control" id="mdp"
                                       placeholder="Mot de passe">
                                <p class="error"></p>
                            </div>

                            <div class="form-group">
                                <input type="password" class="form-control" id="mdp2"
                                       placeholder="Confirmation du mot de passe">
                                <p class="error"></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                    <button name="action" onclick="validation('InscriptionServlet')" id="submit" value="inscription"
                            class="btn btn-primary">Inscription
                    </button>
                </div>

            </div>
        </div>
    </div>

</c:if>

<script type="text/javascript" language="javascript">

    function validation(urlParamater) {
        var url = "/webCavardages/mps/" + urlParamater;
        $(".error").each(function (url) {
            $(this).text("");
        });
        var message = "";
        var valide = true;
        var valideConnection = true;

        if (urlParamater != "ConnectionServlet") {

            var lastname = $("#lastName").val();
            var firstName = $("#firstName").val();
            var date = $("#date").val();
            var login = $("#mail").val();
            var password = $("#mdp").val();
            var passwordConfirm = $("#mdp2").val();


            if (lastname == "") {
                message = " Votre nom doit être rempli";
                $("#lastName").next().text(message);
                valide = false;
            }
            if (firstName == "") {
                message = "Votre prénom doit être rempli";
                $("#firstName").next().text(message);
                valide = false;
            }
            if (login == "") {
                message = "Votre mail doit être rempli";
                $("#mail").next().text(message);
                valide = false;
            }
            if (password == "") {
                message = "Votre mot de passe doit être rempli";
                $("#mdp").next().text(message);
                valide = false;
            }
            if (date === "") {
                message = "Votre date de naissance doit être remplie";
                $("#erreurDate").text(message);
                valide = false;
            }

            if (password != passwordConfirm) {
                message = "Les mots de passes ne correspondent pas";
                $("#mdp2").next().text(message);
                valide = false;
            }


            if (valide == true) {

                $.ajax({
                    method: "POST",
                    url: url,
                    data: {
                        lastName: $("#lastName").val(),
                        firstName: $("#firstName").val(),
                        date: $("#date").val(),
                        login: $("#mail").val(),
                        password: $("#mdp").val()
                    },
                    success: function (results) {

                        window.location.href = results;
                    },
                    error: function (response) {


                    }
                });
            }

        }
        else {
            var loginConnection = $("#email").val();
            var passwordConnection = $("#exampleInputPassword1").val();

            if (loginConnection == "") {
                message = "Votre mail doit être rempli";
                $("#email").next().text(message);
                valideConnection = false;
            }
            if (passwordConnection == "") {
                message = "Votre mot de passe doit être rempli";
                $("#exampleInputPassword1").next().text(message);
                valideConnection = false;
            }
            if (valideConnection) {
                $.ajax({
                    method: "POST",
                    url: url,
                    data: {
                        login: loginConnection,
                        password: passwordConnection
                    },
                    success: function (results) {

                        window.location.href = results;
                    },
                    error: function (response) {


                    }
                });
            }
        }
    }

    $(function () {
        var bindDatePicker = function() {
            $(".date").datetimepicker({
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
            console.log(m);
            if (m)
                value = ("00" + m[1]).slice(-2) + '/' + ("00" + m[3]).slice(-2) + '/' + m[5];
            console.log(value);
            return value;
        }

        bindDatePicker();
    });

</script>
<style>
    .error {
        color: red;
    }
</style>
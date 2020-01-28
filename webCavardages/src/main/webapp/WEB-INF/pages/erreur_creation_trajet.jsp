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

            <p><c:out value="${messageErreur}"></c:out></p>

        </div>
    </div>
</div>

<%@ include file="../inclusion/pied_de_page.jsp" %>


</body>
</html>
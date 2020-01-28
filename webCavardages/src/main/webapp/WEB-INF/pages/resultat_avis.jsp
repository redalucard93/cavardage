<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${avis}" var="a">

        <div class="col-lg-8 bloc_trajet">
            <table>
                <tr> <td>prenom : </td><td>${a.emetteur.prenom}</td></tr>
                <tr> <td>Avis :</td><td>${a.emetteur.message}</td></tr>
            </table>
        </div>

</c:forEach>

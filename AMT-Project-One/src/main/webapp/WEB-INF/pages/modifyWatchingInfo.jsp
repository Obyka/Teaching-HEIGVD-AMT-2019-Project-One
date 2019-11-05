<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>AMT Project One</title>

    <!-- Custom fonts for this template-->
    <link href="../assets/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../assets/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

<div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4">Modifier une Watching Info</h1>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Information sur la Serie</h6>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">${serie.title}</h5>
                                <p class="card-text">${serie.synopsis}</p>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Information sur le Viewer</h6>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">${viewer.username}</h5>
                                <p class="card-text">${viewer.firstname} ${viewer.lastname}</p>
                            </div>
                        </div>
                        <form class="user" method="POST" action="./modifywatchinginfo">
                            <div class="form-group">
                                <label>Temps de visionnage (min)</label>
                                <input type="text" name="timespent" class="form-control form-control-user" placeholder="Time spent" value="${watchingInfo.timeSpent}" required="true">
                            </div>
                            <div class="form-group">
                                <label>Date du premier visionnage</label>
                                <input type="date" name="beginningdate" class="form-control" placeholder="Beginning date" value="${watchingInfo.beginningDate}" required="true">
                            </div>
                            <input type="hidden" name="idserie" value="${watchingInfo.idSerie}" required="true">
                            <input type="hidden" name="idviewer" value="${watchingInfo.idViewer}" required="true">
                            <input type="submit" class="btn btn-primary btn-user btn-block" value="Modifier"/>
                        </form>
                        <hr>
                        <div class="text-center">
                            <a class="small" href="${backToWebsite}">Retour</a>
                        </div>
                        <c:if test="${errors != null}">
                            <hr>
                            Erreurs:
                            <ul>
                                <c:forEach items="${errors}" var="error">
                                    <li>${error}</li>
                                </c:forEach>
                            </ul>
                        </c:if>
                        <c:if test="${stateOfCreation != null}">
                            <p>${stateOfCreation}</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="../assets/vendor/jquery/jquery.min.js"></script>
<script src="../assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="../assets/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="../assets/js/sb-admin-2.min.js"></script>

</body>

</html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.min.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css"
          integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA=="
          crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js"
            integrity="sha512-nMMmRyTVoLYqjP9hrbed9S+FzjZHW5gY1TWCHA5ckwXZBadntCNs8kEqAWdrb9O7rxbCaA4lKTIWjDXZxflOcA=="
            crossorigin=""></script>
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/common.css">

    <title>Stations</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'station-tab' ? 'active' : ''} text-style" id="station-tab">
        <div class="row">
            <div class="col-sm-11">
                <div class="row">
                    <div class="col-sm-6">
                        <div id="mapid" style="width: 400px; height: 600px;"></div>
                    </div>
                    <div class="col-sm-6">
                        <form:form name="stationForm" id="stationForm" class="form-group"
                                   modelAttribute="stationForm"
                                   role="form" data-toggle="validator">
                            <form:input path="id" id="idForm" type="hidden"></form:input>
                            <spring:bind path="stationName">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <form:input type="text"
                                                id="stationName" placeholder="New Station"
                                                class="form-control"
                                                path="stationName" required="required"/>
                                    <form:errors path="stationName"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="latitude">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <form:input type="number" step="any" min="-180" max="180"
                                                id="latitude" placeholder="Latitude"
                                                class="form-control"
                                                path="latitude" required="required"/>
                                    <form:errors path="latitude"></form:errors>
                                </div>
                            </spring:bind>
                            <spring:bind path="longitude">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <form:input type="number" step="any" min="-90" max="90" id="longitude"
                                                placeholder="Longitude"
                                                class="form-control"
                                                path="longitude" required="required"/>
                                    <form:errors path="longitude"></form:errors>
                                </div>
                            </spring:bind>
                            <button type="submit" id="btnAddStation">Save</button>
                            <button type="button" id="btnClearStation">Clear</button>
                        </form:form>
                        <div class="form-group">
                            <h3>Stations</h3>
                            <div id="stationMessage"></div>
                            <table class="table" id="myTableStations">
                                <thead>
                                <tr>
                                    <th>Station name</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <script id="template" type="text/x-handlebars-template">
                                    {{#each stations}}
                                    <tr>
                                        <td value="{{stationName}}">{{stationName}}</td>
                                        <td>
                                            <input type="hidden" id="idStation-{{@index}}" value="{{id}}">
                                            <button type="button" id="btnChangeStation-{{@index}}"
                                                    onclick="stationEdit('{{@index}}')">
                                                Change
                                            </button>
                                            <button type="button" id="btnDeleteStation-{{@index}}"
                                                    onclick="stationDelete('{{@index}}')">
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                    {{/each}}
                                </script>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${contextPath}/resources/js/stations.js"></script>
<script>
    var contextPath = '${contextPath}';

    var mymap = L.map('mapid').setView([32.08, 34.78], 7);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
            '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
            'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox.streets'
    }).addTo(mymap);

    var theMarker = {};

    function onMapClick(e) {
        if (theMarker != undefined) {
            mymap.removeLayer(theMarker);
        };

        var lat = e.latlng.lat;
        var lng = e.latlng.lng;
        $('#latitude').val(lat);
        $('#longitude').val(lng);

        theMarker = L.marker([lat, lng]).addTo(mymap)
            .bindPopup($('#stationName').val()).openPopup();
    }

    mymap.on('click', onMapClick);
</script>
</body>
</html>

$().ready(function () {
    $.validator.setDefaults({
        highlight: function (element) {
            $(element)
                .closest('.form-group')
                .addClass('has-error')
        },
        unhighlight: function (element) {
            $(element)
                .closest('.form-group')
                .removeClass('has-error')
        }
    });

    $('#stationForm').validate({
        rules: {
            stationName: {
                required: true
            }
        },
        messages: {
            stationName: {
                required: "Please enter stationName",
            }
        }
    });
});

function stationEdit(index) {
    event.preventDefault();

    var station = $("#idStation-" + index).val();

    var object = {stationId: station};

    $.post(contextPath + "/admin/stations?change", object).done(function (result) {
        $('#stationMessage').text('');

        $('form[name=stationForm]').val(result);
        $('#idForm').val(result.id);
        $('#stationName').val(result.stationName);
        $('#latitude').val(result.latitude);
        $('#longitude').val(result.longitude);

        if (theMarker != undefined) {
            mymap.removeLayer(theMarker);
        };

        var latitude = $('#latitude').val();
        var longitude = $('#longitude').val();
        theMarker = L.marker([latitude, longitude]).addTo(mymap)
            .bindPopup($('#stationName').val()).openPopup();
    }).fail(function () {
        $('#stationMessage').text('Edit station failed');
    });
}

function stationDelete(index) {
    event.preventDefault();

    var station = $("#idStation-" + index).val();

    var object = {stationId: station};

    $.post(contextPath + "/admin/stations?delete", object).done(function (result) {
        $('#stationMessage').empty().text(result);
        getStationList();
    }).fail(function () {
        $('#stationMessage').text('Delete station failed');
    });
}

$('#btnAddStation').click(function () {
    event.preventDefault();

    $.post(contextPath + "/admin/stations?save", $('#stationForm').serialize()).done(function (result) {
        $('#stationMessage').empty().text(result);

        if (theMarker != undefined) {
            mymap.removeLayer(theMarker);
        };

        var latitude = $('#latitude').val();
        var longitude = $('#longitude').val();
        theMarker = L.marker([latitude, longitude]).addTo(mymap)
            .bindPopup($('#stationName').val()).openPopup();

        getStationList();
    }).fail(function () {
        $('#stationMessage').text('Add station failed');
    });
})

$('#btnClearStation').click(function () {
    event.preventDefault();
    $('form input[type="text"], form input[type="hidden"], form input[type="number"]').val('');
    if (theMarker != undefined) {
        mymap.removeLayer(theMarker);
        theMarker = undefined;
    };
})

function getStationList() {
    event.preventDefault();

    $.get(contextPath + "/admin/stations?list", {}).done(function (result) {
        var data = {stations: result};
        var template = Handlebars.compile($('#template').html());
        $("#myTableStations tr>td").remove();
        $('.table').append(template(data));
    }).fail(function () {
        $('#stationMessage').text("Get station's list failed");
    });
}

window.onload = getStationList;

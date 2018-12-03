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

    $.post(contextPath+"/admin/stations?change", object).done(function (result) {
        $('#stationMessage').text('');

        for (var list = Object.keys(result), i = list.length, form = document.forms.namedItem('stationForm'); i--;) {
            if (form[list[i]]) {
                form[list[i]].value = result[list[i]];
            }
        }
        // $("#btnChangeStation-"+index).prop("disabled", true);
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

function stationDelete(index) {
    event.preventDefault();

    var station = $("#idStation-" + index).val();

    var object = {stationId: station};

    $.post(contextPath+"/admin/stations?delete", object).done(function (result) {
        $('#stationMessage').empty().text(result);
        getStationList();
    }).fail(function () {
        alert('Delete station failed');
    });
}

$('#btnAddStation').click(function () {
    event.preventDefault();

    $.post(contextPath+"/admin/stations?save", $('#stationForm').serialize()).done(function (result) {
        $('#stationMessage').empty().text(result);
        getStationList();
    }).fail(function (e) {
        alert('Error: ' + JSON.stringify(e));
    });
})

$('#btnClearStation').click(function () {
    event.preventDefault();
    $('form input[type="text"], form input[type="hidden"]').val('');
})

function getStationList() {
    event.preventDefault();

    $.get(contextPath+"/admin/stations?list", {}).done(function (result) {
        var data = {stations: result};
        var template = Handlebars.compile($('#template').html());
        $("#myTableStations tr>td").remove();
        $('.table').append(template(data));
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

window.onload = getStationList;
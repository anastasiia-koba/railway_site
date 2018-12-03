function routEdit(index) {
    event.preventDefault();

    var rout = $("#idRout-" + index).val();

    var object = {routId: rout};

    $.post(contextPath+"/admin/routs?change", object).done(function (result) {
        $('#routMessage').text('');

        $('form[name=routForm]').val(result);
        $('#idForm').val(result.id);
        $('#routName').val(result.routName);
        $('#comboboxStart').val(result.startStation.stationName);
        $('#comboboxEnd').val(result.endStation.stationName);
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

function routDelete(index) {
    event.preventDefault();

    var rout = $("#idRout-" + index).val();

    var object = {routId: rout};

    $.post(contextPath+"/admin/routs?delete", object).done(function (result) {
        $('#routMessage').empty().text(result);
        getRoutList();
    }).fail(function () {
        alert('Delete rout failed');
    });
}

$('#btnAddRout').click(function () {
    event.preventDefault();

    $.post(contextPath+"/admin/routs?save", $('#routForm').serialize()).done(function (result) {
        $('#routMessage').empty().text(result);
        getRoutList();
    }).fail(function (e) {
        alert('Error: ' + JSON.stringify(e));
    });
})

$('#btnClearRout').click(function () {
    event.preventDefault();
    $('form input[type="text"], form input[type="hidden"]').val('');
    $('form[name=routForm]').trigger('reset');
})

function getRoutList() {
    event.preventDefault();

    $.get(contextPath+"/admin/routs?list", {}).done(function (result) {
        var data = {routs: result};
        var template = Handlebars.compile($('#template').html());
        $("#myTableRouts tr>td").remove();
        $('.table').append(template(data));
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

window.onload = getRoutList;
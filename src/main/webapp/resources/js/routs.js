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
    }).fail(function () {
        $('#routMessage').text('Edit rout failed');
    });
}

function routDelete(index) {
    event.preventDefault();

    var rout = $("#idRout-" + index).val();

    var object = {routId: rout};

    $.post(contextPath+"/admin/routs?delete", object).done(function (result) {
        $('#routMessage').empty().text("Rout " + result + " was deleted");
        getRoutList();
    }).fail(function () {
        $('#routMessage').text('Delete rout failed');
    });
}

$('#btnAddRout').click(function () {
    event.preventDefault();

    $.post(contextPath+"/admin/routs?save", $('#routForm').serialize()).done(function (result) {
        $('#routMessage').empty().text("Rout " + result + " was saved");
        getRoutList();
    }).fail(function () {
        $('#routMessage').text('Save rout failed');
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
        $('#routMessage').text("Get rout's list failed");
    });
}

window.onload = getRoutList;
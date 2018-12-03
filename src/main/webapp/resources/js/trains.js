function trainEdit(index) {
    event.preventDefault();

    var train = $("#idTrain-" + index).val();

    var object = {trainId: train};

    $.post(contextPath+"/admin/trains?change", object).done(function (result) {
        $('#trainMessage').text('');

        $('form[name=trainForm]').val(result);
        $('#idForm').val(result.id);
        $('#trainName').val(result.trainName);
        $('#placesNumber').val(result.placesNumber);
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

function trainDelete(index) {
    event.preventDefault();

    var train = $("#idTrain-" + index).val();

    var object = {trainId: train};

    $.post(contextPath+"/admin/trains?delete", object).done(function (result) {
        $('#trainMessage').empty().text(result);
        getTrainList();
    }).fail(function () {
        alert('Delete rout failed');
    });
}

$('#btnAddTrain').click(function () {
    event.preventDefault();

    $.post(contextPath+"/admin/trains?save", $('#trainForm').serialize()).done(function (result) {
        $('#trainMessage').empty().text(result);
        getTrainList();
    }).fail(function (e) {
        alert('Error: ' + JSON.stringify(e));
    });
})

$('#btnClearTrain').click(function () {
    event.preventDefault();
    $('form input[type="text"], form input[type="hidden"]').val('');
})

function getTrainList() {
    event.preventDefault();

    $.get(contextPath+"/admin/trains?list", {}).done(function (result) {
        var data = {trains: result};
        var template = Handlebars.compile($('#template').html());
        $("#myTableTrains tr>td").remove();
        $('.table').append(template(data));
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

window.onload = getTrainList;
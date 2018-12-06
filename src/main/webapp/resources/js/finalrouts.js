function finalEdit(index) {
    event.preventDefault();

    var rout = $("#idFinal-" + index).val();

    var object = {finalRoutId: rout};

    $.post(contextPath+"/admin/finalrouts?change", object).done(function (result) {
        $('#finalRoutMessage').text('');

        $('form[name=routRoutForm]').val(result);
        $('#idForm').val(result.id);
        $('#date').val(result.date);
        $('#comboboxTrain').val(result.train.id);
        $('#comboboxRout').val(result.rout.id);
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

function finalDelete(index) {
    event.preventDefault();

    var final = $("#idFinal-" + index).val();

    var object = {finalRoutId: final};

    $.post(contextPath+"/admin/finalrouts?delete", object).done(function (result) {
        $('#finalRoutMessage').empty().text(result);
        getFinalList();
    }).fail(function () {
        alert('Delete final rout failed');
    });
}

$('#btnSaveFinal').click(function () {
    event.preventDefault();

    $.post(contextPath+"/admin/finalrouts?save", $('#finalRoutForm').serialize()).done(function (result) {
        $('#finalRoutMessage').empty().text(result);
        getFinalList();
    }).fail(function (e) {
        alert('Error: ' + JSON.stringify(e));
    });
})

$('#btnClearFinal').click(function () {
    event.preventDefault();
    $('form input[type="text"], form input[type="hidden"], form input[type="date"]').val('');
    $('form[name=finalRoutForm]').trigger('reset');
})

function getFinalList() {
    event.preventDefault();

    $.getJSON(contextPath+"/admin/finalrouts?list", {}).done(function (result) {
        // var prep = JSON.parse(result);
        var data = {finalrouts: $.parseJSON(result)};
        var template = Handlebars.compile($('#template').html());
        $("#myTableFinalRouts tr>td").remove();
        $('.table').append(template(data));
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

window.onload = getFinalList;
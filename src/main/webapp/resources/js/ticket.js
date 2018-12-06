// $(document).ready(function () {
    $('#btnBuy').click(function () {
        event.preventDefault();

        var routId = $("#routId").val();
        var stationFrom = $("#stationFrom").val();
        var stationTo = $("#stationTo").val();
        var price = $("#price").val();

        var object = {stationFrom: stationFrom, stationTo: stationTo, routId: routId, price: price};

        $.post(contextPath+"/buy?purchase", object).done(function (result) {
            if (result.toString().startsWith("ok")) {
                alert("You have successfully bought tickets!");
                window.location = contextPath + "/home"
            } else {
                $('#errorMessage').empty().text(result);
            }
        }).fail(function () {
            $('#errorMessage').empty().text('Purchase ticket faile');
        });

    });
// });

function showAddUser() {
    event.preventDefault();

    clearPassengerForm();
    $('.container').show();
}

function clearPassengerForm() {
    event.preventDefault();
    $('form input[type="text"], form input[type="hidden"], form input[type="date"]').val('');
    $('form[name=passengerForm]').trigger('reset');
}

function userEdit(index) {
    event.preventDefault();

    var id = $("#idUser-" + index).val();
    var firstname = $("#firstname-" + index).val();
    var surname = $("#surname-" + index).val();
    var birthDay = $("#birthDate-" + index).val();

    var object = {id: id, firstname: firstname, surname: surname, birthDay: birthDay};

    $.post(contextPath+"/buy/passengers/change", object).done(function (result) {
        $('#userMessage').text('');

        $('form[name=passengerForm]').val(result);
        $('#idForm').val(result.id);
        $('#firstname').val(result.firstname);
        $('#surname').val(result.surname);
        $('#birthDate').val(result.birthDate);
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

function userDelete(index) {
    event.preventDefault();

    var firstname = $("#firstname-" + index).val();
    var surname = $("#surname-" + index).val();
    var birthDay = $("#birthDate-" + index).val();

    var object = {firstname: firstname, surname: surname, birthDay: birthDay};

    $.post(contextPath+"/buy/passengers/delete", object).done(function (result) {
        $('#userMessage').empty().text(result);
        getPassengerList();
    }).fail(function () {
        alert('Delete user from order failed');
    });
}

$('#btnSave').click(function () {
    event.preventDefault();

    var firstname = $('#firstname').val();
    var surname = $('#surname').val();
    var birthDate = $('#birthDate').val();

    var obj = {surname: surname, firstname: firstname, date: birthDate};

    $.post(contextPath+"/buy/passengers?valid", obj).done(function (result) {
        // $('#userMessage').empty().text(result);

        if (result.toString().startsWith("no user")) {
            if (confirm("This user is not registered yet. Do you want add him as passenger?")) {
                $.post(contextPath + "/buy/passengers/add?new", obj).done(function (result) {
                    $('#userMessage').empty().text(result);
                }).fail(function () {
                    alert('Adding user to order failed ');
                });
            }
        } else {
            $.post(contextPath + "/buy/passengers/add", {passenger: result.toString()}).done(function (result) {
                $('#userMessage').empty().text(result);
            }).fail(function () {
                alert('Adding user to order failed ');
            });
        }

        getPassengerList();
    }).fail(function (e) {
        alert('Error: ' + JSON.stringify(e));
    });
})

$('#btnClear').click(function () {
    clearPassengerForm();
})

function getPassengerList() {
    event.preventDefault();

    // var from = $('#comboboxSectionFrom').val();
    // var to = $('#comboboxSectionTo').val();

    // var object = {section

    $.get(contextPath+"/buy/passengers?list").done(function (result) {
        var data = {users: result};
        var template = Handlebars.compile($('#template').html());
        $("#myTablePassengers tr>td").remove();
        $('#myTablePassengers').append(template(data));
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

window.onload = getPassengerList;
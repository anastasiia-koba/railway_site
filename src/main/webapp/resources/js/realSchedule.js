function searchRout() {
    event.preventDefault();

    var station = $("#comboboxStation").val();
    var date = $("#date").val();

    var obj = {station: station, date: date};
    $.get(contextPath+"/admin/schedule/list", obj).done(function (result) {
        $('.container').show();

        var data = JSON.parse(result);
        var routs = {routs: data};

        $('#countTrains').empty().text('Through the station '+ station+' '+data.length+ ' Trains founded');
        $('#message').text('');

        var template = Handlebars.compile($('#templateRouts').html());
        $("#myTable tr>td").remove();
        $('#myTable').append(template(routs));
    }).fail(function () {
        $('#message').empty().text('Error during load');
    });
}

function ontimeSend(index) {
    event.preventDefault();

    var rout = $('#idRout-'+index).val();
    var station = $("#comboboxStation").val();
    var date = $("#date").val();


    var object = {station: station, date: date, rout: rout};
    $.post(contextPath+"/admin/schedule/sendOnTime", object).done(function (result) {
        $('#message').empty().text("Send 'On Time' to Tablo for station "+result+" on date "+date);
    }).fail(function () {
        $('#message').empty().text('Error during send "On Time"');
    });
}

function delaySend(index) {
    event.preventDefault();

    var rout = $('#idRout-'+index).val();
    var station = $("#comboboxStation").val();
    var date = $("#date").val();


    var object = {station: station, date: date, rout: rout};
    $.post(contextPath+"/admin/schedule/sendDelayed", object).done(function (result) {
        $('#message').empty().text("Send 'Delayed' to Tablo for station "+result+" on date "+date);
    }).fail(function () {
        $('#message').empty().text('Error during send "Delayed"');
    });
}

function cancelSend(index) {
    event.preventDefault();

    var rout = $('#idRout-'+index).val();
    var station = $("#comboboxStation").val();
    var date = $("#date").val();


    var object = {station: station, date: date, rout: rout};
    $.post(contextPath+"/admin/schedule/sendCanceled", object).done(function (result) {
        $('#message').empty().text("Send 'Canceled' to Tablo for station "+result+" on date "+date);
    }).fail(function () {
        $('#message').empty().text('Error during send "Cancel"');
    });
}
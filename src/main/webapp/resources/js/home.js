function searchRout() {
    event.preventDefault();

    var from = $('#comboboxFrom').val();
    var to = $('#comboboxTo').val();

    var access = true;

    if (from == "") {
        $('#messageFrom').text("Departure required!");
        access = false;
    } else
        $('#messageFrom').text('');

    if (to == "") {
        $('#messageTo').text("Destination required!");
        access = false;
    } else
        $('#messageTo').text('');

    var date = $('#date').val();
    var count = $('#places').val();

    if (date == "") {
        $('#dateMsg').text("Date required!");
        access = false;
    } else
        $('#dateMsg').text('');

    if (count == "") {
        $('#countMsg').text("Count passengers required!");
        access = false;
    } else
        $('#countMsg').text('');

    if (!access)
        return;

    var object = { from: from,
        to: to,
        date: date,
        count: count};

    $.get(contextPath+"/search", object).done(function (result) {
        $('.container').show();
        $('#buildMessage').empty().text(from + " -> " + to);
        $('#countRoutMessage').empty().text(result.size);

        var data = JSON.parse(result);
        var routs = {routs: data};

        var template = Handlebars.compile($('#templateFinals').html());
        $("#myTableRouts tr>td").remove();
        $('#myTableRouts').append(template(routs));

        data.forEach(function (value, index) {
            $("#from-" + index).val(from);
            $("#to-" + index).val(to);
            if (value.freePlace-count > 0) {
                $('#btnBuy-'+index).prop("disabled", false);
                $('#error-'+index).text('');
            } else {
                $('#btnBuy-'+index).prop("disabled", true);
                $('#error-'+index).text('Not enough free seats');
            };
            if (!value.available) {
                $('#btnBuy-'+index).prop("disabled", false);
                $('#error-'+index).text('');
            } else {
                $('#btnBuy-'+index).prop("disabled", true);
                $('#error-'+index).text("Train's departure is in less than 10 minutes");
            };
        })
    }).fail(function () {
        $('#buildMessage').empty().text('Search route failed');
    });
}

function fromSelect() {
    event.preventDefault();

    var from = $('#comboboxFrom').val();
    var op = document.getElementById("comboboxTo").getElementsByTagName("option");
    for (var i = 0; i < op.length; i++) {
        (op[i].value == from)
            ? op[i].disabled = true
            : op[i].disabled = false ;
    }
}

function toSelect() {
    event.preventDefault();

    var to = $('#comboboxTo').val();
    var op = document.getElementById("comboboxFrom").getElementsByTagName("option");
    for (var i = 0; i < op.length; i++) {
        (op[i].value == to)
            ? op[i].disabled = true
            : op[i].disabled = false ;
    }
}

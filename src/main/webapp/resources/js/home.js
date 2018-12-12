function searchRout() {
    event.preventDefault();

    var from = $('#comboboxFrom').val();
    var to = $('#comboboxTo').val();
    var date = $('#date').val();
    var count = $('#places').val();

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
                $('btnBuy-'+index).prop("disabled", false);
            } else {
                $('btnBuy-'+index).prop("disabled", true);
            }
        })
    }).fail(function () {
        $('#buildMessage').empty().text('Search rout failed');
    });
}

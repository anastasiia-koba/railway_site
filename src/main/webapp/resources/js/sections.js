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

    $('#sectionForm').validate({
        rules: {
            departure: "required",
            destination: "required",
            departureTime: "required",
            arrivalTime: "required",
            distance: "required",
            price: "required"
        },
        messages: {
            surname: "Please enter ",
            departure: "Please enter departure station",
            destination: "Please enter destination station",
            departureTime: "Please enter departure time",
            arrivalTime: "Please enter arrival time",
            distance: "Please enter distance",
            price: "Please enter price"
        }
    });
});


function searchRout() {
    event.preventDefault();

    clearSectionForm();
    $('#sectionMessage').text('');
    $('#errorBackMessage').text('');
    var rout = $('#comboboxRout').val();

    $('.container').show();
    getSectionList(rout);
}

function formBackRout() {
    event.preventDefault();

    var rout = $('#routId').val();
    var backRout = $('#comboboxBack').val();

    var object = {rout: rout, backRout: backRout};

    $.post(contextPath+"/admin/sections/form?back", object).done(function (result) {
        $('#errorBackMessage').empty().text(result);
        getSectionList(rout);
    }).fail(function () {
        alert('Delete rout failed');
    });
}

function sectionEdit(index) {
    event.preventDefault();

    var section = $("#idSection-" + index).val();

    var object = {sectionId: section};

    $.post(contextPath+"/admin/sections?change", object).done(function (result) {
        $('#sectionMessage').text('');
        $('#errorBackMessage').text('');

        $('form[name=sectionForm]').val(result);
        $('#idForm').val(result.id);
        $('#comboboxSecFrom').val(result.departure.stationName);
        $('#comboboxSecTo').val(result.destination.stationName);
        $('#timeDeparture').val(result.departureTime);
        $('#timeArrival').val(result.arrivalTime);
        $('#distance').val(result.distance);
        $('#price').val(result.price);
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

function sectionDelete(index) {
    event.preventDefault();

    var section = $("#idSection-" + index).val();
    var rout = $('#routId').val();

    var object = {sectionId: section, routId: rout};

    $.post(contextPath+"/admin/sections?delete", object).done(function (result) {
        $('#sectionMessage').empty().text(result);
        $('#errorBackMessage').text('');
        getSectionList(rout);
    }).fail(function () {
        alert('Delete rout failed');
    });
}

$('#btnSaveSection').click(function () {
    event.preventDefault();

    var rout = $('#routId').val();

    $.post(contextPath+"/admin/sections?save", $('#sectionForm').serialize()+"&routId="+rout).done(function (result) {
        $('#sectionMessage').empty().text(result);
        $('#errorBackMessage').text('');
        getSectionList(rout);
        getSearchList();
    }).fail(function (e) {
        alert('Error: ' + JSON.stringify(e));
    });
})

$('#btnClearSection').click(function () {
    clearSectionForm();
})

function clearSectionForm() {
    event.preventDefault();
    $('form input[type="text"], form input[type="hidden"], form input[type="time"]').val('');
    $('form[name=sectionForm]').trigger('reset');
}

function getSectionList(rout) {
    event.preventDefault();

    $('#routId').val(rout);
    $.get(contextPath+"/admin/sections/rout?list", {routForSearch: rout}).done(function (result) {
        var data = JSON.parse(result);
        $("#buildMessage").empty().text(data.buildMessage);
        $('#errorBackMessage').text('');

        // if (data.buildMessage == "Rout has errors") {
        //     $("#buildMessage").style.backgroundColor = 'red';
        // } else {
        //     $("#buildMessage").style.backgroundColor = 'green';
        // }

        var rs = {sections : JSON.parse(data.sections)};
        var template = Handlebars.compile($('#templateSections').html());
        $("#myTableSections tr>td").remove();
        $('#myTableSections').append(template(rs));
    }).fail(function (e) {
        alert('Error: ' + JSON.stringify(e));
    });
}

$('#btnAllSections').click( function () {
    getSearchList();
})

function getSearchList() {
    event.preventDefault();

    var from = $('#comboboxSectionFrom').val();
    var to = $('#comboboxSectionTo').val();

    var object = {sectionFrom: from, sectionTo: to};

    $.get(contextPath+"/admin/sections/list", object).done(function (result) {
        var data = {search: result};
        var template = Handlebars.compile($('#templateSearch').html());
        $("#myTableAllSections tr>td").remove();
        $('#myTableAllSections').append(template(data));
    }).fail(function (e) {
        alert('Error: ' + e);
    });
}

function sectionAdd(index) {
    event.preventDefault();

    var section = $("#idSearch-" + index).val();
    var rout = $("#routId").val();

    var object = {sectionId: section, routId: rout};

    $.post(contextPath+"/admin/sections/all?add", object).done(function (result) {
        $('#sectionMessage').empty().text(result);
        $('#errorBackMessage').text('');
        getSectionList(rout);
    }).fail(function () {
        alert('Delete section failed');
    });
}

function sectionDeleteFromAll(index) {
    event.preventDefault();

    var section = $("#idSearch-" + index).val();
    var rout = $("#routId").val();

    var object = {sectionId: section};

    $.post(contextPath+"/admin/sections/all?delete", object).done(function (result) {
        $('#sectionMessage').empty().text(result);
        $('#errorBackMessage').text('');
        getSectionList(rout);
        getSearchList();
    }).fail(function () {
        alert('Delete section failed');
    });
}


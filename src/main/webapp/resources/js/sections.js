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
        $('#sectionMessage').text('Form back route failed');
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
    }).fail(function () {
        $('#sectionMessage').text('Edit section failed');
    });
}

function sectionDelete(index) {
    event.preventDefault();

    var section = $("#idSection-" + index).val();
    var rout = $('#routId').val();

    var object = {sectionId: section, routId: rout};

    $.post(contextPath+"/admin/sections?delete", object).done(function (result) {
        if (result.toString().startsWith("Error:")) {
            $('#sectionMessage').empty().text(result);
        } else {
            $('#sectionMessage').empty().text("Section " + result + " was deleted");
        }
        $('#errorBackMessage').text('');
        getSectionList(rout);
    }).fail(function () {
        $('#sectionMessage').text('Delete section failed');
    });
}

$('#sectionForm').submit(function (event) {
    event.preventDefault();

    var rout = $('#routId').val();

    $.post(contextPath+"/admin/sections?save", $('#sectionForm').serialize()+"&routId="+rout).done(function (result) {
        if (result.toString().startsWith("Error:")) {
            $('#sectionMessage').empty().text(result);
        } else {
            $('#sectionMessage').empty().text("Section " + result + " was saved");
        }
        $('#errorBackMessage').text('');
        getSectionList(rout);
        getSearchList();
    }).fail(function () {
        $('#sectionMessage').text('Save section failed');
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

        var rs = {sections : JSON.parse(data.sections)};
        var template = Handlebars.compile($('#templateSections').html());
        $("#myTableSections tr>td").remove();
        $('#myTableSections').append(template(rs));
    }).fail(function () {
        $('#sectionMessage').text("Get section's list failed");
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
        $('#sectionMessage').text("Get existing section's list");
    });
}

function sectionAdd(index) {
    event.preventDefault();

    var section = $("#idSearch-" + index).val();
    var rout = $("#routId").val();

    var object = {sectionId: section, routId: rout};

    $.post(contextPath+"/admin/sections/all?add", object).done(function (result) {
        if (result.toString().startsWith("Error:")) {
            $('#sectionMessage').empty().text(result);
        } else {
            $('#sectionMessage').empty().text("Section " + result + " was saved");
        }
        $('#errorBackMessage').text('');
        getSectionList(rout);
    }).fail(function () {
        $('#sectionMessage').text('Delete section failed');
    });
}

function sectionDeleteFromAll(index) {
    event.preventDefault();

    var section = $("#idSearch-" + index).val();
    var rout = $("#routId").val();

    var object = {sectionId: section};

    $.post(contextPath+"/admin/sections/all?delete", object).done(function (result) {
        if (result.toString().startsWith("Error:")) {
            $('#sectionMessage').empty().text(result);
        } else {
            $('#sectionMessage').empty().text("Section " + result + " was deleted from all routs");
        }
        $('#errorBackMessage').text('');
        getSectionList(rout);
        getSearchList();
    }).fail(function () {
        $('#sectionMessage').text('Delete section failed');
    });
}

function fromSelect() {
    event.preventDefault();

    var from = $('#comboboxSecFrom').val();
    var op = document.getElementById("comboboxSecTo").getElementsByTagName("option");
    for (var i = 0; i < op.length; i++) {
        (op[i].value == from)
            ? op[i].disabled = true
            : op[i].disabled = false ;
    }
}

function toSelect() {
    event.preventDefault();

    var to = $('#comboboxSecTo').val();
    var op = document.getElementById("comboboxSecFrom").getElementsByTagName("option");
    for (var i = 0; i < op.length; i++) {
        (op[i].value == to)
            ? op[i].disabled = true
            : op[i].disabled = false ;
    }
}

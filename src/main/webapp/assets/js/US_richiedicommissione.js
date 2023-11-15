$(document).ready(function () {
    try {
        $('#commissione').select2({
            allowClear: true,
            language: "it",
            maximumSelectionLength: 2
        });
        $('#sostituti').select2({
            allowClear: true,
            language: "it",
            maximumSelectionLength: 2
        });
        $('#SOSTITUTO').select2({
            allowClear: true,
            language: "it",
            maximumSelectionLength: 2
        });
    } catch (e) {
    }
});

function checkdatisalvati() {

    var nominativicommissione = ($('#commissione').val() + "");
    var nominativisostituti = ($('#sostituti').val() + "");
    // alert(nominativicommissione.split(",").length);
    // alert(nominativisostituti.split(",").length);
    // return false;
    if (nominativicommissione.split(",").length !== 2) {
        $.alert({
            title: "NOMINATIVI COMMISSIONE NON COMPLETI.",
            content: "Controllare.",
            type: 'red',
            typeAnimated: true,
            buttons: {
                confirm: {
                    text: 'OK',
                    btnClass: 'btn-red'
                }
            }
        });
        return false;
    } else if (nominativisostituti.split(",").length !== 2) {
        $.alert({
            title: "NOMINATIVI SOSTITUTI NON COMPLETI.",
            content: "Controllare.",
            type: 'red',
            typeAnimated: true,
            buttons: {
                confirm: {
                    text: 'OK',
                    btnClass: 'btn-red'
                }
            }
        });
        return false;
    }


}

function popolaeverificadocenti(idcorso) {

    var nominativicommissione = ($('#commissione').val() + "");
    if (nominativicommissione.split(",").length === 2) {

        $.ajax({
            type: "POST",
            url: "Search",
            data: {
                "type": "LISTDOCENTICORSOPERCOMMISSIONE",
                "IDCORSO": idcorso,
                "SELECTED": nominativicommissione
            },
            success: function (data) {
                $("#sostituti").html('');
                $("#sostituti").html(data);
            }
        });

    } else {
        $("#sostituti").html('');
    }
}


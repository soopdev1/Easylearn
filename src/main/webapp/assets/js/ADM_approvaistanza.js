function checkapprovaistanza() {
    var sel = 0;
    $('.checkboxesr input[type=checkbox]').each(function () {
        if (this.checked) {
            sel++;
        }
    });
    if (sel > 0) {
        return true;
    } else {
        $.alert({
            title: "Errore durante l'operazione!",
            content: "Necessario approvare almeno un corso dell'istanza. Riprovare",
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

function abilitaapprovazione(idcorso) {
    if (!$('#OK_' + idcorso).is(':checked')) {
        if ($('#CIP_' + idcorso).length > 0) {
            $('#CIP_' + idcorso).val("");
            $('#CIP_' + idcorso).attr('disabled', 'disabled');
        }
        if ($('#CUP_' + idcorso).length > 0) {
            $('#CUP_' + idcorso).val("");
            $('#CUP_' + idcorso).attr('disabled', 'disabled');
        }
        if ($('#ID_' + idcorso).length > 0) {
            $('#ID_' + idcorso).val("");
            $('#ID_' + idcorso).attr('disabled', 'disabled');
        }
        if ($('#CS_' + idcorso).length > 0) {
            $('#CS_' + idcorso).val("");
            $('#CS_' + idcorso).attr('disabled', 'disabled');
        }
        if ($('#ED_' + idcorso).length > 0) {
            $('#ED_' + idcorso).val("");
            $('#ED_' + idcorso).attr('disabled', 'disabled');
        }
    } else {
        if ($('#CIP_' + idcorso).length > 0) {
            $('#CIP_' + idcorso).removeAttr('disabled');
        }
        if ($('#CUP_' + idcorso).length > 0) {
            $('#CUP_' + idcorso).removeAttr('disabled');
        }
        if ($('#ID_' + idcorso).length > 0) {
            $('#ID_' + idcorso).removeAttr('disabled');
        }
        if ($('#CS_' + idcorso).length > 0) {
            $('#CS_' + idcorso).removeAttr('disabled');
        }
        if ($('#ED_' + idcorso).length > 0) {
            $('#ED_' + idcorso).removeAttr('disabled');
        }
    }

}
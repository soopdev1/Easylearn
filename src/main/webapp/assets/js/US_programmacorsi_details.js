function verificasalvataggiodati() {
    $('#messageerror').css('display', 'none');
    var idcorsodasalvare = $('#idcorsodasalvare').val();
    var ORETOTALI = $('#ORETOTALI').val();
    var OREAULATEO = $('#OREAULATEO').val();
    var OREAULATEC = $('#OREAULATEC').val();
    var OREELE = $('#OREELE').val();
    var ok = false;
    $.ajax({
        url: 'Operations',
        type: 'POST',
        data: {
            'type': 'CHECKMODULO',
            'IDOCORSO': idcorsodasalvare,
            'ORETOTALI': ORETOTALI,
            'OREAULATEO': OREAULATEO,
            'OREAULATEC': OREAULATEC,
            'OREELE': OREELE
        },
        dataType: 'json',
        async: false,
        success: function (data) {
            //check
            if (data.result) {
                ok = true;
            } else {
                $('#messageerror').css('display', '');
                $('#messageerror').html("ERRORE: " + data.message);
            }
        },
        error: function (request, error) {
            $('#messageerror').css('display', '');
            $('#messageerror').html("ERRORE: " + error);
        }
    });
    if (ok) {
        if ($('.checkboxesr input:checked').length === 0) {
            ok = false;
            $('#messageerror').css('display', '');
            $('#messageerror').html("ERRORE: &#200; necessario selezionare almeno una competenza (Abilit&#224; e/o Conoscenza)");
        }
    }
    return ok;
}


function check_abilita_competenze() {
    $('.compicon').css('display', 'none');
    $('.checkboxesr input[type=checkbox]').each(function () {
        var completeid = $(this).attr('id');
//        var idch = completeid.split('_')[1];
        var idcompetenza = completeid.split('_')[2];

        if (completeid.startsWith("AB")) {
            if (this.checked) {
                $('#comp_' + idcompetenza).css('display', '');
            }
        } else if (completeid.startsWith("CO")) {
            if (this.checked) {
                $('#comp_' + idcompetenza).css('display', '');
            }
        }
    });
}

$(document).ready(function () {
    Inputmask({
        mask: "999,9",
        digits: 1,
        numericInput: true
    }).mask(".decimalvalue");
    return check_abilita_competenze();
});
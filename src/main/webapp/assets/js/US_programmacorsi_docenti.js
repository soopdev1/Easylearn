function verificasalvataggiodocentemodulo() {

    var checked = false;
    var ok = true;

    $('.checkboxesr input[type=checkbox]').each(function () {
        var completeid = $(this).attr('id');
        if ($(this).is(":checked")) {
            checked = true;
            var inputdaverificare = $("#" + completeid.replaceAll("CH_", "ORE_"));
            var oremaxdaverificare = $("#" + completeid.replaceAll("CH_", "ore_modules_"));
            var v1 = parseFloat(inputdaverificare.val().replaceAll("_", "0").replace(/,/g, '.'));
            var v2 = parseFloat(oremaxdaverificare.val());
            if (v1 > v2) {
                $('#messageerror').css('display', '');
                $('#messageerror').html("ERRORE: Il numero di ore previste per il docente supera le ore totali. Controllare.");
                ok = false;
            }
        }
    });

    if (!checked) {
        ok = false;
        $('#messageerror').css('display', '');
        $('#messageerror').html("ERRORE: &#200; necessario selezionare almeno un modulo formativo da associare al docente");
    }

    return ok;
}


function abilitainputore(id) {

    var idinput = id.replace("CH_", "ORE_");
    var ischecked = $('#' + id).is(":checked");
    if (ischecked) {
        $("#" + idinput).val("");
        $("#" + idinput).prop("disabled", false);
        $("#" + idinput).prop("required", false);
        $("#" + idinput).attr("disabled", false);
        $("#" + idinput).attr("required", true);
        $("#" + idinput).removeAttr('disabled');
        $("#" + idinput).removeProp('disabled');
    } else {
        $("#" + idinput).val("");
        $("#" + idinput).prop("disabled", true);
        $("#" + idinput).attr("disabled", true);
        $("#" + idinput).removeAttr('required');
        $("#" + idinput).removeProp('required');
    }
}

$(document).ready(function () {
    Inputmask({
        mask: "999,9",
        digits: 1,
        numericInput: true
    }).mask(".decimalvalue");
    Inputmask({
        mask: "9999",
        digits: 1,
        numericInput: true
    }).mask(".intvalue");
});
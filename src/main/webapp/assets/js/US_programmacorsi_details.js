function verificasalvataggiodati() {

    var idcorsodasalvare = $('#idcorsodasalvare').val();
    var ORETOTALI = $('#ORETOTALI').val();
    var OREAULATEO = $('#OREAULATEO').val();
    var OREAULATEC = $('#OREAULATEC').val();
    var OREELE = $('#OREELE').val();
    $.ajax({
        url: 'Operations',
        type: 'POST',
        data: {
            'type':'CHECKMODULO',
            'IDOCORSO': idcorsodasalvare,
            'ORETOTALI': ORETOTALI,
            'OREAULATEO': OREAULATEO,
            'OREAULATEC': OREAULATEC,
            'OREELE': OREELE            
        },
        dataType: 'json',
        success: function (data) {
            alert('Data: ' + data);
        },
        error: function (request, error)
        {
            alert("Request: " + JSON.stringify(request));
        }
    });


    return false;
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
//        if ($(this).is(":checked")) {
//            selected.push($(this).attr('name'));
//        }
    });
}

function checkmaskdecimal(input) {
    alert($(input).val());
}


$(document).ready(function () {
    Inputmask({
        mask: "999,9",
        greedy: false,
        digits: 1,
        placeholder: "000,0"
    }).mask(".decimalvalue");
    return check_abilita_competenze();
});
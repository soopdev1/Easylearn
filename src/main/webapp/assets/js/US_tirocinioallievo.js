$(document).ready(function () {
    Inputmask({
        mask: "9999",
        digits: 1,
        numericInput: true
    }).mask(".intvalue");
});


function checkore() {

    var maxstage = parseInt($("#maxstage").val());

    $('.orestabilite').each(function (i, obj) {
        if (obj.id !== "") {
            maxstage = maxstage - parseInt($('#' + obj.id).val());
        }
    });

    var ORE = parseInt($("#ORE").val().replaceAll("_", "").trim());

    if (ORE > maxstage) {
        $.alert({
            title: "Errore durante l'operazione!",
            content: "Le ore di stage indicate <b>(" + ORE + ")</b> superano quelle di tirocinio disponibili <b>(" + maxstage +
                    ")</b> per il corso svolto dall'allievo. Controllare.",
            type: 'red',
            typeAnimated: true,
            theme: 'bootstrap',
            columnClass: 'col-md-9',
            buttons: {
                confirm: {
                    text: 'OK',
                    btnClass: 'btn-red'
                }
            }
        });
        return false;
    } else {
        return true;
    }
}
var table;

$(document).ready(function () {
    table = $('#tab_dt1').DataTable({
        dom: '<if<t>lp>',
        lengthMenu: [[20, 100, -1], [20, 100, "Tutto"]],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
    Inputmask({
        mask: "9999",
        digits: 1,
        numericInput: true
    }).mask(".intvalue");
    Inputmask({
        mask: "999",
        digits: 1,
        numericInput: true
    }).mask(".intvalue3");
    Inputmask({
        mask: "999,99",
        digits: 1,
        numericInput: true
    }).mask(".decimalvalue");
    $('.form-select').select2({
        language: "it"
    });
    Fancybox.bind(".fan1", {
        groupAll: false, // Group all items
        hideScrollbar: false,
        on: {
            closing: (fancybox) => {
                window.location.reload();
            }
        },
        fullscreen: {
            autoStart: true
        }
    });
    Fancybox.bind(".fan2", {
        groupAll: false, // Group all items
        hideScrollbar: false,
        on: {
            closing: (fancybox) => {
                refreshtable();
            }
        },
        fullscreen: {
            autoStart: true
        }
    });

    try {
        tinymce.init({
            selector: 'textarea',
            toolbar: 'undo redo | bold italic underline',
            menubar: false,
            branding: false,
            statusbar: false,
            height: 200,
            max_height: 200
        });
    } catch (e) {

    }

    $(".viewresult").on("change", function () {
        calcolaValutazioneFinale(this.id.split('_')[1]);
    });



});

function refreshtable() {
    table.ajax.reload(null, false);
}

function calcolaValutazioneFinale(endrow) {

    var AMMESSO = $('#AMMESSO_' + endrow).val();
    var AMMESSOFORZATO = $('#AMMESSOFORZATO_' + endrow).val();
    var PRESENTE = $('#PRESENTE_' + endrow).val();

    var calcola = true;

    if (AMMESSO === "NO") {
        if (AMMESSOFORZATO === "NO") {
            calcola = false;
        }
    }

    if (PRESENTE === "NO") {
        calcola = false;
    }



    var VOTOAMMISSIONE = $('#VOTOAMM_' + endrow).val();
    var VOTOPROVEINTERM = $('#VOTOMEDIA_' + endrow).val();
    var VOTOPROVASCRITTA = $('#VOTOPSC_' + endrow).val();
    var VOTOCOLLOQUIO = $('#VOTOCOLL_' + endrow).val();
    var VOTOPROVAPRATICA = $('#VOTOPPR_' + endrow).val();

    try {


        if (calcola) {



            var v_VOTOAMMISSIONE = parseFloatR(VOTOAMMISSIONE.replaceAll("_", "0").replace(/,/g, '.'));
            var v_VOTOPROVEINTERM = parseFloatR(VOTOPROVEINTERM.replaceAll("_", "0").replace(/,/g, '.'));
            var v_VOTOPROVASCRITTA = parseFloatR(VOTOPROVASCRITTA.replaceAll("_", "0").replace(/,/g, '.'));
            var v_VOTOCOLLOQUIO = parseFloatR(VOTOCOLLOQUIO.replaceAll("_", "0").replace(/,/g, '.'));
            var v_VOTOPROVAPRATICA = parseFloatR(VOTOPROVAPRATICA.replaceAll("_", "0").replace(/,/g, '.'));
            var v1 = v_VOTOAMMISSIONE * 0.1;
            var v2 = v_VOTOPROVEINTERM * 0.1;
            var v3 = Math.ceil(v_VOTOPROVASCRITTA * 0.2);
            var v4 = Math.ceil(v_VOTOCOLLOQUIO * 0.2);
            var v5 = Math.ceil(v_VOTOPROVAPRATICA * 0.4);
            var res = Math.ceil(v1 + v2 + v3 + v4 + v5);

            $('#VOTOFINALE_' + endrow).val(res);
            if (res >= 70) {
                $("#ESITO_" + endrow).val("IDONEO").trigger('change');
            } else {
                $("#ESITO_" + endrow).val("NON IDONEO").trigger('change');
            }
        } else {

            $('#VOTOPSC_' + endrow).val("0");
            $('#VOTOCOLL_' + endrow).val("0");
            $('#VOTOPPR_' + endrow).val("0");
            $('#VOTOFINALE_' + endrow).val("0");
            $("#ESITO_" + endrow).val("ASSENTE").trigger('change');


        }

    } catch (e) {
        console.error(e.message);
    }


    //$('#VOTOFINALE_'+endrow)
}


function parseFloatR(val) {
    try {
        if (isNaN(parseFloat(val))) {
            return 0;
        } else {
            return parseFloat(val);
        }
    } catch (e) {
        return 0;
    }

}
$(document).ready(function () {
    setstartdate("datelez", 1, "min", null);
    populatedatafine(document.getElementById('orai'));
});

function checkorariomax() {
    var orai = $('#orai').val();
    var oraf = $('#oraf').val();
    $('.sel-presenza').each(function (i, obj) {
        if (obj.id !== "") {
            $('#' + obj.id).val(null).trigger('change');
            $('#' + obj.id).empty();
        }
    });
    try {
        var date1 = new Date('1/1/2000 ' + orai + ':00');
        var date2 = new Date('1/1/2000 ' + oraf + ':00');
        var diff = (date2 - date1) / 3600000;
        //console.log(diff);
        for (var st = 0; st <= diff; st = st + 0.5) {
            $('.sel-presenza').each(function (i, obj) {
                if (obj.id !== "") {
                    var millis = (st * 3600000);
                    var st1 = Number(st).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, "");
                    var newOption = new Option(st1, millis, false, false);
                    $('#' + obj.id).append(newOption).trigger('change');
//                    console.log(i + " --- " + obj.id);
                }
//                $('#' + obj.id).append(newOption).trigger('change');
            });
        }

        $('.sel-presenza').each(function (i, obj) {
            if (obj.id !== "") {
                $('#' + obj.id).val($('#start_' + obj.id).val()).trigger('change');
            }
        });
    } catch (e) {
        console.log(e);
    }
}

function setstartdate(id, addday, attr, startdate) {
    var dtToday = new Date();
    if (startdate !== null) {
        dtToday = new Date(startdate);
    }
    dtToday.setDate(dtToday.getDate() + addday);
    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if (month < 10) {
        month = '0' + month.toString();
    }
    if (day < 10) {
        day = '0' + day.toString();
    }
    var maxDate = year + '-' + month + '-' + day;
    $('#' + id).attr(attr, maxDate);
}

function checkdatisalvati() {

    var tipolez = $('#tipolez').val();
    var orai = $('#orai').val();
    var oraf = $('#oraf').val();
    var residue = $('#modulo option:selected').attr('data-res');
    var residuefad = $('#modulo option:selected').attr('data-resfad');
    var date1 = new Date('1/1/2000 ' + orai + ':00');
    var date2 = new Date('1/1/2000 ' + oraf + ':00');

    var diff = (date2 - date1) / 3600000;
    var labelres = residue;
    var check = diff > residue;
    alert(tipolez);
    if (tipolez === "FAD") {
        check = diff > residuefad;
        labelres = residuefad;
    }

    if (check) {

        var d1 = Intl.NumberFormat('it-IT', {
            style: 'decimal'
        });

        $.alert({
            title: "Errore durante l'operazione!",
            content: "Per il modulo selezionato e per il tipo di lezione selezionato il numero di ore programmate <b>(" + d1.format(diff) + ")</b> supera il numero di ore residue: <b><u>" + d1.format(labelres) + "</u></b>. Controllare.",
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
    }
    return true;
}

function populatedatafine(component) {
    $('#oraf').val(null).trigger('change');
    var start = component.value;
    if (start !== "") {
        for (var i = 8; i < 21; i++) {
            var v0 = new Date('1/1/2000 ' + start + ':00');
            var v1 = new Date('1/1/2000 ' + i + ':00:00');
            var v2 = new Date('1/1/2000 ' + i + ':30:00');

            if (v1 > v0) {
                var out1 = i + ":00";
                if (i < 10) {
                    out1 = "0" + i + ":00";
                }
                var newOption = new Option(out1, out1, false, false);
                $('#oraf').append(newOption).trigger('change');
            }
            if (v2 > v0) {
                var out1 = i + ":30";
                if (i < 10) {
                    out1 = "0" + i + ":30";
                }
                var newOption = new Option(out1, out1, false, false);
                $('#oraf').append(newOption).trigger('change');
            }

        }
    }
    checkorariomax();
}
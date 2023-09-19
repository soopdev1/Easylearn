$(document).ready(function () {
    $('#ISTANZA').change(
            function () {
                $.ajax({
                    type: "POST",
                    url: "Search",
                    data: {
                        "type": "LISTCORSIISTANZA",
                        "IDIST": $(this).val()
                    },
                    success: function (data) {
                        $("#CORSO").html('');
                        $("#CORSO").html(data);
                        $("#DOCENTI").html('');
                    }
                });
                $("#CORSOHelp").html('Elenco dei corsi relativi all\'istanza selezionata.');
            }
    );
    $('#CORSO').change(
            function () {
                $.ajax({
                    type: "POST",
                    url: "Search",
                    data: {
                        "type": "LISTDOCENTICORSO",
                        "IDCORSO": $(this).val()
                    },
                    success: function (data) {
                        $("#DOCENTI").html(data);
                    }
                });
                var md = $(this).find(':selected').data('maxday');
                if (md === "") {
                    md = 0;
                } else {
                    try {
                        md = parseInt(md);
                    } catch (e) {
                        md = 0;
                    }
                }
                var na = $(this).find(':selected').data('numall');
                if (na === "") {
                    na = 0;
                } else {
                    try {
                        na = parseInt(na);
                    } catch (e) {
                        na = 0;
                    }
                }

                $('#ALLIEVI').select2({maximumSelectionSize: 1});
                $("#checkend").val(md);
                setstartdate("DATAFINE", 15, "min", null);
                //setstartdate("DATAFINE", 15 + md, "max", null);
                $("#DATAFINEHelp").html("Durata corso indicata in istanza: <b>" + md + "</b> Giorni.");
                $("#ALLIEVIHelp").html("Numero Massimo Allievi: <b>" + na + "</b>.");
                $("#DOCENTIHelp").html("Elenco docenti autorizzati.");

            }
    );
    setstartdate("DATAINIZIO", 15, "min", null);
    $('#DATAINIZIO').change(
            function (e) {
                var date = $(this).val();
                var day = new Date(date).getDay();
                if (day === 0 || day === 6) {
                    e.preventDefault();
                    $(this).val("");
                    $.alert({
                        title: "Data non selezionabile.",
                        content: "Il giorno scelto non è valido. Controllare.",
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
                } else {
                    var v1 = $("#checkend").val();
                    try {
                        v1 = parseInt(v1);
                    } catch (e) {
                        v1 = 0;
                    }
                    setstartdate("DATAFINE", 1, "min", date);
                }
                //setstartdate("DATAFINE", 1 + v1, "max", date);
            }
    );

    $('#DOCENTI').select2({
        ajax: {
            url: "Search",
            dataType: 'json',
            delay: 250,
            type: "POST",
            data: function (params) {
                return {
                    type: 'SELECTDOCENTE',
                    q: params.term, // search term
                    page: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {
                    results: data.items,
                    pagination: {
                        more: (params.page * 30) < data.total_count
                    }
                };
            },
            cache: true
        },
        escapeMarkup: function (markup) {
            return markup; // let our custom formatter work
        },
        minimumInputLength: 2,
        templateResult: function (repo) {
            if (repo.loading)
                return repo.text;
            return repo.full_name;
        },
        templateSelection: function (repo) {
            return repo.full_name || repo.text;
        }
    });

});


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
    return true;
}
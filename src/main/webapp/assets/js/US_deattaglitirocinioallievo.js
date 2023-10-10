$(document).ready(function () {
    populatedatafine(document.getElementById('orai'));
});

function populatedatafine(component) {
    $('#oraf').val(null).trigger('change');
    $('#oraf').empty();
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
}

function convalidapresenzatirocinio(idpresenza) {
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        title: 'Conferma Operazione',
        content: "Confermi di voler convalidare i dati inseriti nel la presenza selezionata? L'operazione non potrà essere annullata.",
        theme: 'bootstrap',
        columnClass: 'col-md-9',
        buttons: {
            confirm: {
                btnClass: 'btn-success',
                text: "<i class='fa fa-check'></i> CONFERMO", // With spaces and symbols
                action: function () {
                    $.ajax({
                        url: 'Operations',
                        type: 'POST',
                        data: {
                            'type': 'CONVALIDAPRESENZATIROCINIO',
                            'IDPRESENZA': idpresenza
                        },
                        dataType: 'json',
                        async: false,
                        success: function (data) {
                            //check
                            if (data.result) {
                                ok = true;
                            } else {
                                messageko = ("ERRORE: " + data.message);
                            }
                        },
                        error: function (request, error) {
                            messageko = ("ERRORE: " + error);
                        }
                    });

                    if (ok) {
                        $.alert({
                            title: 'Operazione conclusa con successo!',
                            content: '',
                            type: 'success',
                            typeAnimated: true,
                            buttons: {
                                confirm: {
                                    text: 'OK',
                                    btnClass: 'btn-success',
                                    action: function () {
                                        location.reload(true);
                                    }
                                }
                            }
                        });
                    } else {
                        $.alert({
                            title: "Errore durante l'operazione!",
                            content: messageko,
                            type: 'red',
                            typeAnimated: true,
                            buttons: {
                                confirm: {
                                    text: 'OK',
                                    btnClass: 'btn-red'
                                }
                            }
                        });
                    }
                }
            }
            ,
            cancel: {
                btnClass: 'btn-danger',
                text: "<i class='fa fa-remove'></i> ANNULLO" // With spaces and symbols                
            }
        }
    });
}

function rimuovipresenzatirocinio(idpresenza) {

    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        title: 'Conferma Operazione',
        content: "Confermi di voler eliminare la presenza selezionata? L'operazione non potrà essere annullata.",
        theme: 'bootstrap',
        columnClass: 'col-md-9',
        buttons: {
            confirm: {
                btnClass: 'btn-success',
                text: "<i class='fa fa-check'></i> CONFERMO", // With spaces and symbols
                action: function () {
                    $.ajax({
                        url: 'Operations',
                        type: 'POST',
                        data: {
                            'type': 'DELETEPRESENZATIROCINIO',
                            'IDPRESENZA': idpresenza
                        },
                        dataType: 'json',
                        async: false,
                        success: function (data) {
                            //check
                            if (data.result) {
                                ok = true;
                            } else {
                                messageko = ("ERRORE: " + data.message);
                            }
                        },
                        error: function (request, error) {
                            messageko = ("ERRORE: " + error);
                        }
                    });

                    if (ok) {
                        $.alert({
                            title: 'Operazione conclusa con successo!',
                            content: '',
                            type: 'success',
                            typeAnimated: true,
                            buttons: {
                                confirm: {
                                    text: 'OK',
                                    btnClass: 'btn-success',
                                    action: function () {
                                        location.reload(true);
                                    }
                                }
                            }
                        });
                    } else {
                        $.alert({
                            title: "Errore durante l'operazione!",
                            content: messageko,
                            type: 'red',
                            typeAnimated: true,
                            buttons: {
                                confirm: {
                                    text: 'OK',
                                    btnClass: 'btn-red'
                                }
                            }
                        });
                    }
                }
            }
            ,
            cancel: {
                btnClass: 'btn-danger',
                text: "<i class='fa fa-remove'></i> ANNULLO" // With spaces and symbols                
            }
        }
    });
}
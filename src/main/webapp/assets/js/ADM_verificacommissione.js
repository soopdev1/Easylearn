function gestiscicommissione(IDCOMM, type, message) {

    var NUMPROTRICH = $('#NUMPROTRICH').val();
    var DATAPROTRICH = $('#DATAPROTRICH').val();

    if (NUMPROTRICH === "" ||
            DATAPROTRICH === "") {
        $.alert({
            title: "Errore durante l'operazione!",
            content: "Compilare i campi 'PROT. RICHIESTA' e 'DATA PROT. RICHIESTA'",
            type: 'red',
            typeAnimated: true,
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


    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        columnClass: 'col-md-9',
        title: 'Conferma Operazione',
        content: "Confermi di voler " + message + " la commissione d'esame? L'operazione non potrà essere annullata.",
        theme: 'bootstrap',
        buttons: {
            confirm: {
                btnClass: 'btn-success',
                text: "<i class='fa fa-check'></i> CONFERMO", // With spaces and symbols
                action: function () {
                    $.ajax({
                        url: 'Operations',
                        type: 'POST',
                        data: {
                            'type': type,
                            'IDCOMM': IDCOMM,
                            'NUMPROTRICH': NUMPROTRICH,
                            'DATAPROTRICH': DATAPROTRICH                            
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
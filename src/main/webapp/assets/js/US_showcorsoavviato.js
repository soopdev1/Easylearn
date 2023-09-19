$(document).ready(function () {
    $('#tab_dt0').DataTable({
        dom: '<f<t>>',
        lengthMenu: [[100, -1], [100, "Tutto"]],
        columnDefs: [
            {orderable: false, targets: 5}
        ],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
    $('#tab_dt1').DataTable({
        dom: '<t>',
        lengthMenu: [[100, -1], [100, "Tutto"]],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
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
});


function modificadirettore(idcorsoavviato) {
    var nomedirettore = "KO";
    $.ajax({
        url: "Search",
        type: "POST",
        async: true,
        data: {
            'type': "GETDIRETTORE",
            'IDCORSO': idcorsoavviato
        },
        success: function (data) {
            nomedirettore = data;
        },
        error: function (data) {
            console.log("ERRORE: " + data);
        }
    });

    if (nomedirettore === "KO") {
        $.alert({
            title: "Errore durante l'operazione!",
            content: "DIRETTORE NON TROVATO. RIPROVARE",
            type: 'red',
            typeAnimated: true,
            theme: 'bootstrap',
            columnClass: 'col-md-6',
            buttons: {
                confirm: {
                    text: 'OK',
                    btnClass: 'btn-red'
                }
            }
        });
        return false;
    } else {
        $.confirm({
            title: 'Modifica Direttore Corso - ID ' + idcorsoavviato,
            content: '<span>Direttore Attuale:' + nomedirettore + '</span>' +
                    '<form action="" class="formName">' +
                    '<div class="form-group">' +
                    '<label>Enter something here</label>' +
                    '<input type="text" placeholder="Your name" class="name form-control" required />' +
                    '</div>' +
                    '</form>',
            buttons: {
                formSubmit: {
                    text: 'Submit',
                    btnClass: 'btn-blue',
                    action: function () {
                        var name = this.$content.find('.name').val();
                        if (!name) {
                            $.alert('provide a valid name');
                            return false;
                        } else {
                            //submit
                            $.alert('Your name is ' + name);
                        }
                    }
                },
                cancel: function () {
                    //close
                }
            },
            onContentReady: function () {
                // bind to events
                var jc = this;
                this.$content.find('form').on('submit', function (e) {
                    // if the user submits the form by pressing enter in the field.
                    e.preventDefault();
                    jc.$$formSubmit.trigger('click'); // reference the button and click it
                });
            }
        });
    }
}

function rimuovilezione(idlezionecal) {

    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        title: 'Conferma Operazione',
        content: "Confermi di voler eliminare la lezione selezionata? L'operazione non potrà essere annullata.",
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
                            'type': 'DELETELEZIONE',
                            'IDLEZIONE': idlezionecal
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
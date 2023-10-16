var table;

$(document).ready(function () {
    table = $('#tab_dt1').DataTable({
        dom: '<Bif<t>lp>',
        buttons: [
            {
                extend: 'excelHtml5',
                className: 'btn btn-sm btn-primary',
                text: '<i class="fa fa-file-excel"></i>',
                titleAttr: 'Esporta in Excel',
                exportOptions: {
                    columns: [8, 1, 2, 3, 4, 5, 6] //Your Column value those you want
                }
            }
        ],
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "Tutto"]],
        order: [[6, 'desc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        },
        responsive: true,
        processing: true,
        ajax: {
            url: 'Search',
            type: 'POST',
            data: function (d) {
                d.type = 'list_corso_pres';
                d.soggetto = $('#soggetto').val();
                d.statocorso = $('#statocorso').val();
                d.tipopercorso = $('#tipopercorso').val();
            }
        },
        columns: [
            {data: 'stato'},
            {data: 'soggetto'},
            {data: 'id'},
            {data: 'nome'},
            {data: 'datainizio', type: "date-eu"},
            {data: 'datafine', type: "date-eu"},
            {data: 'datainserimento', type: "date-euro"},
            {data: 'azioni', orderable: false},
            {data: 'statovisual', visible: false},
            {data: 'presidente', visible: false},
            {data: 'protnomina', visible: false},
            {data: 'dataprotnomina', visible: false}
        ]
    });
    Fancybox.bind(".fan1", {
        groupAll: false, // Group all items
        on: {
            closing: (fancybox) => {
                refreshtable();
            }
        }
    });
});

function refreshtable() {
    table.ajax.reload(null, false);
}

function approvacorso(idcorso) {
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        title: 'Conferma Operazione',
        content: "Confermi di voler Autorizzare l'avvio del corso con ID <b>" + idcorso + "</b> ? L'operazione non potrà essere annullata.",
        theme: 'bootstrap',
        columnClass: 'col-md-9',
        buttons: {
            confirm: {
                btnClass: 'btn-success btn-lg',
                text: "<i class='fa fa-check'></i> CONFERMO", // With spaces and symbols
                action: function () {
                    $.ajax({
                        url: 'Operations',
                        type: 'POST',
                        data: {
                            'type': 'AUTORIZZAAVVIOCORSO',
                            'IDCORSO': idcorso
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
                                        refreshtable();
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
                            theme: 'bootstrap',
                            columnClass: 'col-md-9',
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
                btnClass: 'btn-danger btn-lg',
                text: "<i class='fa fa-remove'></i> NO" // With spaces and symbols                
            }
        }
    });
}

function approvacambiosede(idcorso) {
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        title: 'Conferma Operazione',
        content: "Confermi di voler Autorizzare il cambio sede del corso con ID <b>" + idcorso + "</b> ? L'operazione non potrà essere annullata.",
        theme: 'bootstrap',
        columnClass: 'col-md-9',
        buttons: {
            confirm: {
                btnClass: 'btn-success btn-lg',
                text: "<i class='fa fa-check'></i> CONFERMO", // With spaces and symbols
                action: function () {
                    $.ajax({
                        url: 'Operations',
                        type: 'POST',
                        data: {
                            'type': 'AUTORIZZACAMBIOSEDE',
                            'IDCORSO': idcorso
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
                                        refreshtable();
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
                            theme: 'bootstrap',
                            columnClass: 'col-md-9',
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
                btnClass: 'btn-danger btn-lg',
                text: "<i class='fa fa-remove'></i> NO" // With spaces and symbols                
            }
        }
    });
}
function impostadesignazione(idcorso) {
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        title: 'Conferma Operazione',
        content: "Confermi di voler predisporre il corso con ID <b>" + idcorso + "</b> nello stato 'IN DESIGNAZIONE PRESIDENTE'? L'operazione non potrà essere annullata.",
        theme: 'bootstrap',
        columnClass: 'col-md-9',
        buttons: {
            confirm: {
                btnClass: 'btn-success btn-lg',
                text: "<i class='fa fa-check'></i> CONFERMO", // With spaces and symbols
                action: function () {
                    $.ajax({
                        url: 'Operations',
                        type: 'POST',
                        data: {
                            'type': 'IMPOSTADESIGNAZIONE',
                            'IDCORSO': idcorso
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
                                        refreshtable();
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
                            theme: 'bootstrap',
                            columnClass: 'col-md-9',
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
                btnClass: 'btn-danger btn-lg',
                text: "<i class='fa fa-remove'></i> NO" // With spaces and symbols                
            }
        }
    });
}
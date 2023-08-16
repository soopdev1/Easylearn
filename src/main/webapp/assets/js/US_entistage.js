var table;

$(document).ready(function () {
    table = $('#tab_dt1').DataTable({
        dom: '<if<t>lp>',
        lengthMenu: [[50, 100, -1], [50, 100, "Tutto"]],
        order: [[1, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        },
        responsive: true,
        processing: true,
        ajax: {
            url: 'Search',
            type: 'POST',
            data: {'type': 'list_enti'}
        },
        columns: [
            {data: 'stato', orderable: false},
            {data: 'idallievo'},
            {data: 'cognome'},
            {data: 'nome'},
            {data: 'cf'},
            {data: 'data', type: "date-eu"},
            {data: 'azioni', orderable: false}
        ]
    });    
    Fancybox.bind(".fan1", {
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
    
});

function refreshtable() {
    table.ajax.reload(null, false);
}


function rimuoviallievo(idallievo,cf) {
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        title: 'Conferma Operazione',
        content: "Confermi di voler eliminare l'anagrafica dell'allievo con CF <b>" + cf + "</b> ? L'operazione non potrà essere annullata.",
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
                            'type': 'DELETEALLIEVO',
                            'IDALLIEVO': idallievo
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
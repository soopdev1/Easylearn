var table;
$(document).ready(function () {
    var sti = $('#statoistanza').val();
    var tip = $('#tipopercorso').val();
    table = $('#tab_dt1').DataTable({
        dom: '<if<t>lp>',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "Tutto"]],
        order: [[1, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        },
        responsive: true,
        processing: true,
        ajax: {
            url: 'Search',
            type: 'POST',
            data: function (d) {
                d.type = 'list_istanze_adm';
                d.statoistanza = $('#statoistanza').val();
                d.tipopercorso = $('#tipopercorso').val();
            }
        },
        columns: [
            {data: 'stato', orderable: false},
            {data: 'cognome'},
            {data: 'nome'},
            {data: 'cf'},
            {data: 'email'},
            {data: 'telefono'},
            {data: 'azioni', orderable: false}
        ]
    });
});
function refreshtable() {
    table.ajax.reload(null, false);
}




function approvaistanza(idistanza) {
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        columnClass: 'col-md-6',
        title: 'Conferma Operazione',
        content: "Confermi di voler APPROVARE l'istanza con ID " + idistanza + " ? L'operazione non potrà essere annullata.",
        theme: 'bootstrap',
        type: 'green',
        closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            confirm: {
                btnClass: 'btn-success',
                text: "<i class='fa fa-check'></i> CONFERMO", // With spaces and symbols
                action: function () {
                    $.ajax({
                        url: 'Operations',
                        type: 'POST',
                        data: {
                            'type': 'APPROVAISTANZA',
                            'IDISTANZA': idistanza
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
        }
    });
}

function rigettaistanza(idistanza) {
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        columnClass: 'col-md-12',
        title: 'Rigetta Istanza',
        content: '<form action="" class="formName">' +
                '<div class="row row-border col-md-12 p-5 checkboxesr">' +
                '<div class="form-check form-switch">' +
                '<input class="form-check-input" type="checkbox" role="switch" id="soccorsoistr" name="soccorsoistr" />' +
                '<label class="form-check-label" for="soccorsoistr">Abilita Soccorso Istruttorio</label>' +
                '</div>' +
                '</div>' +
                '<div class="form-group">' +
                '<label>Inserire indicazioni per il Soggetto Proponente o la motivazione del rigetto</label>' +
                '<textarea></textarea>' +
                '</div>' +
                '</form>',
        theme: 'bootstrap',
        type: 'red',
        closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            formSubmit: {
                btnClass: 'btn-success',
                text: "<i class='fa fa-check'></i> CONFERMO", // With spaces and symbols
                action: function () {
                    var name = this.$content.find('.name').val();
                    if (!name) {
                        $.alert('provide a valid name');
                        return false;
                    }
                    $.alert('Your name is ' + name);
                }
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
        },
        onOpenBefore: function () {
            // before the modal is displayed.
            tinymce.init({
                selector: 'textarea',
                toolbar: 'undo redo | bold italic underline',
                menubar: false
            });
        }
    });
}
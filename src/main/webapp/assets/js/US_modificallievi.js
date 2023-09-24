$(document).ready(function () {
    var allievimax = 20;
    try {
        allievimax = parseInt($('#allievimax').val());
    } catch (e) {
        allievimax = 20;
    }
    $('#ALLIEVI').select2({maximumSelectionSize: allievimax});
});


function modificastatoallievo(idallievo,nomecogn,stato) {
    
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        theme: 'bootstrap',
        columnClass: 'col-md-9',
        title: "Modifica stato allievo -  " + atob(nomecogn),
        content: '<form action="" class="formName">' +
                '<div class="form-group">' +
                '<label>Stato Attuale: <b>' + atob(stato) + '</b></label>' +
                '<select name="NUOVOSTATO" id="NUOVOSTATO" aria-label="Scegli..." data-control="select2" data-placeholder="Scegli..."'+
                'class="form-select fw-bold NUOVOSTATO" required>'+
                '<option value="">Scegli...</option>'+
                //'<option value="ATTIVO">ATTIVO</option>'+
                '<option value="RITIRATO">RITIRATO</option>'+
                '</select>' +
                '</div>' +
                '</form>',
        buttons: {
            formSubmit: {
                text: "<i class='fa fa-check'></i> SALVA MODIFICA",
                btnClass: 'btn-success',
                action: function () {
                    var NUOVOSTATO = this.$content.find('.NUOVOSTATO').val();
                    if (!NUOVOSTATO) {
                        $.alert('Inserire un valore corretto.');
                        return false;
                    } else {

                        var ok = false;
                        var messageko = "ERRORE GENERICO";
                        //submit
                        $.ajax({
                            url: "Operations",
                            type: "POST",
                            async: false,
                            dataType: 'json',
                            data: {
                                'type': "CAMBIASTATOALLIEVO",
                                'idallievo': idallievo,
                                'NUOVOSTATO': NUOVOSTATO
                            },
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
                                            window.location.reload();
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
            },
            cancel: {
                btnClass: 'btn-danger',
                text: "<i class='fa fa-remove'></i> CHIUDI" // With spaces and symbols                
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
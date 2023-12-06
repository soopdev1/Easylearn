function selezionaCT(idc) {
    var num_ore = $("#ctdurata_" + idc).val();
    var req_ing = $("#ctreqing_" + idc).val();
    if (req_ing === "0" || req_ing === 0) {
        $("#htmlctdurata_" + idc).html(num_ore);
    } else {
        $("#htmlctdurata_" + idc).html(0);
    }
    competenzetrasv();
}


function competenzetrasv() {
    var stage_dur = parseInt($("#stageduration").val());
    var inizio_ore = parseInt($("#startduration").val());
    $('.ctdurata').each(function () {
        inizio_ore += parseInt($(this).html());
    });
    $("#completeduration").html(inizio_ore);
    $("#completeduration2").html(inizio_ore);

    //  calcolo ore pian
    var pianificate = 0.0;
    var pianificateel = 0.0;
    var pianificatetec = 0.0;

    $(".value_ore").each(function () {
        pianificate += parseFloat($(this).val());
    });

    $(".value_oreel").each(function () {
        pianificateel += parseFloat($(this).val());
    });
    $(".value_oretec").each(function () {
        pianificatetec += parseFloat($(this).val());
    });
    
    
    

    $("#orepianificate").html(Number(pianificate).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, ""));
    $("#orepianificateel").html(Number(pianificateel).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, ""));
    $("#orepianificatec").html(Number(pianificatetec).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, ""));

    var orepian = parseFloat($("#orepianificate").html());
    $("#totaleorecompl").html(Number(inizio_ore+stage_dur).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, ""));

    var oredapianificare = inizio_ore - orepian;
    $("#oredapianificare").html(Number(oredapianificare).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, ""));

    if (oredapianificare === 0) {
        $('#addcalendariobutton').click(function () {
            return false;
        });
        $('#addcalendariobutton').addClass("disabled btn-secondary");
        $('#addcalendariobutton').removeClass("btn-primary");
    } else {
        $('#addcalendariobutton').click(function () {
            return true;
        });
        $('#addcalendariobutton').removeClass("disabled");
        $('#addcalendariobutton').removeClass("btn-secondary");
        $('#addcalendariobutton').addClass("btn-primary");
    }

    //var oredapian = parseInt($("#oredapianificare").html());



}

$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<<t>p>',
        lengthMenu: [[100, -1], [100, "Tutto"]],
        columnDefs: [
            {orderable: false, targets: 0},
            {orderable: false, targets: 1},
            {orderable: false, targets: 2},
            {orderable: false, targets: 3},
            {orderable: false, targets: 4},
            {orderable: false, targets: 5},
            {orderable: false, targets: 6},
            {orderable: false, targets: 7},
            {orderable: false, targets: 8}
        ],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
    $('#tab_dt2').DataTable({
        dom: '<<t>p>',
        lengthMenu: [[100, -1], [100, "Tutto"]],
        columnDefs: [
            {orderable: false, targets: 0},
            {orderable: false, targets: 1},
            {orderable: false, targets: 2},
            {orderable: false, targets: 3}
        ],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
    $('#tab_dt3').DataTable({
        dom: '<<t>p>',
        lengthMenu: [[100, -1], [100, "Tutto"]],
        columnDefs: [
            {orderable: true, targets: 0},
            {orderable: false, targets: 1},
            {orderable: false, targets: 2},
            {orderable: false, targets: 3},
            {orderable: false, targets: 4},
            {orderable: false, targets: 5}
        ],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
    Inputmask({
        mask: "999.999,99",
        digits: 1,
        numericInput: true
    }).mask(".decimalvalue");
});


function ELIMINAASSEGNAZIONEMODULO(iddocente,idcorso) {
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        title: 'Conferma Operazione',
        content: "Confermi di voler eliminare l'assegnazione selezionata? L'operazione non potrà essere annullata.",
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
                            'type': 'ELIMINAASSEGNAZIONEMODULO',
                            'IDDOCENTE': iddocente,
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

function ELIMINAMODULO(idmodulo,nomemodulo) {
    var ok = false;
    var messageko = "ERRORE GENERICO";
    $.confirm({
        title: 'Conferma Operazione',
        content: "Confermi di voler eliminare il modulo attualmente identificato come <b>" + nomemodulo + "</b> ? L'operazione non potrà essere annullata.",
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
                            'type': 'ELIMINAMODULO',
                            'IDMODULO': idmodulo
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
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

    //  calcolo ore pian
    var pianificate = 0.0;
    var pianificateel = 0.0;

    $(".value_ore").each(function () {
        pianificate += parseFloat($(this).val());
    });

    $(".value_oreel").each(function () {
        pianificateel += parseFloat($(this).val());
    });

    $("#orepianificate").html( Number(pianificate).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, ""));
    $("#orepianificateel").html( Number(pianificateel).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, ""));

    var orepian = parseFloat($("#orepianificate").html());
    $("#totaleorecompl").html( Number(inizio_ore + stage_dur).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, ""));

    var oredapianificare = inizio_ore - orepian;
    $("#oredapianificare").html( Number(oredapianificare).toLocaleString("it-IT", {minimumFractionDigits: 1}).replace(/[.,]0$/, ""));

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



function verificasalvataggiodati() {
    return true;
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
});
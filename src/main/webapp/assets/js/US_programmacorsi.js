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
    var pianificate = 0;

    $(".value_ore").each(function () {
        pianificate += parseInt($(this).val());
    });

    $("#orepianificate").html(pianificate);

    var orepian = parseInt($("#orepianificate").html());
    $("#totaleorecompl").html(inizio_ore + stage_dur);
    $("#oredapianificare").html(inizio_ore - orepian);
    
    //var oredapian = parseInt($("#oredapianificare").html());


}

function verificasalvataggiodati() {
    return true;
}





$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<<t>fp>',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "Tutto"]],
        columnDefs: [
            {orderable: false, targets: 0},
            {orderable: false, targets: 1},
            {orderable: false, targets: 2},
            {orderable: false, targets: 3},
            {orderable: false, targets: 4},
            {orderable: false, targets: 5},
            {orderable: false, targets: 6},
            {orderable: false, targets: 7}
        ],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
});
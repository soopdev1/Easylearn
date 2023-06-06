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
    var inizio_ore = parseInt($("#startduration").val());

    //alert($("#completeduration").html());
    $('.ctdurata').each(function () {
        inizio_ore += parseInt($(this).html());
    });
    $("#completeduration").html(inizio_ore);


}
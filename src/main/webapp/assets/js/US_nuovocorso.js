var ore_min = 0;
var ore_max = 0;
var stage_min = 0;
var stage_max = 0;
var eler_min = 0;
var eler_max = 0;


function selezionaCorso() {
    var scelta_rep = $("#repertoriochoice").val().split(";")[0];
    var scelta_att = $("#repertoriochoice").val().split(";")[1];
    $("#detail_corso").hide();
    $.ajax({
        type: "POST",
        url: "Operations",
        async: false,
        data: {
            "type": "SCELTAREPERTORIO",
            "val": scelta_rep,
            "val_att": scelta_att
        },
        success: function (data) {
            //response script here
            alert(data);
            var content = JSON.parse(data.split("@@@")[0]);
            $("#cont_label_area").html(content.area);
            $("#cont_label_sottoarea").html(content.sottoarea);
            $("#cont_label_professioni").html(content.professioni);
            $("#cont_label_livello").html(content.livelloeqf);
            $("#cont_label_cert").html(content.certificazione);

            var content_att = JSON.parse(data.split("@@@")[1]);
            $("#cont_label_ore").html(content_att.orelabel);
            $("#cont_label_stage").html(content_att.stagelabel);
            $("#cont_label_elearn").html(content_att.elelabel);
            $("#cont_label_assmax").html(content_att.assenzemax);

            $("#detail_corso").show();
            
            ore_min = content_att.oremin;
            ore_max = content_att.oremax;
            stage_min = content_att.stagemin;
            stage_max = content_att.stagemax;
            eler_min = content_att.elemin;
            eler_max = content_att.elemax;
            
            
        },
        error: function (error) {
            console.log("Error:");
            console.log(error);
        }
    });

}


function controllaINS() {
    const errorMessageEL =
            '<div class="alert alert-danger alert-dismissible fade show" role="alert"><strong>Attenzione</strong> La percentuale di ore in eLearning non rientra nel range previsto dal corso (0-30%). Inserire un valore in questo range.<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Chiudi avviso"></div>';
    const errorMessageAL =
            '<div class="alert alert-danger alert-dismissible fade show" role="alert"><strong>Attenzione</strong> Il Numero allievi indicato non rientra nel range previsto (1-20). Inserire un valore in questo range.<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Chiudi avviso"></div>';
    var elearning = $("#stageore").val();
    var numeroallievi = $("#numeroallievi").val();
    
    
    if (elearning > 30) {
        $('errorMsgContainer').alert();
        const errorWrapper = document.querySelector('#errorMsgContainer');
        errorWrapper.innerHTML = '';
        errorWrapper.innerHTML = errorMessageEL;
        errorWrapper.scrollIntoView();
        return false;
    } else if (numeroallievi > 20) {
        $('errorMsgContainer').alert();
        const errorWrapper = document.querySelector('#errorMsgContainer');
        errorWrapper.innerHTML = '';
        errorWrapper.innerHTML = errorMessageAL;
        errorWrapper.scrollIntoView();
        return false;
    }
    return true;
}

$(document).ready(function () {
    Inputmask({
        "placeholder": "000",
        "mask": "9",
        "repeat": 4,
        "greedy": false
    }).mask(".numbR");
});
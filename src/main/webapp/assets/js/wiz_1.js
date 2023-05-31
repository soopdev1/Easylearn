var ore_min = 0;
var ore_max = 0;
var stage_min = 0;
var stage_max = 0;
var eler_min = 0;
var eler_max = 0;

//ON CHANGE SELEZIONA CORSO REPERTORIO
function selezionaCorso() {
    var scelta_rep = $("#repertoriochoice").val().split(";")[0];
    var scelta_att = $("#repertoriochoice").val().split(";")[1];
    $("#detail_corso").hide();
    $("#detail_corso_s3").hide();
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
            var content = JSON.parse(data.split("@@@")[0]);
            $("#cont_label_area").html(content.area);
            $("#cont_label_sottoarea").html(content.sottoarea);
            $("#cont_label_professioni").html(content.professioni);
            $("#cont_label_livello").html(content.livelloeqf);
            $("#cont_label_cert").html(content.certificazione);

            $("#cont_label_area_s3").html(content.area);
            $("#cont_label_sottoarea_s3").html(content.sottoarea);
            $("#cont_label_professioni_s3").html(content.professioni);
            $("#cont_label_livello_s3").html(content.livelloeqf);
            $("#cont_label_cert_s3").html(content.certificazione);

            var content_att = JSON.parse(data.split("@@@")[1]);
            $("#cont_label_ore").html(content_att.orelabel);
            $("#cont_label_stage").html(content_att.stagelabel);
            $("#cont_label_elearn").html(content_att.elelabel);
            $("#cont_label_assmax").html(content_att.assenzemax);
            $("#cont_label_ore_s3").html(content_att.orelabel);
            $("#cont_label_stage_s3").html(content_att.stagelabel);
            $("#cont_label_elearn_s3").html(content_att.elelabel);
            $("#cont_label_assmax_s3").html(content_att.assenzemax);

            $("#detail_corso").show();
            $("#detail_corso_s3").show();

            ore_min = content_att.oremin;
            ore_max = content_att.oremax;
            stage_min = content_att.stagemin;
            stage_max = content_att.stagemax;
            eler_min = content_att.elemin;
            eler_max = content_att.elemax;

            var errorWrapper = document.querySelector('#errorMsgContainer');
            errorWrapper.innerHTML = '';
//            errorWrapper.scrollIntoView();

        },
        error: function (error) {
            var errorWrapper = document.querySelector('#errorMsgContainer');
            errorWrapper.innerHTML = '';
            errorWrapper.innerHTML = 'Error: ' + error;
            errorWrapper.scrollIntoView();
        }
    });

}


//ON CHANGE COMPETENZE TRASVERSALI
function selezionaCT(idcompetenza){
    alert(idcompetenza);
}


$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<t>',
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
            data: {'type': 'list_allievi'}
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


//WIZARD

var currentTab = 0; // Current tab is set to be the first tab (0)
showTab(currentTab); // Display the current tab

function showTab(n) {
    var x = document.getElementsByClassName("step");
    x[n].style.display = "block";
    if (n === 0) {
        document.getElementById("prevBtn").style.display = "none";
    } else {
        document.getElementById("prevBtn").style.display = "inline";
    }
    if (n === (x.length - 1)) {
        document.getElementById("nextBtn").innerHTML = "<i class='fa fa-save'></i> SALVA DATI";
    } else {
        document.getElementById("nextBtn").innerHTML = "<i class='fa fa-forward-step'></i> AVANTI";
    }
    fixStepIndicator(n);
}

function nextPrev(n) {
    var x = document.getElementsByClassName("step");
    if (n === 1 && !validateForm()) {
        return false;
    }
    x[currentTab].style.display = "none";
    currentTab = currentTab + n;
    if (currentTab >= x.length) {
        document.getElementById("signUpForm").submit();
        return false;
    }
    showTab(currentTab);
}

function validateForm() {
    var errorWrapper = document.querySelector('#errorMsgContainer');
    errorWrapper.innerHTML = '';
    errorWrapper.scrollIntoView();
    var x, y, i, z, valid = true;
    x = document.getElementsByClassName("step");
    y = x[currentTab].getElementsByTagName("input");
    for (i = 0; i < y.length; i++) {
        if (y[i].value === "") {
            y[i].className += " invalid";
            valid = false;
        }
    }
    z = x[currentTab].getElementsByTagName("select");
    for (i = 0; i < z.length; i++) {
        if (z[i].value === "") {
            $('errorMsgContainer').alert();
            var errorWrapper = document.querySelector('#errorMsgContainer');
            errorWrapper.innerHTML = '';
            errorWrapper.innerHTML = '<div class="alert alert-danger alert-dismissible fade show" role="alert"><strong>Attenzione</strong> Compilare tutti i campi!<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Chiudi avviso"></div>';
            errorWrapper.scrollIntoView();
            valid = false;
        }
    }

    if (currentTab === 2) { //ULTIMO STEP

        var elearning = parseInt($("#elearning").val());
        var numeroallievi = $("#numeroallievi").val();
        var stageore = parseInt($("#stageore").val());
        var maxallievi = parseInt($("#maxallievi").val());

        if (stageore < stage_min || stageore > stage_max) {
            $('errorMsgContainer').alert();
            const errorWrapper = document.querySelector('#errorMsgContainer');
            errorWrapper.innerHTML = '';
            errorWrapper.innerHTML = '<div class="alert alert-danger alert-dismissible fade show" role="alert"><strong>Attenzione</strong> La percentuale di ore in stage non rientra nel range previsto dal corso (' + stage_min + ' - ' + stage_max + '). Inserire un valore in questo range.<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Chiudi avviso"></div>';
            errorWrapper.scrollIntoView();
            valid = false;
        } else if (elearning < eler_min || elearning > eler_max) {
            $('errorMsgContainer').alert();
            const errorWrapper = document.querySelector('#errorMsgContainer');
            errorWrapper.innerHTML = '';
            errorWrapper.innerHTML = '<div class="alert alert-danger alert-dismissible fade show" role="alert"><strong>Attenzione</strong> La percentuale di ore in eLearning non rientra nel range previsto dal corso (' + eler_min + '% - ' + eler_max + '%). Inserire un valore in questo range.<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Chiudi avviso"></div>';
            errorWrapper.scrollIntoView();
            valid = false;
        } else if (numeroallievi > maxallievi) {
            $('errorMsgContainer').alert();
            const errorWrapper = document.querySelector('#errorMsgContainer');
            errorWrapper.innerHTML = '';
            errorWrapper.innerHTML = '<div class="alert alert-danger alert-dismissible fade show" role="alert"><strong>Attenzione</strong> Il Numero allievi indicato non rientra nel range previsto (1 - ' + maxallievi + '). Inserire un valore in questo range.<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Chiudi avviso"></div>';
            errorWrapper.scrollIntoView();
            valid = false;
        }

    }

    // If the valid status is true, mark the step as finished and valid:
    if (valid) {
        document.getElementsByClassName("stepIndicator")[currentTab].className += " finish";
    }

    return valid; // return the valid status
}

function fixStepIndicator(n) {
    var i, x = document.getElementsByClassName("stepIndicator");
    for (i = 0; i < x.length; i++) {
        x[i].className = x[i].className.replace(" active", "");
    }
    x[n].className += " active";
}

$(document).ready(function () {
    Inputmask({
        "placeholder": "000",
        "mask": "9",
        "repeat": 4,
        "greedy": false
    }).mask(".numbR");
});
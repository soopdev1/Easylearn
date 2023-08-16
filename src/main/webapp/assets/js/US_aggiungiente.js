/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function () {
    Inputmask({
        mask: "999999999999",
        digits: 1,
        numericInput: true
    }).mask(".intvalue");
    Inputmask("email").mask(".mailvalue");
});

function controllasalvataggio() {

    var CODICEFISCALE = $('#CODICEFISCALE').val();
    var EMAIL = $('#EMAIL').val();

    var ch1 = controllaCF(CODICEFISCALE);
    var ch2 = controllaEmail(EMAIL);

    var msg = "";
    if (!ch1) {
        msg = "CODICE FISCALE ERRATO, CONTROLLARE.";
    } else if (!ch2) {
        msg = "EMAIL ERRATA, CONTROLLARE.";
    }
    if (msg === "") {
        return true;
    } else {
        $.alert({
            title: "Errore durante l'operazione!",
            content: msg,
            type: 'red',
            typeAnimated: true,
            buttons: {
                confirm: {
                    text: 'OK',
                    btnClass: 'btn-red'
                }
            }
        });
        return false;
    }

}



function controllaCF(cf) {
    var validi, i, s, set1, set2, setpari, setdisp;
    if (cf === '') {
        return false;
    }
    cf = cf.toUpperCase();
    if (cf.length !== 16) {
        return false;
    }
    validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (i = 0; i < 16; i++) {
        if (validi.indexOf(cf.charAt(i)) === -1)
            return false;
    }
    set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
    s = 0;
    for (i = 1; i <= 13; i += 2) {
        s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    }
    for (i = 0; i <= 14; i += 2) {
        s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    }
    if (s % 26 !== cf.charCodeAt(15) - 'A'.charCodeAt(0)) {
        return false;
    }
    return true;
}

function controllaEmail(email) {
    if (email !== "") {
        var emailFilter = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$/;
        if (!emailFilter.test(email)) {
            return false;
        }
    }
    return true;
}
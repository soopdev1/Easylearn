$(document).ready(function () {
    Inputmask({
        mask: "99999999999",
        digits: 1,
        numericInput: true
    }).mask(".intvalue");
    Inputmask({
        mask: "99999",
        digits: 1,
        numericInput: true
    }).mask(".capvalue");
});

function controllasalvataggio() {
    var PIVA = $('#PIVA').val().trim();
    var msg = controllaPartitaIVA(PIVA);
    if (msg === "OK") {
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

function controllaPartitaIVA_OLD(pi) {
    if (pi === '') {
        return "IL CAMPO PARTITA IVA E' OBBLIGATORIO";
    } else if (!/^[0-9]{11}$/.test(pi)) {
        return 'IL CAMPO PARTITA IVA DEVE CONTENERE 11 NUMERI';
    } else {
        var s = 0;
        for (var i = 0; i <= 9; i += 2) {
            s += pi.charCodeAt(i) - '0'.charCodeAt(0);
        }
        for (var i = 1; i <= 9; i += 2) {
            var c = 2 * (pi.charCodeAt(i) - '0'.charCodeAt(0));
            if (c > 9) {
                c = c - 9;
            }
            s += c;
        }
        var controllo = (10 - s % 10) % 10;
        if (controllo !== pi.charCodeAt(10) - '0'.charCodeAt(0)) {
            return 'OK';
        }
        return "LA PARTITA IVA INSERITA NON E' FORMALMENTE CORRETTA. RIPROVARE.";
    }
}

function controllaPartitaIVA(pi)
{
    pi = pi.replace(/\s/g, "");
    if (pi.length === 0) {
        return "IL CAMPO PARTITA IVA E' OBBLIGATORIO";
    } else if (pi.length !== 11) {
        return 'IL CAMPO PARTITA IVA DEVE CONTENERE 11 NUMERI';
    }
    if (!/^[0-9]{11}$/.test(pi)) {
        return 'IL CAMPO PARTITA IVA DEVE CONTENERE 11 NUMERI';
    }
    var s = 0;
    for (var i = 0; i < 11; i++) {
        var n = pi.charCodeAt(i) - "0".charCodeAt(0);
        if ((i & 1) === 1) {
            n *= 2;
            if (n > 9)
                n -= 9;
        }
        s += n;
    }
    if (s % 10 !== 0) {
        return "LA PARTITA IVA INSERITA NON E' FORMALMENTE CORRETTA. RIPROVARE.";
    }
    return "OK";
}
$(document).ready(Fancybox.bind(".fan1", {
    groupAll: false, // Group all items
    hideScrollbar: false,
    on: {
        closing: (fancybox) => {
            window.location.reload();
        }
    },
    fullscreen: {
        autoStart: true
    }
}));


function checkNoSpecialChar(field) {
    field.value = RemoveAccents(field.value.toUpperCase());
    fieldNameSurnameNEW(field.id);
}

function fieldNameSurnameNEW(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`!#$%^&*+=-[]();,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°èéòàù§*ç@|!£$%&/()=?^€ì";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;
}

function RemoveAccents(str) {
    var accents = 'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž';
    var accentsOut = "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz";
    str = str.split('');
    var strLen = str.length;
    var i, x;
    for (i = 0; i < strLen; i++) {
        if ((x = accents.indexOf(str[i])) !== -1) {
            str[i] = accentsOut[x] + "'";
        }
        ;
    }
    return str.join('');
}

function fieldNOSPecial_1(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`!#$%^&*+=-[];,{}()|\":<>?£ààáâãäçèéêëìíîïñòóôõö€ùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°€";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    document.getElementById(fieldid).value = stringToReplace;
}



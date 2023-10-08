$(document).ready(function () {
    populatedatafine(document.getElementById('orai'));
});

function populatedatafine(component) {
    $('#oraf').val(null).trigger('change');
    var start = component.value;
    if (start !== "") {
        for (var i = 8; i < 21; i++) {
            var v0 = new Date('1/1/2000 ' + start + ':00');
            var v1 = new Date('1/1/2000 ' + i + ':00:00');
            var v2 = new Date('1/1/2000 ' + i + ':30:00');

            if (v1 > v0) {
                var out1 = i + ":00";
                if (i < 10) {
                    out1 = "0" + i + ":00";
                }
                var newOption = new Option(out1, out1, false, false);
                $('#oraf').append(newOption).trigger('change');
            }
            if (v2 > v0) {
                var out1 = i + ":30";
                if (i < 10) {
                    out1 = "0" + i + ":30";
                }
                var newOption = new Option(out1, out1, false, false);
                $('#oraf').append(newOption).trigger('change');
            }
        }
    }
}

function selectsede() {
    $.ajax({
        url: "Search",
        type: "POST",
        async: false,
        data: {
            'type': "LISTSEDESTAGE",
            'IDENTE': $("#ENTESTAGE").val()
        },
        success: function (data) {
            $("#SEDESTAGE").html('');
            $("#SEDESTAGE").html(data);
        },
        error: function (data) {
            console.log("ERRORE: " + data);
        }
    });
}
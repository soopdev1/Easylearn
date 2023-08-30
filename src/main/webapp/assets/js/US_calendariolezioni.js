$(document).ready(function () {
    setstartdate("data", 0, "min", $('#date_start').val());
    setstartdate("data", 0, "max", $('#date_end').val());
});


function setstartdate(id, addday, attr, startdate) {
    var dtToday = new Date();
    if (startdate !== null) {
        dtToday = new Date(startdate);
    }
    dtToday.setDate(dtToday.getDate() + addday);
    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if (month < 10) {
        month = '0' + month.toString();
    }
    if (day < 10) {
        day = '0' + day.toString();
    }
    var maxDate = year + '-' + month + '-' + day;
    $('#' + id).attr(attr, maxDate);
}

function checkdatisalvati() {
    return true;
}

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
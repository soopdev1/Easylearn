$(document).ready(function () {
    $('#ISTANZA').change(
            function () {
                $.ajax({
                    type: "POST",
                    url: "Search",
                    data: {
                        "type": "LISTCORSIISTANZA",
                        "IDIST": $(this).val()
                    },
                    success: function (data) {
                        $("#CORSO").html(data);
                    }
                });
            }
    );
});
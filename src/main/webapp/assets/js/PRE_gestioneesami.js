$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<if<t>lp>',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "Tutto"]],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        },
        columnDefs: [
            {
                targets: 5,
                orderable: false
            },
            {
                targets: 6,
                orderable: false
            },
            {
                targets: 7,
                orderable: false
            },
            {
                targets: 8,
                orderable: false
            }
        ]
    });
    Inputmask({
        mask: "9999",
        digits: 1,
        numericInput: true
    }).mask(".intvalue");
    Inputmask({
        mask: "999",
        digits: 1,
        numericInput: true
    }).mask(".intvalue3");
    $('.form-select').select2({
        language: "it"
    });
});
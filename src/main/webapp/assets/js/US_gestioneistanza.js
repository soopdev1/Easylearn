$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<if<t>lp>',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "Tutto"]],
        columnDefs: [
            {orderable: false, targets: 0},
            {orderable: false, targets: 1},
            {orderable: false, targets: 2},
            {type: "date-eu", targets: 3},
            {orderable: false, targets: 4}
        ],
        order: [[3, 'desc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
});
$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<t>',
        lengthMenu: [[100, -1], [100, "Tutto"]],
        columnDefs: [
            {orderable: false, targets: 0},
            {orderable: false, targets: 1},
            {orderable: false, targets: 2},
            {orderable: false, targets: 3},
            {orderable: false, targets: 4}
        ],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
    $('#tab_dt2').DataTable({
        dom: '<t>',
        lengthMenu: [[100, -1], [100, "Tutto"]],
        columnDefs: [
            {orderable: false, targets: 0},
            {orderable: false, targets: 1},
            {orderable: false, targets: 2},
            {orderable: false, targets: 3},
            {orderable: false, targets: 4}
        ],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
});
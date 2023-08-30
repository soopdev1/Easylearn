$(document).ready(function () {
    $('#tab_dt0').DataTable({
        dom: '<t>',
        lengthMenu: [[100, -1], [100, "Tutto"]],
        columnDefs: [
            {orderable: false, targets: 0},
            {orderable: false, targets: 1},
            {orderable: false, targets: 2},
            {orderable: false, targets: 3}
        ],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
    $('#tab_dt1').DataTable({
        dom: '<t>',
        lengthMenu: [[100, -1], [100, "Tutto"]],
        columnDefs: [
            {orderable: false, targets: 0},
            {orderable: false, targets: 1},
            {orderable: false, targets: 2},
            {orderable: false, targets: 3}
        ],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        }
    });
    Fancybox.bind(".fan1", {
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
    });
});
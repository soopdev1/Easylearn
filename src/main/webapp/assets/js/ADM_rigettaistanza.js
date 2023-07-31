$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<if<t>lp>',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "Tutto"]],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        },
        responsive: true,
        order: [[3, 'desc']],
        columnDefs: [
            {orderable: true, targets: 0},
            {orderable: false, targets: 1},
            {orderable: true, targets: 2},
            {type: "date-euro", orderable: true, targets: 3},
            {orderable: true, targets: 4},
            {orderable: false, targets: 5},
            {orderable: false, targets: 6}
        ]
    });
    tinymce.init({
        selector: 'textarea',
        toolbar: 'undo redo | bold italic underline',
        menubar: false,
         max_width: 500
    });
});

function abilitasoccorsoistr() {
    if ($('#soccorsoistr').is(":checked")) {
        $('#abilitasoccorso').css('display', '');
    } else {
        $('#abilitasoccorso').css('display', 'none');
    }
}
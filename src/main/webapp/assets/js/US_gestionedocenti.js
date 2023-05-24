$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<if<t>lp>',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "Tutto"]],
        order: [[1, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        },
        responsive: true,
        processing: true,
        ajax: {
            url: 'Search',
            type: 'POST',
            data: {'type': 'list_docenti'}
        },
        columns: [
            {data: 'stato', orderable: false},
            {data: 'cognome'},
            {data: 'nome'},
            {data: 'cf'},
            {data: 'titolo'},
            {data: 'email'},
            {data: 'telefono'},
            {data: 'azioni', orderable: false}
        ]
    });
});
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
            data: {'type': 'list_sedi_soggetto'}
        },
        columns: [
            {data: 'stato', orderable: false},
            {data: 'tipo'},
            {data: 'indirizzo'},
            {data: 'cap'},
            {data: 'comune'},
            {data: 'provincia'},
            {data: 'azioni', orderable: false}
        ]
    });
});
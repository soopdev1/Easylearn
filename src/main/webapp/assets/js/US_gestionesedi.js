$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<Bif<t>lp>',
        buttons: [
            {
                extend: 'excelHtml5',
                className: 'btn btn-sm btn-primary',
                text: '<i class="fa fa-file-excel"></i>',
                titleAttr: 'Esporta in Excel',
                exportOptions: {
                    columns: [6, 1, 2, 3, 4,5] //Your Column value those you want
                }
            }
        ],
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
            {data: 'statovisual',visible:false}
        ]
    });
});
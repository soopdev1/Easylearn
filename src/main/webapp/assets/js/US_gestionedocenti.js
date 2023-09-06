$(document).ready(function () {
    $('#tab_dt1').DataTable({
        dom: '<Bif<t>lp>',
        buttons: [
            {
                extend: 'excelHtml5',
                className: 'btn btn-sm btn-primary',
                text: '<i class="fa fa-file-excel"></i>',
                titleAttr: 'Esporta in Excel'
            }
        ],
        lengthMenu: [[50, 100, -1], [50, 100, "Tutto"]],
        order: [[0, 'asc']],
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
            {data: 'cognome'},
            {data: 'nome'},
            {data: 'cf'},
            {data: 'titolo'},
            {data: 'profiloprof'}
        ]
    });
    $('#tab_dt2').DataTable({
        dom: '<Bif<t>lp>',
        buttons: [
            {
                extend: 'excelHtml5',
                className: 'btn btn-sm btn-primary',
                text: '<i class="fa fa-file-excel"></i>',
                titleAttr: 'Esporta in Excel'
            }
        ],
        lengthMenu: [[50, 100, -1], [50, 100, "Tutto"]],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        },
        responsive: true,
        processing: true,
        ajax: {
            url: 'Search',
            type: 'POST',
            data: {'type': 'list_altropersonale'}
        },
        columns: [
            {data: 'cognome'},
            {data: 'nome'},
            {data: 'cf'},
            {data: 'titolo'},
            {data: 'profiloprof'}
        ]
    });
});
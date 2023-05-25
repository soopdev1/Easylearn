$(document).ready(function () {
    $('#tab_dt1').DataTable({//tipologiapercorso
        dom: '<if<t>lp>',
        lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "Tutto"]],
        order: [[0, 'asc']],
        language: {
            url: 'assets/plugins/DataTables/it-IT.json'
        },
        responsive: true,
        processing: true,
        ajax: {
            url: 'Edit',
            type: 'POST',
            data: {'type': 'backoffice_tipologiapercorso'}
        },
        columns: [
            {data: 'tipo'},
            {data: 'descrizione'},
            {data: 'datastart', type: 'date-eu'},
            {data: 'dataend', type: 'date-eu'},
            {data: 'stato'},
            {data: 'azioni', orderable: false}
        ]
    });
});
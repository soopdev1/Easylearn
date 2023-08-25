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
            url: 'Search',
            type: 'POST',
            data: {'type': 'BO_LIST_TIPOLOGIAPERCORSO'}
        },
        columns: [
            {data: 'tipo'},
            {data: 'descrizione'},
            {data: 'datastart', type: 'date-eu'},
            {data: 'dataend', type: 'date-eu'},
            {data: 'maxcorsi'},
            {data: 'maxedizioni'},
            {data: 'stato'},
            {data: 'azioni', orderable: false}
        ]
    });
    tinymce.init({
        selector: 'textarea',
        toolbar: 'undo redo | bold italic underline',
        menubar: false,
        branding: false,
        statusbar: false,
        max_width: 500
    });
});
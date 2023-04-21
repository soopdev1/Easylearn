"use strict";
var KTSigninGeneral = function () {
    var t, e, i;
    return{init: function () {
            t = document.querySelector("#kt_sign_in_form"),
                    e = document.querySelector("#kt_sign_in_submit"),
                    i = FormValidation.formValidation(t,
                            {fields: {username: {validators: {notEmpty: {message: "Campo Obbligatorio!"}}},
                                    password: {validators: {notEmpty: {message: "Campo Obbligatorio!"}}}},
                                plugins: {trigger: new FormValidation.plugins.Trigger,
                                    bootstrap: new FormValidation.plugins.Bootstrap5({rowSelector: ".fv-row"})}}),
                    e.addEventListener("click", (function (n) {
                        n.preventDefault(),
                                i.validate().then((function (i) {
                            "Valid" === i ? (e.setAttribute("data-kt-indicator", "on"), e.disabled = !0,
                                    $.post("LoginOperations",
                                            {
                                                type: "login",
                                                username: $("input[name=username]").val(),
                                                password: $("input[name=password]").val()
                                            }
                                    )
                                    .done(function (resp) {
                                        if (resp.startsWith("ER")) {
                                            swalError('Errore', resp);
                                            e.setAttribute("data-kt-indicator", "off");
                                            e.disabled = 0;
                                        } else {
                                            window.location.href = resp;
                                        }
                                    }
                                    )) : Swal.fire({text: "Si è verificato un errore. Riprovare.", icon: "error",
                                buttonsStyling: !1, confirmButtonText: "OK!", customClass: {confirmButton: "btn btn-primary"}});
                        }));
                    }));
        }};
}();
KTUtil.onDOMContentLoaded((function () {
    KTSigninGeneral.init();
}));


function swalSuccess(title, HTMLmessage) {
    Swal.fire({
        title: '<h2 class="kt-font-io-n"><b>' + title + '</b></h2><br>',
        html: "<h4>" + HTMLmessage + "</h4><br>",
        icon: "success",
        "confirmButtonColor": '#363a90',
        confirmButtonText: "OK!",
        customClass: {confirmButton: "btn btn-primary"}
    });
}
function swalError(title, message) {
    Swal.fire({
        text: message,
        html: title,
        icon: "error",
        buttonsStyling: !1,
        confirmButtonText: "OK!",
        customClass: {confirmButton: "btn btn-primary"}}
    );
}
function swalWarning(title, message) {
    Swal.fire({
        "title": title,
        "html": message,
        "type": "warning",
        cancelButtonClass: "btn btn-secondary",
        customClass: {
            container: 'my-swal'
        }
    });
}
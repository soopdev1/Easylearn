$(document).ready(function () {
    Inputmask({
        mask: "99",
        digits: 1,
        numericInput: true
    }).mask(".intvalue");
});
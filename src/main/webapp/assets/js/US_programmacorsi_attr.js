$(document).ready(function () {
    Inputmask({
        mask: "99999",
        digits: 1,
        numericInput: true
    }).mask(".intvalue");
});
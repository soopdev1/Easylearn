$(document).ready(Fancybox.bind(".fan1", {
    groupAll: false, // Group all items
    on: {
        closing: (fancybox) => {
            window.location.reload();
        }
    }
}));
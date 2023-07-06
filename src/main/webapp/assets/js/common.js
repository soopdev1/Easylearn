$(document).ready(Fancybox.bind(".fan1", {
    groupAll: false, // Group all items
    hideScrollbar: false,
    on: {
        closing: (fancybox) => {
            window.location.reload();
        }
    },
    fullscreen: {
        autoStart: true
    }
}));
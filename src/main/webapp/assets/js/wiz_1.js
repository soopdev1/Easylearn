var currentTab = 0; // Current tab is set to be the first tab (0)
showTab(currentTab); // Display the current tab

function showTab(n) {
    // This function will display the specified tab of the form...
    var x = document.getElementsByClassName("step");
    x[n].style.display = "block";
    //... and fix the Previous/Next buttons:
    if (n === 0) {
        document.getElementById("prevBtn").style.display = "none";
    } else {
        document.getElementById("prevBtn").style.display = "inline";
    }
    if (n === (x.length - 1)) {
        document.getElementById("nextBtn").innerHTML = "<i class='fa fa-save'></i> Salva";
    } else {
        document.getElementById("nextBtn").innerHTML = "<i class='fa fa-forward-step'></i> Avanti";
    }
    fixStepIndicator(n);
}

function nextPrev(n) {
    // This function will figure out which tab to display
    var x = document.getElementsByClassName("step");
    alert(currentTab);
    // Exit the function if any field in the current tab is invalid:
    if (n === 1 && !validateForm()) {
        return false;
    }
    // Hide the current tab:
    x[currentTab].style.display = "none";
    // Increase or decrease the current tab by 1:
    currentTab = currentTab + n;
    // if you have reached the end of the form...
    if (currentTab >= x.length) {
        // ... the form gets submitted:
        document.getElementById("signUpForm").submit();
        return false;
    }
    // Otherwise, display the correct tab:
    showTab(currentTab);
}

function validateForm() {
    // This function deals with validation of the form fields
    var x, y, i, z, valid = true;
    x = document.getElementsByClassName("step");
    y = x[currentTab].getElementsByTagName("input");
    // A loop that checks every input field in the current tab:
    for (i = 0; i < y.length; i++) {
        // If a field is empty...
        if (y[i].value === "") {
            y[i].className += " invalid";
            valid = false;
        }
    }

    z = x[currentTab].getElementsByTagName("select");

    for (i = 0; i < z.length; i++) {
        // If a field is empty...
        if (z[i].value === "") {

            var errorMessageEL = '<div class="alert alert-danger alert-dismissible fade show" role="alert"><strong>Attenzione</strong> Compilare tutti i campi!<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Chiudi avviso"></div>';
            $('errorMsgContainer').alert();
            var errorWrapper = document.querySelector('#errorMsgContainer');
            errorWrapper.innerHTML = '';
            errorWrapper.innerHTML = errorMessageEL;
            errorWrapper.scrollIntoView();
            valid = false;
        }
    }

    // If the valid status is true, mark the step as finished and valid:
    if (valid) {
        document.getElementsByClassName("stepIndicator")[currentTab].className += " finish";
    }
    return valid; // return the valid status
}

function fixStepIndicator(n) {
    // This function removes the "active" class of all steps...
    var i, x = document.getElementsByClassName("stepIndicator");
    for (i = 0; i < x.length; i++) {
        x[i].className = x[i].className.replace(" active", "");
    }
    //... and adds the "active" class on the current step:
    x[n].className += " active";
}
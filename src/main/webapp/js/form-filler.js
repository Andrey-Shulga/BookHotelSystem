// Run on page load
window.onload = function () {

    // If sessionStorage is storing default values (ex. name), exit the function and do not restore data
    if (sessionStorage.getItem('name') == "name") {
        return;
    }

    // If values are not blank, restore them to the fields
    var login = sessionStorage.getItem('login');
    if (name !== null) $('#inputLogin').val(login);


}

// Before refreshing the page, save the form data to sessionStorage
window.onbeforeunload = function () {
    sessionStorage.setItem("login", $('#inputLogin').val());

}


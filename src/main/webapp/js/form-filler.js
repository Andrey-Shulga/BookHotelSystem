// Run on page load
window.onload = function () {



    // If values are not blank, restore them to the fields
    var login = sessionStorage.getItem('login');
    if (login !== 'undefined') $('#inputLogin').val(login);

    var firstName = sessionStorage.getItem('firstName');
    if (firstName !== 'undefined') $('#inputFirstName').val(firstName);

    var lastName = sessionStorage.getItem('lastName');
    if (lastName !== 'undefined') $('#inputLastName').val(lastName);

    var email = sessionStorage.getItem('email');
    if (email !== 'undefined') $('#inputEmail').val(email);

    var phone = sessionStorage.getItem('phone');
    if (phone !== 'undefined') $('#inputPhone').val(phone);

    var orderId = sessionStorage.getItem('orderId');
    if (orderId !== 'undefined') $('#inputOrderId').val(orderId);

    var roomNumber = sessionStorage.getItem('roomNumber');
    if (roomNumber !== 'undefined') $('#inputRoomNumber').val(roomNumber);

    var roomPrice = sessionStorage.getItem('roomPrice');
    if (roomPrice !== 'undefined') $('#inputRoomPrice').val(roomPrice);

}

// Before refreshing the page, save the form data to sessionStorage
window.onbeforeunload = function () {
    sessionStorage.setItem('login', $('#inputLogin').val());
    sessionStorage.setItem('firstName', $('#inputFirstName').val());
    sessionStorage.setItem('lastName', $('#inputLastName').val());
    sessionStorage.setItem('email', $('#inputEmail').val());
    sessionStorage.setItem('phone', $('#inputPhone').val());
    sessionStorage.setItem('orderId', $('#inputOrderId').val());
    sessionStorage.setItem('roomNumber', $('#inputRoomNumber').val());
    sessionStorage.setItem('roomPrice', $('#inputRoomPrice').val());


}


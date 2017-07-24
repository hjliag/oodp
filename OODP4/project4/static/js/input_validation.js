// validate contact input from the client side
function validateContact(form) {
    var name = form.name;
    var number = form.number;
    var address = form.address;
    var email = form.email;
    var memo = form.memo;

    if(!requiredFieldValidation(name) && !requiredFieldValidation(number)){
        alert("please enter required field!");
        return false;
    }
    if(numberValidation(number)){
        alert("invalid number!");
        number.focus();
        return false;
    }
    if(requiredFieldValidation(email)){
        if(!emailValidation(email)){
            alert("invalid email!");
            email.focus();
            return false;
        }
    }
    return true;
}

// return false if the field is empty
function requiredFieldValidation(field) {
    if (field.value.trim() == "" || field.value == null) {
        return false;
    }
    return true;
}

// return true if the number is valid
function numberValidation(number) {
    if(/\D/.test(number.value)){
        return true;
    }
    return false;
}

// return true if the email is valid
function emailValidation(email){
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(email.value)){
        return false;
    }
    return true;
}

function validateSMS(form) {

}

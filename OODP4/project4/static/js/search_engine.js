// Search function for contract_main.html
function search_contact(text) {
    $('li.contact').each(function(i) {
        if ($(this).text().indexOf(text) == -1) {
            $(this).hide();
        } else {
            $(this).show();
        }
    });
};
// this funciton is activated by every input
$(window).ready(function() {
    $('#search_contact').on('input', function(e) {
        search_contact($('#search_contact').val());
    });
});


// Search function for call_main.html
function search_call(text) {
    $('li.call').each(function(i) {
        if ($(this).text().indexOf(text) == -1) {
            $(this).hide();
        } else {
            $(this).show();
        }
    });
};
// this funciton is activated by every input
$(window).ready(function() {
    $('#search_call').on('input', function(e) {
        search_call($('#search_call').val());
    });
});


// Search function for sms_main.html
function search_sms(text) {
    $('li.sms').each(function(i) {
        if ($(this).text().indexOf(text) == -1) {
            $(this).hide();
        } else {
            $(this).show();
        }
    });
};
// this funciton is activated by every input
$(window).ready(function() {
    $('#search_sms').on('input', function(e) {
        search_sms($('#search_sms').val());
    });
});

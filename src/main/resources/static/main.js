jQuery(document).ready(function () {

    $("#compile-post-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

         $('#compile-output').html("wait...");
         $("#btn-submit-compile").prop("disabled", true);

        setTimeout(fire_ajax_submit, 16000);

    });

});

function fire_ajax_submit() {
    var search = $("#postId").val();
    //var search = {}
    //search["postId"] = $("#postId").val();
    var token =  $('input[name="_csrf"]').attr('value');

    $.ajax({
            type: "POST",
            contentType: "application/json",
            headers: {
            'X-CSRF-Token': token
                         },
            url: "/api/search",
            data: search,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {

                var json = JSON.stringify(data, null, 4);
                $('#compile-output').html(json);

                console.log("SUCCESS : ", data);
                $("#btn-submit-compile").prop("disabled", false);

            },
            error: function (e) {

                var json = e.responseText;
                $('#compile-output').html(json);

                console.log("ERROR : ", e);
                $("#btn-submit-compile").prop("disabled", false);

            }
        });
}
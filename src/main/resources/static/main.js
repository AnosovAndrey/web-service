jQuery(document).ready(function () {

    $("#compile-post-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

         $("#btn-submit-compile").prop("disabled", true);

        compile_ajax_submit();

        setTimeout(fire_ajax_submit, 20000);

    });

});

function compile_ajax_submit() {
    var postId = $("#postId").val();
    var token =  $('input[name="_csrf"]').attr('value');
    var input = $("#compile-form-input").val();
    var compiler = $("#compile-form-select").val();

    $.ajax({
            type: "POST",
            contentType: "application/json",
            headers: {
                'X-CSRF-Token': token
            },
            url: "/compile/post/" + postId + "/" + compiler,
            data: input,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {

                var json = JSON.stringify(data, null, 4);
                $('#compile-output').html(json);

                console.log("SUCCESS : ", data);

            },
            error: function (e) {

                var json = e.responseText;
                $('#compile-output').html(json);

                console.log("ERROR : ", e);

            },
            complete: function () {
                  setTimeout(function () {
                              $('#compile-output').html("wait...");
                          } , 5000);
             }
        });
}

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
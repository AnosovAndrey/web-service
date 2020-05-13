jQuery(document).ready(function () {

    $("#compile-post-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

         $("#btn-submit-compile").prop("disabled", true);

        compile_ajax_submit();
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
            dataType: 'text',
            cache: false,
            timeout: 600000,
            success: function (data) {

                $('#compile-output').html(data);

                console.log("SUCCESS : ", data);
                setTimeout(fire_ajax_submit, 1000);
            },
            error: function (e) {

                var json = e.responseText;
                $('#compile-output').html(json);

                console.log("ERROR : ", e);

            }
        });
}

function fire_ajax_submit() {
    var search = $("#postId").val();
    var token =  $('input[name="_csrf"]').attr('value');
    var version = $("#postCompileVersion").val();

     var countTime = 0;
     var storeTimeInterval = setInterval(function(){
            ++countTime;
            $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    headers: {
                    'X-CSRF-Token': token
                                 },
                    url: "/api/search/"  + version,
                    data: search,
                    dataType: 'text',
                    cache: false,
                    timeout: 600000,
                    success: function (data) {
                        if(countTime == 21){
                            clearInterval(storeTimeInterval);
                            console.log("SUCCESS : ", data);
                            $("#btn-submit-compile").prop("disabled", false);
                        }

                        if(data !== "wait..."){
                            clearInterval(storeTimeInterval);
                            console.log("SUCCESS : ", data);
                            $("#btn-submit-compile").prop("disabled", false);
                        }

                        $('#compile-output').html(data);
                        $('#compile-output').outputEdit();
                    },
                    error: function (e) {

                            clearInterval(storeTimeInterval);
                            var json = e.responseText;
                            $('#compile-output').html(json);

                            console.log("ERROR : ", e);
                            $("#btn-submit-compile").prop("disabled", false);
                    }
            });
     }, 1000);
}
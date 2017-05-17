<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 20.04.2017
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<meta charset="utf-8">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.35.4/js/bootstrap-dialog.min.js"></script>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.9/js/bootstrap-dialog.min.js"></script>--%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>



<!-- Latest compiled and minified CSS -->
<%--
<link rel="stylesheet" href="https://bootswatch.com/paper/bootstrap.min.css">
--%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<!-- Facebook and other appl icons -->
<link rel="stylesheet" href="https://lipis.github.io/bootstrap-social/bootstrap-social.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- Google icons -->
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">



<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>



<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });

    function deleteRequest(requestBody) {
        BootstrapDialog.confirm({
            title: 'Confirmation on delete ...',
            message: "Continue this operation?",
            type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
            closable: true, // <-- Default value is false
            draggable: true, // <-- Default value is false
            btnCancelLabel: 'Cancel', // <-- Default value is 'Cancel',
            btnOKLabel: 'Confirm', // <-- Default value is 'OK',
            btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
            callback: function(result){
                if(result) {
                    var request = new XMLHttpRequest();
                    request.open("GET", ${pageContext.request.contextPath} + '/' + requestBody, true);
                    request.addEventListener("load", function() {
                        console.log(request.responseText);
                        if (request.responseText != ""){
                            BootstrapDialog.alert({
                                title: 'WARNING',
                                message: request.responseText,
                                type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
                                closable: true, // <-- Default value is false
                                draggable: true, // <-- Default value is false
                                buttonLabel: 'Ok', // <-- Default value is 'OK',
                                callback: function(result) {
                                    // result will be true if button was click, while it will be false if users close the dialog directly.
                                }
                            });
                        } else {
                            window.location.reload();
                        }
                    });
                    request.send(null);
                }
            }
        });
    };
</script>


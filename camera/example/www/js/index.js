; $(function() {
    "use strict";

    $('#take-photo').on('singletap', function() {
        axemas.call('open-camera');
    });

    axemas.register("send-path-to-js", function(data){
        document.getElementById('image-div').style.backgroundImage = 'url(' + data.path + ')';
    });

});
$(function() {

    $('#with-sidebar').on('singletap', function() {
        axemas.goto({'url':'www/second.html', 'toggleSidebarIcon': 'slide_icon' });
    });

    $('#get-info').on('singletap', function() {
        axemas.call('get-native-info','',function(result){
            axemas.alert("Native data",'Navigation stack size: ' + result.items + '\nDevice name: '+ result.device_name);
        });
    });

    $('#issue-call').on('singletap', function() {
        axemas.call('call-number',{'number': document.getElementById('number-of-views').value});
    });

    axemas.register("time-from-native", function(data){
        document.getElementById("time-div").innerHTML = data.current_time;
    });

    $('#continue-tutorial').on('singletap', function() {
        axemas.call('push-native-view', {'close': false});
    });

});
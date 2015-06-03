$(function() {

    $('#with-sidebar').on('singletap', function() {
        axemas.goto({'url':'www/second.html', 'toggleSidebarIcon': 'slide_icon' });
    });

    $('#no-sidebar').on('singletap', function() {
        axemas.goto({'url':'www/second.html'});
    });

    $('#push-and-clear-stack').on('singletap', function() {
        axemas.goto({'url':'www/second.html', 'stackMaintainedElements': 0});
    });

});
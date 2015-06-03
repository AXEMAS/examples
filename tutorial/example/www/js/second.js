$(function() {

    $('#maintain-views').on('singletap', function() {
        axemas.goto({'url':'www/index.html', 'stackMaintainedElements': document.getElementById('number-of-views').value});
    });

    $('#pop-views').on('singletap', function() {
        axemas.goto({'url':'www/index.html', 'stackPopElements': document.getElementById('number-of-views').value});
    });


    $('#continue-tutorial').on('singletap', function() {
        axemas.goto({'url':'www/native.html', 'toggleSidebarIcon': 'slide_icon', 'stackMaintainedElements': 0});
    });

});
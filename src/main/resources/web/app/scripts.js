/* menu-top auto-hide */
$(document).click(function(event) {
  if ($('#navbar-collapse-top').hasClass('in')) {
    $('.navbar-collapse').collapse('hide');
  }
});

/* notify.js settings, url: https://notifyjs.com/ */
$.notify.defaults({
  autoHide: false,
  globalPosition: 'bottom right',
  className: 'success'
});
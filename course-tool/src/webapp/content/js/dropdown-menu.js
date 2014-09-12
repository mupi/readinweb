$(document).ready(function(){
	// Collapse accordion every time dropdown is shown
	$('#lista_menu_modulos').on('show.bs.dropdown', function (event) {
	  $(this).find('.panel-collapse.in').collapse('hide');
	});

	// Prevent dropdown to be closed when we click on an accordion link
	$('#lista_menu_modulos').on('click', 'a[data-toggle="collapse"]', function (event) {
	  event.preventDefault();
	  event.stopPropagation();
	  $($(this).data('parent')).find('.panel-collapse.in').collapse('hide');
	  $($(this).attr('href')).collapse('show');
	});
});
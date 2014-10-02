var indice_affix = function() {
	$('#indice-gramatica').affix({
    offset: {
      top: 500
    , bottom: function () {
        return (this.bottom = $('.footer').outerHeight(true))
      }
    }
  });
};

$(document).ready( function() {
	indice_affix;
});
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
        //indice_affix;
        $("a[href='#topo']").click(function(e){
                e.preventDefault();
                $("html,body", window.parent.document).scrollTop(0);
                window.parent.location.hash="#topo";
                return true; 
                });
        $("#lista_indice_gramatica ul li a").click(function(e){
                e.preventDefault();
                $("html, body", window.parent.document).scrollTop($($(this).attr("href")).offset().top);
                window.parent.location.hash=$(this).attr("href");
                return true; 
                });
        });

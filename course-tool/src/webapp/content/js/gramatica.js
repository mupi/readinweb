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
        $("a[href='#topo']").click(function(){
                $("html,body", window.parent.document).scrollTop(0);
                return false;
                });
        $("#lista_indice_gramatica ul li a").click(function(){
                console.log("oi oi");
                /*$("html, body", window.parent.document).animate({
                 *                         'scrollTop':   $($(this).attr("href")).offset().top
                 *                                         }, 2000);*/
                $("html, body", window.parent.document).scrollTop($($(this).attr("href")).offset().top);
                });
        });

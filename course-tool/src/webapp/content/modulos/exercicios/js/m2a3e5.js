
  $(document).ready(function(){
    $("input:button").removeAttr("disabled");

    $(".resposta").click(function(){
       $(this).parent().children('span').css('visibility','visible');
	   $(this).attr({disabled: "disabled"});
    });

  });
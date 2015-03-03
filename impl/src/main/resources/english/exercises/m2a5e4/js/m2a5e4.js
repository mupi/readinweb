
  $(document).ready(function(){
    $("input:button").removeAttr("disabled");
   
    $(".resposta").click(function(){
       $(this).parent().children('span').show(); 
       $(this).attr({disabled: "disabled"});
    });
 
  });
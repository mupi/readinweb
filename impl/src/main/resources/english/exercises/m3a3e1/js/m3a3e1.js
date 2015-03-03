  $(document).ready(function(){
    $("input:button").removeAttr("disabled");
   
    $(".resposta").click(function(){
       $(this).parent().parent().next().children().show();
       $(this).attr('disabled','disabled');
    });
 
  });

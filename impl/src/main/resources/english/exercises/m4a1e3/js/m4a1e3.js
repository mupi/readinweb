
  $(document).ready(function(){
    $("input:button").removeAttr("disabled");
   
    $(".resposta").click(function(){
       $(this).parent().children('span').show(); 
       $(this).attr({disabled: "disabled"});
    });
    
    $("#link_1").click(function(){
		$(".paragrafo").hide();
		$("#p1").show();
    });
    
    $("#link_2").click(function(){
		$(".paragrafo").hide();
		$("#p2").show();
    });
    
    $("#link_4").click(function(){
		$(".paragrafo").hide();
		$("#p4").show();
    });
    
    $("#link_5").click(function(){
		$(".paragrafo").hide();
		$("#p5").show();
    });
 
  });
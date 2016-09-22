
  $(document).ready(function(){
    $("#resposta").click(function(){
    	
      var certo = "../../../../readinweb-tool/content/modulos/exercicios/imagens/certo.gif";
	  var errado = "../../../../readinweb-tool/content/modulos/exercicios/imagens/errado.gif";
	      
      if ($("#opcao1").val() == "5") {	          
		  $("#resp1").attr({ src: certo});
	  }
	  else {$("#resp1").attr({ src: errado});
	  $(this).next().show();};
	
      if ($("#opcao2").val() == "2") {
          $("#resp2").attr({ src: certo});
      }
      else {$("#resp2").attr({ src: errado});
      $(this).next().show();};

      if ($("#opcao3").val() == "3") {
          $("#resp3").attr({ src: certo});
      }
      else {$("#resp3").attr({ src: errado});
      $(this).next().show();};

      if ($("#opcao4").val() == "4") {
          $("#resp4").attr({ src: certo});
      }
      else {$("#resp4").attr({ src: errado});
      $(this).next().show();};

      if ($("#opcao5").val() == "1") {
          $("#resp5").attr({ src: certo});
      }
      else {$("#resp5").attr({ src: errado});
      $(this).next().show();};

      if ($("#opcao6").val() == "4") {
          $("#resp6").attr({ src: certo});
      }
      else {$("#resp6").attr({ src: errado});
      $(this).next().show();};

    });
 
  });
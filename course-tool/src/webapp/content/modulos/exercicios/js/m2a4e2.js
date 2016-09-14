
  $(document).ready(function(){
    $("input:button").removeAttr("disabled");
   
    $("#resposta").click(function(){
      var certo = "../../../../readinweb-tool/content/modulos/exercicios/imagens/certo.gif";
      var errado = "../../../../readinweb-tool/content/modulos/exercicios/imagens/errado.gif";
    	
      if ($("#opcao1").val() == "2") {
	  $("#resp1").attr({ src: certo});
      }
      else {$("#resp1").attr({ src: errado});};

      if ($("#opcao2").val() == "1") {
    	  $("#resp2").attr({ src: certo});
      }
      else {$("#resp2").attr({ src: errado});};

      if ($("#opcao3").val() == "2") {
	  $("#resp3").attr({ src: certo});
      }
      else {$("#resp3").attr({ src: errado});};

      if ($("#opcao4").val() == "1") {
	  $("#resp4").attr({ src: certo});
      }
      else {$("#resp4").attr({ src: errado});};

      if ($("#opcao5").val() == "1") {
	  $("#resp5").attr({ src: certo});
      }
      else {$("#resp5").attr({ src: errado});};

      if ($("#opcao6").val() == "1") {
	  $("#resp6").attr({ src: certo});
      }
      else {$("#resp6").attr({ src: errado});};

      if ($("#opcao7").val() == "2") {
	  $("#resp7").attr({ src: certo});
      }
      else {$("#resp7").attr({ src: errado});};

      if ($("#opcao8").val() == "1") {
	  $("#resp8").attr({ src: certo});
      }
      else {$("#resp8").attr({ src: errado});};

      if ($("#opcao9").val() == "1") {
	  $("#resp9").attr({ src: certo});
      }
      else {$("#resp9").attr({ src: errado});};

      if ($("#opcao10").val() == "1") {
	  $("#resp10").attr({ src: certo});
      }
      else {$("#resp10").attr({ src: errado});};

      if ($("#opcao11").val() == "2") {
	  $("#resp11").attr({ src: certo});
      }
      else {$("#resp11").attr({ src: errado});};

      if ($("#opcao12").val() == "2") {
	  $("#resp12").attr({ src: certo});
      }
      else {$("#resp12").attr({ src: errado});};
    });
    
  });

  $(document).ready(function(){
    $("#resposta").click(function(){
      var certo = "../../../../readinweb-tool/content/modulos/exercicios/imagens/certo.gif";
      var errado = "../../../../readinweb-tool/content/modulos/exercicios/imagens/errado.gif";
        
      if ($("#opcao1").val() == "1") {
	  $("#resp1").attr({ src: certo});
      }
      else {$("#resp1").attr({ src: errado});};

      if ($("#opcao2").val() == "3") {
          $("#resp2").attr({ src: certo});
      }
      else {$("#resp2").attr({ src: errado});};

      if ($("#opcao3").val() == "4") {
          $("#resp3").attr({ src: certo});
      }
      else {$("#resp3").attr({ src: errado});};

      if ($("#opcao4").val() == "1") {
          $("#resp4").attr({ src: certo});
      }
      else {$("#resp4").attr({ src: errado});};

      if ($("#opcao5").val() == "3") {
          $("#resp5").attr({ src: certo});
      }
      else {$("#resp5").attr({ src: errado});};

    });
 
  });
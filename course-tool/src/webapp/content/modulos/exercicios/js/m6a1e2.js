
  $(document).ready(function(){
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

      if ($("#opcao3").val() == "3") {
          $("#resp3").attr({ src: certo});
      }
      else {$("#resp3").attr({ src: errado});};

    });
 
  });
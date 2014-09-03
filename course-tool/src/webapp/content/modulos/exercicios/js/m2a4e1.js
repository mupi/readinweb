
  $(document).ready(function(){
    $("#resposta").click(function(){
      
        var certo = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
        var errado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";
      
      if ($("#opcao1").val() == "2") {
          document["resp1"].src = certo;
      }
      else {
    	  document["resp1"].src = errado;
      }

      if ($("#opcao2").val() == "1") {
          document["resp2"].src = certo;
      }
      else {
    	  document["resp2"].src = errado;
      }

      if ($("#opcao3").val() == "1") {
          document["resp3"].src = certo;
      }
      else {
    	  document["resp3"].src = errado;
      }

      if (($("#opcao4").val() == "2") && ($("#opcao5").val() == "2")) {
          document["resp4"].src = certo;
      }
      else {
    	  document["resp4"].src = errado;
      }

    });
 
  });

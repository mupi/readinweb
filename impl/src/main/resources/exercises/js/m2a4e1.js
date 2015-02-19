
  $(document).ready(function(){
    $("#resposta").click(function(){
      
        var certo = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
        var errado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";
      
      if ($("#opcao1").val() == "2") {
          document.getElementById("resp1").src = certo;
      }
      else {
    	  document.getElementById("resp1").src = errado;
      }

      if ($("#opcao2").val() == "1") {
          document.getElementById("resp2").src = certo;
      }
      else {
    	  document.getElementById("resp2").src = errado;
      }

      if ($("#opcao3").val() == "1") {
          document.getElementById("resp3").src = certo;
      }
      else {
    	  document.getElementById("resp3").src = errado;
      }

      if (($("#opcao4").val() == "2") && ($("#opcao5").val() == "2")) {
          document.getElementById("resp4").src = certo;
      }
      else {
    	  document.getElementById("resp4").src = errado;
      }

    });
 
  });

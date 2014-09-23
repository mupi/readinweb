  $(document).ready(function(){
    $("#resposta").click(function(){
    	             
      var certo = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
      var errado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";
     
      document.getElementById("resp1").src = $("#opcao1").val() == 1 ? certo : errado;
      document.getElementById("resp2").src = $("#opcao2").val() == 2 ? certo : errado;
      document.getElementById("resp3").src = $("#opcao3").val() == 2 ? certo : errado;
      document.getElementById("resp4").src = $("#opcao4").val() == 1 ? certo : errado;
      document.getElementById("resp5").src = $("#opcao5").val() == 2 ? certo : errado;
      document.getElementById("resp6").src = $("#opcao6").val() == 1 ? certo : errado;

    });
  });
 

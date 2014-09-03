
  $(document).ready(function(){
    $("#resposta").click(function(){
      var certo = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
      var errado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";
        
      if (($("#opcao1").val() == "1") && ($("#opcao2").val() == "4")){
	  $("#resp1").attr({ src: certo});
      }
      else {$("#resp1").attr({ src: errado});};

      if (($("#opcao3").val() == "2") && ($("#opcao4").val() == "5")){
	  $("#resp2").attr({ src: certo});
      }
      else {$("#resp2").attr({ src: errado});};

      if (($("#opcao5").val() == "3") && ($("#opcao6").val() == "6")){
	  $("#resp3").attr({ src: certo});
      }
      else {$("#resp3").attr({ src: errado});};

    });
 
  });
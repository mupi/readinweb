
  $(document).ready(function(){
    $("input:button").removeAttr("disabled");
   
    $("#resposta").click(function(){
      var certo = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
      var errado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";
        
      if ($("#opcao1").val() == "4") {
	  $("#resp1").attr({ src: certo});
      }
      else {$("#resp1").attr({ src: errado});};

      if ($("#opcao2").val() == "2") {
	  $("#resp2").attr({ src: certo});
      }
      else {$("#resp2").attr({ src: errado});};

      if ($("#opcao3").val() == "1") {
	  $("#resp3").attr({ src: certo});
      }
      else {$("#resp3").attr({ src: errado});};

      if ($("#opcao4").val() == "3") {
	  $("#resp4").attr({ src: certo});
      }
      else {$("#resp4").attr({ src: errado});};
    });
    
  });
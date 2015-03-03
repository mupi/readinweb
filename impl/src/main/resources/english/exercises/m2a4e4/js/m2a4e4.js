
  $(document).ready(function(){
    $("#resposta").click(function(){
      
        var certo = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
        var errado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";
      
      $("#txt_resp1, #txt_resp2").show();
        
        
      if ($("#opcao1").val() == "1") {
          document["resp1"].src = certo;
      }
      else {document["resp1"].src = errado;};

      if ($("#opcao2").val() == "2") {
          document["resp2"].src = certo;
      }
      else {document["resp2"].src = errado;};

    });
 
  });
 
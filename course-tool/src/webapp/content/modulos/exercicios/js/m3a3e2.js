  $(document).ready(function(){
    $("#resposta").click(function(){
    	
        var certo = "../../../../readinweb-tool/content/modulos/exercicios/imagens/certo.gif";
        var errado = "../../../../readinweb-tool/content/modulos/exercicios/imagens/errado.gif";
      
      if ($("#opcao1").val() == "1") {
          document["resp1"].src = certo;
      }
      else {document["resp1"].src = errado;}

      if ($("#opcao2").val() == "2") {
          document["resp2"].src = certo;
      }
      else {document["resp2"].src = errado;}

      if ($("#opcao3").val() == "2") {
          document["resp3"].src = certo;
      }
      else {document["resp3"].src = errado;}

      if ($("#opcao4").val() == "2") {
          document["resp4"].src = certo;
      }
      else {document["resp4"].src = errado;}

      if ($("#opcao5").val() == "2") {
          document["resp5"].src = certo;
      }
      else {document["resp5"].src = errado;}

      if ($("#opcao6").val() == "2") {
          document["resp6"].src = certo;
      }
      else {document["resp6"].src = errado;}

      if ($("#opcao7").val() == "2") {
          document["resp7"].src = certo;
      }
      else {document["resp7"].src = errado;}

      if ($("#opcao8").val() == "2") {
          document["resp8"].src = certo;
      }
      else {document["resp8"].src = errado;}
    });
 
  });
 
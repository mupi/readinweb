$(document).ready(function(){
    //true se clicou no lado esquerdo, false se clicou no lado direito
    var clicou_esquerda = false;
    var positon_esquerda;
    var positon_direita;
    var cont_cliques = 0;
    var x1;
    var y1;
    var x2;
    var y2;
    var quadro_esquerda;
    var quadro_direita;
    var certo1 = false;
    var certo2 = false;
    var certo3 = false;
    var certo4 = false;
    
    function desenha_linha( x1, y1, x2, y2, cor, grossura){
        $("#linha").drawLine(x1, y1, x2, y2, {color: cor, stroke: grossura});
    }
    $("input:button").removeAttr("disabled");

    $(".quadro").mouseover(function() {
	    $(this).addClass("mouse_over");
    });

    $(".quadro").mouseout(function() {
	    $(this).removeClass("mouse_over");
    });

    $(".quadro").click(function() {
     	if (($(this).parent("div").attr("id") == "col_esquerda") && !(clicou_esquerda))
	    {
		    $(this).addClass("quadro_clicado");
            clicou_esquerda = true;
            position_esquerda = $(this).position();
            quadro_esquerda = $(this).attr("id");
	    };
        if (($(this).parent("div").attr("id") == "col_direita") && (clicou_esquerda))
	    {
		    if (cont_cliques < 4)
		    {
				$(this).addClass("quadro_clicado");
		        clicou_esquerda = false;
		        position_direita = $(this).position();
		        quadro_direita = $(this).attr("id");
		        x2 = position_direita.left;
				y2 = position_direita.top + 55; //deixa no meio do quadrinho
				y1 = position_esquerda.top + 55; //deixa no meio do quadrinho
				x1 = position_esquerda.left + 200; //deixa depois do quadro da esquerda
				desenha_linha(x1,y1,x2,y2);
				cont_cliques = cont_cliques + 1;
				if ((quadro_esquerda == "esq1") && (quadro_direita == "dir4"))
				{
					certo1 = true;
				} else if ((quadro_esquerda == "esq2") && (quadro_direita == "dir1"))
						{
							certo2 = true;
						}
						else if ((quadro_esquerda == "esq3") && (quadro_direita == "dir2"))
							{
								certo3 = true;
							} else if ((quadro_esquerda == "esq4") && (quadro_direita == "dir3"))
								{
									certo4 = true;
								};
			}
	    };
    });
   
    $("#resposta").click(function(){
    	
    	var imgcerto = "../../../../readinweb-tool/content/modulos/exercicios/imagens/certo.gif";
        var imgerrado = "../../../../readinweb-tool/content/modulos/exercicios/imagens/errado.gif";
	   
        if (certo1)
	    {
	    	 $('#resp1').attr({ src: imgcerto });
	    }
	    else
	    {
	    	 $('#resp1').attr({ src: imgerrado});
	    };
	
	    if (certo2)
	    {
	    	 $('#resp2').attr({ src: imgcerto});
	    }
	    else
	    {
	    	 $('#resp2').attr({ src: imgerrado});
	    };
	
	    if (certo3)
	    {
	    	 $('#resp3').attr({ src: imgcerto});
	    }
	    else
	    {
	    	 $('#resp3').attr({ src: imgerrado});
	    };
	
	    if (certo4)
	    {
	    	 $('#resp4').attr({ src: imgcerto});
	    }
	    else
	    {
	    	 $('#resp4').attr({ src: imgerrado});
	    };
		
    });
    
});

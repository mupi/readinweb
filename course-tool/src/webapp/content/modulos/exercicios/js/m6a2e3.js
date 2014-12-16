$j(document).ready(function(){
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
        $j("#linha").drawLine(x1, y1, x2, y2, {color: cor, stroke: grossura});
    }
    $j("input:button").removeAttr("disabled");

    $j(".quadro").mouseover(function() {
	    $j(this).addClass("mouse_over");
    });

    $j(".quadro").mouseout(function() {
	    $j(this).removeClass("mouse_over");
    });

    $j(".quadro").click(function() {
     	if (($j(this).parent("div").attr("id") == "col_esquerda") && !(clicou_esquerda))
	    {
		    $j(this).addClass("quadro_clicado");
            clicou_esquerda = true;
            position_esquerda = $j(this).position();
            quadro_esquerda = $j(this).attr("id");
	    };
        if (($j(this).parent("div").attr("id") == "col_direita") && (clicou_esquerda))
	    {
		    if (cont_cliques < 4)
		    {
				$j(this).addClass("quadro_clicado");
		        clicou_esquerda = false;
		        position_direita = $j(this).position();
		        quadro_direita = $j(this).attr("id");
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

    $j("#resposta").click(function(){

    	var imgcerto = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
        var imgerrado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";

        if (certo1)
	    {
	    	 $j('#resp1').attr({ src: imgcerto });
	    }
	    else
	    {
	    	 $j('#resp1').attr({ src: imgerrado});
	    };

	    if (certo2)
	    {
	    	 $j('#resp2').attr({ src: imgcerto});
	    }
	    else
	    {
	    	 $j('#resp2').attr({ src: imgerrado});
	    };

	    if (certo3)
	    {
	    	 $j('#resp3').attr({ src: imgcerto});
	    }
	    else
	    {
	    	 $j('#resp3').attr({ src: imgerrado});
	    };

	    if (certo4)
	    {
	    	 $j('#resp4').attr({ src: imgcerto});
	    }
	    else
	    {
	    	 $j('#resp4').attr({ src: imgerrado});
	    };

    });

});

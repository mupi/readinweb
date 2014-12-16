$j(document).ready(function(){

    $j("#resposta").click(function(){

    	var imgcerto = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
        var imgerrado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";

        if ($j("#select1").val() == "4")
	    {
	    	 $j('#resp1').attr({ src: imgcerto });
	    }
	    else
	    {
	    	 $j('#resp1').attr({ src: imgerrado});
	    };

	    if ($j("#select2").val() == "1")
	    {
	    	 $j('#resp2').attr({ src: imgcerto});
	    }
	    else
	    {
	    	 $j('#resp2').attr({ src: imgerrado});
	    };

	    if ($j("#select3").val() == "3")
	    {
	    	 $j('#resp3').attr({ src: imgcerto});
	    }
	    else
	    {
	    	 $j('#resp3').attr({ src: imgerrado});
	    };

	    if ($j("#select4").val() == "2")
	    {
	    	 $j('#resp4').attr({ src: imgcerto});
	    }
	    else
	    {
	    	 $j('#resp4').attr({ src: imgerrado});
	    };

    });

});

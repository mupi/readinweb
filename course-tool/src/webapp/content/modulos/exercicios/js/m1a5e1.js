
  $(document).ready(function(){
    $(".respos").click(function(){
    
        var certo = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
        var errado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";

        if ($(this).prev().prev().children('input').val().toUpperCase() == 
        $(this).next().children('.resposta1').text().toUpperCase() ){
        	$(this).prev().prev().children('img').attr('src', certo);
        }
		else {
			$(this).next().children('.resposta1').show('slow');
			$(this).prev().prev().children('img').attr('src', errado);
		}
		if ($(this).prev().children('input').val().toUpperCase() == 
		$(this).next().children('.resposta2').text().toUpperCase()){
			$(this).prev().children('img').attr('src', certo);
		}
		else {
			$(this).next().children('.resposta2').show('slow');
			$(this).prev().children('img').attr('src', errado);
		}
	 });
 
  });
 
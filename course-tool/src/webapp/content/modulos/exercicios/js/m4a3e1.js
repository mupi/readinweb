$(document).ready(function(){
	 var certo = "/readinweb-course-tool/content/modulos/exercicios/imagens/certo.gif";
     var errado = "/readinweb-course-tool/content/modulos/exercicios/imagens/errado.gif";
	
	$('#answer1').click(function(){
		if($("#select11").val() == "1" && $("#select12").val() == "4")
			$('#img1').attr('src', certo);
		else
			$('#img1').attr('src', errado);
		
		$('#img1').show('fast');
	});
	
	$('#answer2').click(function(){
		if($("#select21").val() == "2" && $("#select22").val() == "5")
			$('#img2').attr('src', certo);
		else
			$('#img2').attr('src', errado);
		
		$('#img2').show('fast');
	});
	
	$('#answer3').click(function(){
		if($("#select31").val() == "3" && $("#select32").val() == "6")
			$('#img3').attr('src', certo);
		else
			$('#img3').attr('src', errado);
		
		$('#img3').show('fast');
	});
});
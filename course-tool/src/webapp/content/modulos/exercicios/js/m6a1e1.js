$(document).ready(function(){
	var certo = "../../../../readinweb-tool/content/modulos/exercicios/imagens/certo.gif";
	var errado = "../../../../readinweb-tool/content/modulos/exercicios/imagens/errado.gif";
	$("#answer1").click(function(){
		if ($('input[name=subject1]:checked').val() == '2')
			$('#img1').attr('src', certo);
		else
			$('#img1').attr('src', errado);
	});
	
	$("#answer2").click(function(){
		if ($('input[name=subject2]:checked').val() == '1')
			$('#img2').attr('src', certo);
		else
			$('#img2').attr('src', errado);
	});
	
	$("#answer3").click(function(){
		if ($('input[name=subject3]:checked').val() == '2')
			$('#img3').attr('src', certo);
		else
			$('#img3').attr('src', errado);
	});
	
	$("#answer4").click(function(){
		if ($('input[name=subject4]:checked').val() == '1')
			$('#img4').attr('src', certo);
		else
			$('#img4').attr('src', errado);
	});
});